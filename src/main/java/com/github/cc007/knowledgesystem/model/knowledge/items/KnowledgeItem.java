/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cc007.knowledgesystem.model.knowledge.items;

import com.github.cc007.knowledgesystem.model.knowledge.KnowledgeOrigin;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Rik Schaaf aka CC007 (http://coolcat007.nl/)
 * @param <T> value type
 */
public abstract class KnowledgeItem<T> {

    protected final String name;
    protected final KnowledgeOrigin origin;
    protected final boolean goal;
    protected final String type;
    
    protected T value;
    protected String question;
    protected String tip;
    protected String goalResponse;


    public KnowledgeItem(String name, KnowledgeOrigin origin, boolean goal) {
        this.name = name;
        this.origin = origin;
        this.goal = goal;
        this.type = getType();

        this.value = null;
        this.question = null;
        this.tip = null;
        this.goalResponse = null;
    }

    public KnowledgeItem(String name, T value, KnowledgeOrigin origin, boolean goal) {
        this(name, origin, goal);
        setValue(value);
    }

    public KnowledgeItem(KnowledgeItem<T> item) {
        this(item.name, item.origin, item.goal);
        setValue(item.value);
        setQuestion(item.question);
        setTip(item.tip);
        setGoalResponse(item.goalResponse);
    }

    public abstract String getType();

    public abstract KnowledgeItem<T> copy();

    public final KnowledgeItem<T> setValue(T value) {
        this.value = value;
        return this;
    }

    public final KnowledgeItem<T> setQuestion(String question) {
        this.question = question;
        return this;
    }

    public final KnowledgeItem<T> setTip(String tip) {
        this.tip = tip;
        return this;
    }

    public final KnowledgeItem<T> setGoalResponse(String goalResponse) {
        this.goalResponse = goalResponse;
        return this;
    }

    public String getName() {
        return name;
    }

    public KnowledgeOrigin getOrigin() {
        return origin;
    }

    public boolean isGoal() {
        return goal;
    }

    public T getValue() {
        return value;
    }

    public boolean isValueSet() {
        return value != null;
    }

    public String getQuestion() {
        if (question == null) {
            return getDefaultQuestion();
        }
        return question;
    }

    public abstract String getDefaultQuestion();

    public String getTip() {
        return tip;
    }

    public boolean hasTip() {
        return tip != null;
    }

    public String getGoalResponse() {
        if (goalResponse == null) {
            return getDefaultGoalResponse();
        }
        String result = goalResponse;
        Pattern p = Pattern.compile("%value%");
        Matcher m = p.matcher(result);
        result = m.replaceAll(value + "");
        return result;
    }

    public String getDefaultGoalResponse() {
        return "The value of the goal variable " + name + " is: " + value;
    }

}
