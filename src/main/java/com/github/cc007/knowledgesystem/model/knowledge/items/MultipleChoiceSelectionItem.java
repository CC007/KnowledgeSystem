/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cc007.knowledgesystem.model.knowledge.items;

import com.github.cc007.knowledgesystem.model.knowledge.KnowledgeOrigin;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Rik Schaaf aka CC007 (http://coolcat007.nl/)
 */
public class MultipleChoiceSelectionItem extends KnowledgeItem<Set<Integer>> {

    protected final List<String> options;

    public MultipleChoiceSelectionItem(String name, List<String> options, KnowledgeOrigin origin, boolean goal) {
        super(name, origin, goal);
        this.options = options;
    }

    public MultipleChoiceSelectionItem(String name, List<String> options, Set<Integer> indexes, KnowledgeOrigin origin, boolean goal) {
        super(name, indexes, origin, goal);
        this.options = options;
    }

    public MultipleChoiceSelectionItem(MultipleChoiceSelectionItem item) {
        super(item);
        this.options = item.options;
    }

    @Override
    public String getType() {
        return "indexlist";
    }

    @Override
    public MultipleChoiceSelectionItem copy() {
        return new MultipleChoiceSelectionItem(this);
    }

    @Override
    public String getDefaultQuestion() {
        return "Choose one of the following for " + name + ":";
    }

    public List<String> getOptions() {
        return options;
    }

    public Set<String> getSelectedValues() {
        Set<String> selectedValues = new HashSet<>();
        for (Integer index : value) {
            selectedValues.add(options.get(index));
        }
        return selectedValues;
    }

    public Set<Integer> getSelectedIndices() {
        return value;
    }

    public List<String> getValues(List<Integer> indices) {
        List<String> values = new ArrayList<>();
        for (Integer index : indices) {
            values.add(options.get(index));
        }
        return values;
    }

    public List<Integer> getIndices(List<String> values) {
        List<Integer> indices = new ArrayList<>();
        for (String value : values) {
            indices.add(options.indexOf(value));
        }
        return indices;
    }

    public String getValue(Integer index) {
        return options.get(index);
    }

    public Integer getIndex(String value) {
        return options.indexOf(value);
    }

}
