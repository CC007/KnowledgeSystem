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
public class StringItem extends KnowledgeItem<String> {

    public StringItem(String name, KnowledgeOrigin origin, boolean goal) {
        super(name, origin, goal);
    }

    public StringItem(String name, String value, KnowledgeOrigin origin, boolean goal) {
        super(name, value, origin, goal);
    }

    public StringItem(StringItem item) {
        super(item);
    }

    @Override
    protected String getType() {
        return "text";
    }

    @Override
    public StringItem copy() {
        return new StringItem(this);
    }

    @Override
    public String getDefaultQuestion() {
        return "Give the text value of " + name;
    }

}
