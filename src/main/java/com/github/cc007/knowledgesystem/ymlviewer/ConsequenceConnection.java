/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cc007.knowledgesystem.ymlviewer;

/**
 *
 * @author Rik
 */
public class ConsequenceConnection {

    private RuleNode rule;
    private KnowledgeNode consequence;
    private Object value;

    public ConsequenceConnection(RuleNode rule, KnowledgeNode consequence, Object value) {
        this.rule = rule;
        this.consequence = consequence;
        this.value = value;
    }

    public RuleNode getRule() {
        return rule;
    }

    public KnowledgeNode getConsequence() {
        return consequence;
    }

    public Object getValue() {
        return value;
    }

    public void setRule(RuleNode rule) {
        this.rule = rule;
    }

    public void setConsequence(KnowledgeNode consequence) {
        this.consequence = consequence;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}
