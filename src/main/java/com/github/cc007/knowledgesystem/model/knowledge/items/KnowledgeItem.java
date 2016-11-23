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
 * @param <T> value type
 */
public abstract class KnowledgeItem<T> {

    protected final String name;
    protected final T value;
    protected final boolean valueSet;
    protected final KnowledgeOrigin origin;
    protected final boolean goal;

    public KnowledgeItem(String name, KnowledgeOrigin origin, boolean goal) {
        this.name = name;
        this.value = null;
        this.valueSet = false;
        this.origin = origin;
        this.goal = goal;
    }

    public KnowledgeItem(String name, T value, KnowledgeOrigin origin, boolean goal) {
        this.name = name;
        this.value = value;
        this.valueSet = true;
        this.origin = origin;
        this.goal = goal;
    }

    public KnowledgeItem(KnowledgeItem<T> item, T value) {
        this.name = item.getName();
        this.value = value;
        this.valueSet = true;
        this.origin = item.getOrigin();
        this.goal = item.isGoal();
    }

    public String getName() {
        return name;
    }

    public T getValue() {
        return value;
    }

    public boolean isValueSet() {
        return valueSet;
    }

    public KnowledgeOrigin getOrigin() {
        return origin;
    }

    public boolean isGoal() {
        return goal;
    }
    
    

}
