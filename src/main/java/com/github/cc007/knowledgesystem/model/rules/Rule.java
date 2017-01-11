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
        Logger.getLogger(Rule.class.getName()).info("Checking " + consequence.getName() + ":");
        if (knowledgeBase.getItem(consequence.getName()).isValueSet()) {
        Logger.getLogger(Rule.class.getName()).info("x Concequence already set");
            return false;
        }
        Logger.getLogger(Rule.class.getName()).info("- Concequence not yet set");
        Logger.getLogger(Rule.class.getName()).info("- Checking conditions");
        for (Condition condition : conditions) {
            Logger.getLogger(Rule.class.getName()).info(" - check value of " + condition.getKnowledgeItemName());
            Boolean check = condition.check(knowledgeBase);
            if (check != null && check) {
                Logger.getLogger(Rule.class.getName()).info("  + Condition met");
                continue;
            }
            Logger.getLogger(Rule.class.getName()).info("  - Condition not met");
            Logger.getLogger(Rule.class.getName()).info(" - Check if value is set and its (expected) origin");
            if (check != null) {
                Logger.getLogger(Rule.class.getName()).info("  x Value already set");
                return false;
            }
            KnowledgeItem item = knowledgeBase.getItem(condition.getKnowledgeItemName());
            if (item == null) {
                Logger.getLogger(Rule.class.getName()).info(" x Item not available");
                return false;
            }
            Logger.getLogger(Rule.class.getName()).info("  - Value not set");
            switch (item.getOrigin()) {
                case GIVEN:
                case INFERRED:
                    Logger.getLogger(Rule.class.getName()).info("  x Given or inferred value origin expected");
                    return false;
                default:
                    Logger.getLogger(Rule.class.getName()).info("  + Correct value origin expected");
                    continue;

            }
        }
        return true;
    }

    private boolean checkWithoutInput(KnowledgeBase knowledgeBase) {
        if (knowledgeBase.getItem(consequence.getName()).isValueSet()) {
            return false;
        }
        for (Condition condition : conditions) {
            Boolean check = condition.check(knowledgeBase);
            if (check == null || !check) {
                return false;
            }
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
