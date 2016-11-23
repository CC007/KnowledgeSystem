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
public class StringItem extends KnowledgeItem<String>{

    public StringItem(String name, KnowledgeOrigin origin, boolean goal) {
        super(name, origin, goal);
    }

    public StringItem(String name, String value, KnowledgeOrigin origin, boolean goal) {
        super(name, value, origin, goal);
    }

    public StringItem(StringItem item, String value) {
        super(item, value);
    }

}
