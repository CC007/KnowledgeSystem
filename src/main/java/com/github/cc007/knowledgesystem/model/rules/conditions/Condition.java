/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cc007.knowledgesystem.model.rules.conditions;

import com.github.cc007.knowledgesystem.model.knowledge.KnowledgeBase;

/**
 *
 * @author Rik Schaaf aka CC007 (http://coolcat007.nl/)
 */
public abstract class Condition {

    protected final String knowledgeItemName;

    public Condition(String knowledgeItemName) {
        this.knowledgeItemName = knowledgeItemName;
    }

    public abstract Boolean check(KnowledgeBase knowledgeBase);

    public String getKnowledgeItemName() {
        return knowledgeItemName;
    }
    
    
}
