/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cc007.knowledgesystem.model.rules.conditions;

import com.github.cc007.knowledgesystem.model.knowledge.KnowledgeBase;
import com.github.cc007.knowledgesystem.model.knowledge.items.KnowledgeItem;

/**
 *
 * @author Rik Schaaf aka CC007 (http://coolcat007.nl/)
 */
public class EqualityCondition<T> extends Condition {

    private final T value;
    private final boolean equal;

    public EqualityCondition(String knowledgeItemName, T value) {
        this(knowledgeItemName, value, true);
    }

    public EqualityCondition(String knowledgeItemName, T value, boolean equal) {
        super(knowledgeItemName);
        this.value = value;
        this.equal = equal;
    }

    @Override
    public boolean check(KnowledgeBase knowledgeBase) {
        KnowledgeItem item = knowledgeBase.getItem(knowledgeItemName);
        if (item == null) {
            return false;
        }
        if (!item.isValueSet()) {
            return !equal;
        }
        if (equal) {
            return item.getValue().equals(value);
        }
        return !item.getValue().equals(value);
    }

    public T getValue() {
        return value;
    }

    public boolean isEqual() {
        return equal;
    }

}
