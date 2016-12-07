/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cc007.knowledgesystem.model.knowledge.items;

import com.github.cc007.knowledgesystem.model.knowledge.KnowledgeOrigin;
import java.util.List;

/**
 *
 * @author Rik Schaaf aka CC007 (http://coolcat007.nl/)
 */
public class ChoiceSelectionItem extends KnowledgeItem<Integer> {

    protected final List<String> options;

    public ChoiceSelectionItem(String name, List<String> options, KnowledgeOrigin origin, boolean goal) {
        super(name, origin, goal);
        this.options = options;
    }

    public ChoiceSelectionItem(String name, List<String> options, Integer index, KnowledgeOrigin origin, boolean goal) {
        super(name, index, origin, goal);
        this.options = options;
    }

    public ChoiceSelectionItem(ChoiceSelectionItem item) {
        super(item);
        this.options = item.options;
    }

    @Override
    protected String getType() {
        return "integer";
    }

    @Override
    public ChoiceSelectionItem copy() {
        return new ChoiceSelectionItem(this);
    }

    @Override
    public String getDefaultQuestion() {
        return "Choose one of the following for " + name + ":";
    }

    public List<String> getOptions() {
        return options;
    }

    public String getSelectedValue() {
        return options.get(value);
    }

    public Integer getSelectedIndex() {
        return value;
    }

    public String getValue(Integer index) {
        return options.get(index);
    }

    public Integer getIndex(String value) {
        return options.indexOf(value);
    }

}
