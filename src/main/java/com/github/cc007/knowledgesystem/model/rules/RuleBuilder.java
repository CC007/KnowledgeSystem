/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cc007.knowledgesystem.model.rules;

import com.github.cc007.knowledgesystem.model.knowledge.KnowledgeBase;
import com.github.cc007.knowledgesystem.model.knowledge.items.KnowledgeItem;
import com.github.cc007.knowledgesystem.model.rules.conditions.Condition;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rik Schaaf aka CC007 (http://coolcat007.nl/)
 */
public class RuleBuilder {

    private List<Condition> conditions;
    private KnowledgeItem consequence;

    public RuleBuilder(KnowledgeItem consequence) {
        conditions = new ArrayList<>();
        this.consequence = consequence;

    }

    public RuleBuilder addCondition(Condition condition) {
        conditions.add(condition);
        return this;
    }

    public Rule build() {
        return new Rule(conditions, consequence);
    }

}
