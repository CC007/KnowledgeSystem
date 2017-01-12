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
import java.util.logging.Logger;

/**
 *
 * @author Rik Schaaf aka CC007 (http://coolcat007.nl/)
 */
public class InferenceSystem {

    private final RuleBase ruleBase;
    private final KnowledgeBase knowledgeBase;
    private final View view;

    public InferenceSystem(RuleBase ruleBase, KnowledgeBase knowledgeBase, View view) {
        this.ruleBase = ruleBase;
        this.knowledgeBase = knowledgeBase;
        this.view = view;
    }

    public void start() {
        Logger.getLogger(InferenceSystem.class.getName()).info("[ctrl] Starting inference...");
        boolean goalFound = false;
        KnowledgeItem goalItem = null;

        // print the rule base for debugging purposes
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
        
        //continue looping until the goal is found
        int i = 0;
        while (!goalFound) {
            int ruleCounter = 0;
            
            // print the knowledge base for debugging purposes
            System.out.println("KB:");
            for (KnowledgeItem item : knowledgeBase) {
                System.out.println(item.getName() + " (" + item.getType() + "): " + item.getValue());
            }
            
            // loop using forward chaining to see if rules can be applied without user input (continue looping until none of the rules can be applied anymore)
            for (; ruleCounter < ruleBase.size(); i = (i + 1) % ruleBase.size()) {
                Logger.getLogger(InferenceSystem.class.getName()).info("[ctrl]  Checking conditions for rule " + i);
                Rule rule = ruleBase.getRule(i);
                if (!rule.checkConditions(knowledgeBase, false)) {
                    Logger.getLogger(InferenceSystem.class.getName()).info("[ctrl]   Rule not applicable without user input");
                    ruleCounter++;
                } else {
                    ruleCounter = 0;
                    KnowledgeItem item = rule.getConsequence();
                    Logger.getLogger(InferenceSystem.class.getName()).info("[ctrl]   Rule applied. New value of " + item.getName() + ": " + item.getValue());
                    knowledgeBase.setItem(item);
                    if (item.isGoal()) {
                        goalFound = true;
                        goalItem = item;
                        break;
                    }
                }
            }
            
            // break out of the loop when the goal is found
            if (goalFound) {
                break;
            }
            
            // loop using forward chaining to see if rules can be applied with user input (loop once)
            Logger.getLogger(InferenceSystem.class.getName()).info("[ctrl] User input required");
            boolean change = false;
            for (Rule rule : ruleBase) {
                Logger.getLogger(InferenceSystem.class.getName()).info("[ctrl]  Checking if rule needs user input");
                if (!rule.checkConditions(knowledgeBase, true)) {
                    Logger.getLogger(InferenceSystem.class.getName()).info("[ctrl]  Skipping rule");
                    continue;
                } else {
                    Logger.getLogger(InferenceSystem.class.getName()).info("[ctrl]  Updating rule with user data where needed");
                    for (Condition condition : rule.getConditions()) {
                        KnowledgeItem item = knowledgeBase.getItem(condition.getKnowledgeItemName());
                        Logger.getLogger(InferenceSystem.class.getName()).info("[ctrl]   condition with value origin " + item.getOrigin() + ", value is " + (item.isValueSet() ? "" : "not ") + "set");
                        if (item != null && !item.isValueSet()) {
                            switch (item.getOrigin()) {
                                case INFERRED:
                                case GIVEN:
                                    continue;
                                default:
                                    knowledgeBase.setItem(view.inquire(item.copy()));
                                    change = true;
                                    break;
                            }
                        }
                    }
                    break;
                }
            }
            if (!change) {
                break;
            }
        }
        view.showResult(goalItem, knowledgeBase);
    }
}
