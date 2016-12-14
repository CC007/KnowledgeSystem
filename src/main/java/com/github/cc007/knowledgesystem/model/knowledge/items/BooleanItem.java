/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cc007.knowledgesystem.model.knowledge.items;

import com.github.cc007.knowledgesystem.model.knowledge.KnowledgeOrigin;

/**
 *
 * @author Rik Schaaf aka CC007 (http://coolcat007.nl/)
 */
public class BooleanItem extends KnowledgeItem<Boolean> {

    public BooleanItem(String name, KnowledgeOrigin origin, boolean goal) {
        super(name, origin, goal);
    }

    public BooleanItem(String name, Boolean value, KnowledgeOrigin origin, boolean goal) {
        super(name, value, origin, goal);
    }

    public BooleanItem(BooleanItem item) {
        super(item);
    }

    @Override
    public String getType() {
        return "boolean";
    }

    @Override
    public BooleanItem copy() {
        return new BooleanItem(this);
    }

    @Override
    public String getDefaultQuestion() {
        return "Give the boolean value of " + name;
    }

}
