/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cc007.knowledgesystem.model.rules.conditions;

import com.github.cc007.knowledgesystem.model.knowledge.KnowledgeBase;
import com.github.cc007.knowledgesystem.model.knowledge.items.KnowledgeItem;
import java.math.BigInteger;

/**
 *
 * @author Rik Schaaf aka CC007 (http://coolcat007.nl/)
 * @param <T>
 */
public class ValueCondition<T extends Comparable<T>> extends Condition {

    private final T value;
    private final ValueOperator operator;

    public ValueCondition(String knowledgeItemName, T value, ValueOperator operator) {
        super(knowledgeItemName);
        this.value = value;
        this.operator = operator;
    }

    @Override
    public boolean check(KnowledgeBase knowledgeBase) {
        KnowledgeItem<T> item = knowledgeBase.getItem(knowledgeItemName);
        if (item == null) {
            return false;
        }
        if (!item.isValueSet()) {
            switch (operator) {
                case NOTEQUAL:
                    return true;
                default:
                    return false;
            }
        }
        switch (operator) {
            case EQUAL:
                return item.getValue().equals(value);
            case NOTEQUAL:
                return !item.getValue().equals(value);
            case GREATER:
                return item.getValue().compareTo(value) > 0;
            case LESS:
                return item.getValue().compareTo(value) < 0;
            default:
                return false;
        }
    }
}
