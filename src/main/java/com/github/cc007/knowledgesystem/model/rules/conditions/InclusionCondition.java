/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cc007.knowledgesystem.model.rules.conditions;

import com.github.cc007.knowledgesystem.model.knowledge.KnowledgeBase;
import com.github.cc007.knowledgesystem.model.knowledge.items.KnowledgeItem;
import com.github.cc007.knowledgesystem.model.knowledge.items.MultipleChoiceSelectionItem;

/**
 *
 * @author Rik
 * @param <int>
 */
public class InclusionCondition extends Condition {

    private boolean included;
    private int value;

    public InclusionCondition(String knowledgeItemName, int value, boolean included) {
        super(knowledgeItemName);
        this.value = value;
        this.included = included;
    }

    public InclusionCondition(String knowledgeItemName, int index) {
        this(knowledgeItemName, index, true);
    }

    public InclusionCondition(String knowledgeItemName, KnowledgeBase knowledgeBase, String value) {
        this(knowledgeItemName, castItem(knowledgeItemName, knowledgeBase).getIndex(value), true);
    }

    public InclusionCondition(String knowledgeItemName, KnowledgeBase knowledgeBase, String value, boolean included) {
        this(knowledgeItemName, castItem(knowledgeItemName, knowledgeBase).getIndex(value), included);
    }

    @Override
    public boolean check(KnowledgeBase knowledgeBase) {
        MultipleChoiceSelectionItem item = castItem(knowledgeItemName, knowledgeBase);
        if (item == null) {
            return false;
        }
        if (!item.isValueSet()) {
            return false;
        }
        if (included) {
            return item.getSelectedIndices().contains(value);
        } else {
            return !item.getSelectedIndices().contains(value);
        }

    }

    private static MultipleChoiceSelectionItem castItem(String knowledgeItemName, KnowledgeBase knowledgeBase) {
        if (!(knowledgeBase.getItem(knowledgeItemName) instanceof MultipleChoiceSelectionItem)) {
            throw new IllegalArgumentException(knowledgeItemName + " is not a multiple choice selection item");
        }
        return (MultipleChoiceSelectionItem) knowledgeBase.getItem(knowledgeItemName);

    }
}
