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
public class FloatingPointItem extends KnowledgeItem<Double> {

    public FloatingPointItem(String name, KnowledgeOrigin origin, boolean goal) {
        super(name, origin, goal);
    }

    public FloatingPointItem(String name, Double value, KnowledgeOrigin origin, boolean goal) {
        super(name, value, origin, goal);
    }

    public FloatingPointItem(FloatingPointItem item) {
        super(item);
    }

    @Override
    protected String getType() {
        return "decimal";
    }

    @Override
    public FloatingPointItem copy() {
        return new FloatingPointItem(this);
    }

    @Override
    public String getDefaultQuestion() {
        return "Give the decimal value of " + name;
    }

}
