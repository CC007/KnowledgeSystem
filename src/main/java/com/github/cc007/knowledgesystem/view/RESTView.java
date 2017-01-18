/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cc007.knowledgesystem.view;

import com.github.cc007.knowledgesystem.model.knowledge.KnowledgeBase;
import com.github.cc007.knowledgesystem.model.knowledge.items.KnowledgeItem;
import com.github.cc007.knowledgesystem.server.RESTHandler;
import com.github.cc007.knowledgesystem.server.ResponseMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rik Schaaf aka CC007 (http://coolcat007.nl/)
 */
public class RESTView implements View {

    private final RESTHandler restHandler;
    private volatile KnowledgeItem knowledge;
    private volatile KnowledgeBase context;
    private volatile boolean goalReached;
    private List<ResponseMessage> choices;
    public final Object knowledgeSystemLock;
    private volatile boolean newKnowledgeSystemData;
    public final Object restHandlerLock;
    private volatile boolean newRestHandlerData;
    private volatile boolean stopped;

    public RESTView(RESTHandler restHandler) {
        this.restHandler = restHandler;
        this.knowledge = null;
        this.goalReached = false;
        this.newKnowledgeSystemData = false;
        this.newRestHandlerData = false;
        this.choices = new ArrayList<>();
        this.knowledgeSystemLock = new Object();
        this.restHandlerLock = new Object();
        this.stopped = false;
    }

    @Override
    public <T> KnowledgeItem<T> inquire(KnowledgeItem<T> item) {
        // make data available for the REST response
        goalReached = false;
        knowledge = item;
        setNewKnowledgeSystemData(true);

        // wait for the rest handler to provide new data
        synchronized (restHandlerLock) {
            while (!newRestHandlerData) {
                try {
                    restHandlerLock.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(RESTView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        //set new data variable back to false now that the new data will be processed, to prevent processing it again
        setNewRestHandlerData(false);

        if (stopped) {
            Thread.currentThread().stop();
        }
        return knowledge;
    }

    @Override
    public void showResult(KnowledgeItem goal, KnowledgeBase knowledgeBase) {
        goalReached = true;
        knowledge = goal;
        context = knowledgeBase;
        setNewKnowledgeSystemData(true);
    }

    public boolean hasNewKnowledgeSystemData() {
        return newKnowledgeSystemData;
    }

    public boolean hasNewRestHandlerData() {
        return newRestHandlerData;
    }

    public void setNewKnowledgeSystemData(boolean newKnowledgeSystemData) {
        synchronized (knowledgeSystemLock) {
            this.newKnowledgeSystemData = newKnowledgeSystemData;
            knowledgeSystemLock.notify();
        }
    }

    public void setNewRestHandlerData(boolean newRestHandlerData) {
        synchronized (restHandlerLock) {
            this.newRestHandlerData = newRestHandlerData;
            restHandlerLock.notify();
        }
    }

    public boolean isGoalReached() {
        return goalReached;
    }

    public void setGoalReached(boolean goalReached) {
        this.goalReached = goalReached;
    }

    public KnowledgeItem getKnowledge() {
        return knowledge;
    }

    public KnowledgeBase getContext() {
        return context;
    }

    public List<ResponseMessage> getChoices() {
        return choices;
    }

    public void addChoice(ResponseMessage choice) {
        this.choices.add(choice);
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

}
