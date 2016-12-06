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
import com.github.cc007.knowledgesystem.view.View;

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
        System.out.println("[ctrl] Starting inference...");
        boolean goalFound = false;
        KnowledgeItem goalItem = null;
        int i = 0;
        while (!goalFound) {
            int ruleCounter = 0;
            for (; ruleCounter < ruleBase.size(); i = (i + 1) % ruleBase.size()) {
                System.out.println("[ctrl]  Checking conditions for rule " + i);
                Rule rule = ruleBase.getRule(i);
                if (!rule.checkConditions(knowledgeBase, false)) {
                    System.out.println("[ctrl]   Rule not applicable without user input");
                    ruleCounter++;
                } else {
                    ruleCounter = 0;
                    KnowledgeItem item = rule.getConsequence();
                    System.out.println("[ctrl]   Rule applied. New value of " + item.getName() + ": " + item.getValue());
                    knowledgeBase.setItem(item);
                    if (item.isGoal()) {
                        goalFound = true;
                        goalItem = item;
                        break;
                    }
                }
            }
            if (goalFound) {
                break;
            }
            System.out.println("[ctrl] User input required");
            boolean change = false;
            for (Rule rule : ruleBase) {
                System.out.println("[ctrl]  Checking if rule needs user input");
                if (!rule.checkConditions(knowledgeBase, true)) {
                    System.out.println("[ctrl]  Skipping rule");
                    continue;
                } else {
                    System.out.println("[ctrl]  Updating rule with user data where needed");
                    for (Condition condition : rule.getConditions()) {
                        KnowledgeItem item = knowledgeBase.getItem(condition.getKnowledgeItemName());
                        System.out.println("[ctrl]   condition with value origin " + item.getOrigin() + ", value is " + (item.isValueSet() ? "" : "not ") + "set");
                        if (item != null && !item.isValueSet()) {
                            switch (item.getOrigin()) {
                                case INFERRED:
                                case GIVEN:
                                    continue;
                                default:
                                    knowledgeBase.setItem(view.inquire(item));
                                    change = true;
                            }
                        }
                    }
                }
            }
            if (!change) {
                break;
            }
        }
        view.showResult(goalItem, knowledgeBase);
    }
}
