/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cc007.knowledgesystem.controller;

import com.github.cc007.knowledgesystem.model.knowledge.items.KnowledgeItem;

/**
 *
 * @author Rik Schaaf aka CC007 (http://coolcat007.nl/)
 */
public class State {

    private int currentCounter;
    private boolean goalFound;
    private KnowledgeItem goalItem;

    public State(int currentCounter, boolean found, KnowledgeItem item) {
        this.currentCounter = currentCounter;
        this.goalFound = found;
        this.goalItem = item;
    }

    public int getCurrentCounter() {
        return currentCounter;
    }

    public boolean isGoalFound() {
        return goalFound;
    }

    public KnowledgeItem getItem() {
        return goalItem;
    }

}
