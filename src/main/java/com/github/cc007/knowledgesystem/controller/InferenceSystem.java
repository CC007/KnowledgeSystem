/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cc007.knowledgesystem.controller;

import com.github.cc007.knowledgesystem.model.knowledge.KnowledgeBase;
import com.github.cc007.knowledgesystem.model.knowledge.items.KnowledgeItem;
import com.github.cc007.knowledgesystem.model.rules.Rule;
import com.github.cc007.knowledgesystem.model.rules.RuleBase;
import com.github.cc007.knowledgesystem.model.rules.conditions.Condition;
import com.github.cc007.knowledgesystem.model.rules.conditions.EqualityCondition;
import com.github.cc007.knowledgesystem.view.View;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Logger;
import java.util.stream.BaseStream;

/**
 *
 * @author Rik Schaaf aka CC007 (http://coolcat007.nl/)
 */
public class InferenceSystem {

    private final RuleBase ruleBase;
    private final KnowledgeBase knowledgeBase;
    private final View view;

    private int currentCounter;
    private boolean goalFound;
    private boolean goalReachable;
    private KnowledgeItem goalItem;

    public InferenceSystem(RuleBase ruleBase, KnowledgeBase knowledgeBase, View view) {
        this.ruleBase = ruleBase;
        this.knowledgeBase = knowledgeBase;
        this.view = view;

        this.currentCounter = 0;
        this.goalFound = false;
        this.goalReachable = true;
        this.goalItem = null;
    }

    public void start() {
        System.out.println("[ctrl] Starting inference...");

        // print the rule base for debugging purposes
        printRB();

        //continue looping until the goal is found or unreachable
        while (goalReachable) {
            // print the knowledge base for debugging purposes
            printKB();

            forwardChainingWithoutInput();

            if (goalFound) {
                // break out of the loop when the goal is found
                break;
            }

            backwardChainingWithInput();
        }
        view.showResult(goalItem, knowledgeBase);
    }

    private void printRB() {
        System.out.println("RB:");
        int ruleCnt = 1;
        for (Rule rule : ruleBase) {
            System.out.println("rule " + ruleCnt);
            System.out.println(" conditions:");
            for (Condition condition : rule.getConditions()) {
                System.out.println(" - " + condition.getKnowledgeItemName());
                if (condition instanceof EqualityCondition) {
                    EqualityCondition eqCondition = (EqualityCondition) condition;
                    System.out.println("   " + eqCondition.getValue() + " (" + eqCondition.isEqual() + ")");
                }
            }
            System.out.println(" concequence: " + rule.getConsequence().getName() + " (" + rule.getConsequence().getType() + "): " + rule.getConsequence().getValue());
            ruleCnt++;
        }
    }

    private void printKB() {
        System.out.println("KB:");
        for (KnowledgeItem item : knowledgeBase) {
            System.out.println(item.getName() + " (" + item.getType() + "): " + item.getValue());
        }
    }

    private void forwardChainingWithoutInput() {
        // loop using forward chaining to see if rules can be applied without user input (continue looping until none of the rules can be applied anymore)
        int checkedRuleCounter = 0;
        for (; checkedRuleCounter < ruleBase.size(); currentCounter = (currentCounter + 1) % ruleBase.size()) {
            Rule rule = ruleBase.getRule(currentCounter);
            System.out.println("[ctrl]  Checking conditions for rule " + currentCounter + " (" + rule.getConsequence().getName() +")");
            if (rule.checkConditions(knowledgeBase, false)) {
                checkedRuleCounter = 0;
                KnowledgeItem item = rule.getConsequence();
                System.out.println("[ctrl]   Rule applied. New value of " + item.getName() + ": " + item.getValue());
                knowledgeBase.setItem(item);
                if (item.isGoal()) {
                    goalFound = true;
                    goalItem = item;
                    return;
                }
            } else {
                System.out.println("[ctrl]   Rule not applicable without user input");
                checkedRuleCounter++;
            }
        }
    }

    private void forwardChainingWithInput() {
        // loop using forward chaining to see if rules can be applied with user input (loop once)
        System.out.println("[ctrl] User input required");
        for (Rule rule : ruleBase) {
            System.out.println("[ctrl]  Checking if rule needs user input");
            if (rule.checkConditions(knowledgeBase, true)) {
                askQuestion(rule);
                return;
            } else {
                System.out.println("[ctrl]  Skipping rule");
            }
        }
        goalReachable = false;
    }

    private void backwardChainingWithInput() {
        Queue<Rule> ruleQueue = new LinkedList<>();

        // initialize queue with rules towards goals
        knowledgeBase.stream().filter((item) -> item.isGoal()).forEach((item) -> {
            ruleBase.stream().filter((rule) -> rule.getConsequence().getName().equals(item.getName())).forEach((rule) -> {
                System.out.println("Add rule for item " + item.getName());
                ruleQueue.add(rule);
            });
        });

        System.out.println("Start bfs");
        // walk through the queue with breadth first search
        Rule currentRule = ruleQueue.poll();
        while (currentRule != null) {
            System.out.println("Current rule consequence: " + currentRule.getConsequence().getName());
            if (currentRule.checkConditions(knowledgeBase, true)) {
                askQuestion(currentRule);
                return;
            } else {
                for (Condition condition : currentRule.getConditions()) {
                    ruleBase.stream().filter((rule) -> rule.getConsequence().getName().equals(condition.getKnowledgeItemName())).forEach((rule) -> {
                        System.out.println("Add rule for item " + condition.getKnowledgeItemName());
                        ruleQueue.add(rule);
                    });
                }
            }

            // go to next rule
            currentRule = ruleQueue.poll();
        }
        goalReachable = false;
    }

    private void askQuestion(Rule rule) {
        System.out.println("[ctrl]  Updating rule with user data where needed");
        for (Condition condition : rule.getConditions()) {
            KnowledgeItem item = knowledgeBase.getItem(condition.getKnowledgeItemName());
            if (item != null && !item.isValueSet()) {
                System.out.println("[ctrl]   condition with value origin " + item.getOrigin() + ", value is " + (item.isValueSet() ? "" : "not ") + "set");
                switch (item.getOrigin()) {
                    case INFERRED:
                    case GIVEN:
                        break;
                    default:
                        KnowledgeItem newItem = view.inquire(item.copy());
                        System.out.println("Value of " + newItem.getName() + ": " + newItem.getValue());
                        knowledgeBase.setItem(newItem);
                        return;
                }
            }
        }
    }

}
