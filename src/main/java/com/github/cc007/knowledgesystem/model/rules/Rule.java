/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cc007.knowledgesystem.model.rules;

import com.github.cc007.knowledgesystem.model.rules.conditions.Condition;
import com.github.cc007.knowledgesystem.model.knowledge.KnowledgeBase;
import com.github.cc007.knowledgesystem.model.knowledge.items.KnowledgeItem;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Rik Schaaf aka CC007 (http://coolcat007.nl/)
 */
public class Rule {

    private final List<Condition> conditions;
    private final KnowledgeItem consequence;

    public Rule(List<Condition> conditions, KnowledgeItem consequence) {
        this.conditions = conditions;
        this.consequence = consequence;
    }

    public boolean checkConditions(KnowledgeBase knowledgeBase, boolean userInput) {
        if (!userInput) {
            return checkWithoutInput(knowledgeBase);
        } else {
            return checkWithInput(knowledgeBase);
        }
    }

    private boolean checkWithInput(KnowledgeBase knowledgeBase) {
        System.out.println("Checking " + consequence.getName() + ":");
        if (knowledgeBase.getItem(consequence.getName()).isValueSet()) {
            System.out.println("x Concequence already set");
            return false;
        }
        System.out.println("- Concequence not yet set");
        System.out.println("- Checking conditions");
        for (Condition condition : conditions) {
            System.out.println(" - check value of " + condition.getKnowledgeItemName());
            Boolean check = condition.check(knowledgeBase);
            if (check != null && check) {
                System.out.println("  + Condition met");
                continue;
            }
            System.out.println("  - Condition not met");
            System.out.println(" - Check if value is set and its (expected) origin");
            if (check != null) {
                System.out.println("  x Value already set");
                return false;
            }
            KnowledgeItem item = knowledgeBase.getItem(condition.getKnowledgeItemName());
            if (item == null) {
                System.out.println(" x Item not available");
                return false;
            }
            System.out.println("  - Value not set");
            switch (item.getOrigin()) {
                case GIVEN:
                case INFERRED:
                    System.out.println("  x Given or inferred value origin expected");
                    return false;
                default:
                    System.out.println("  + Correct value origin expected");
                    continue;

            }
        }
        return true;
    }

    private boolean checkWithoutInput(KnowledgeBase knowledgeBase) {
        if (knowledgeBase.getItem(consequence.getName()).isValueSet()) {
            System.out.println("[ctrl] value of " + consequence.getName() + " already set");
            return false;
        }
        for (Condition condition : conditions) {
            Boolean check = condition.check(knowledgeBase);
            if (check == null || !check) {
                System.out.println("Condition " + condition.getKnowledgeItemName() + ": knowledge item is not set or not the correct value");
                return false;
            }
            System.out.println("Condition " + condition.getKnowledgeItemName() + " is met");
        }
        return true;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public KnowledgeItem getConsequence() {
        return consequence;
    }

}
