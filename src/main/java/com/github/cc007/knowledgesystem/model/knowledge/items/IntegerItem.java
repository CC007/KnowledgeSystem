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
public class IntegerItem extends KnowledgeItem<Integer>{

    public IntegerItem(String name, KnowledgeOrigin origin, boolean goal) {
        super(name, origin, goal);
    }

    public IntegerItem(String name, Integer value, KnowledgeOrigin origin, boolean goal) {
        super(name, value, origin, goal);
    }

    public IntegerItem(IntegerItem item, Integer value) {
        super(item, value);
    }

    
}
