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
 * @author Rik
 */
public class IsSetCondition extends Condition {

    public IsSetCondition(String knowledgeItemName) {
        super(knowledgeItemName);
    }

    @Override
    public Boolean check(KnowledgeBase knowledgeBase) {
        KnowledgeItem item = knowledgeBase.getItem(knowledgeItemName);
        if (item == null) {
            return null;
        }
        return item.isValueSet();
    }

}
