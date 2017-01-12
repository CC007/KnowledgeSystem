/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cc007.knowledgesystem.model.rules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 * @author Rik Schaaf aka CC007 (http://coolcat007.nl/)
 */
public class RuleBase implements Iterable<Rule> {

    private final List<Rule> rules;

    public RuleBase() {
        this.rules = new ArrayList<>();
    }

    public void addRule(Rule rule) {
        rules.add(rule);
    }

    public Iterator<Rule> iterator() {
        return rules.iterator();
    }

    public Stream<Rule> stream() {
        return rules.stream();
    }

    public int size() {
        return rules.size();
    }

    public Rule getRule(int index) {
        return rules.get(index);
    }

}
