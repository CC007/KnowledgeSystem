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
public class ConditionConnection {

    private RuleNode rule;
    private KnowledgeNode condition;
    private String conditionType;
    private Object value;

    public ConditionConnection(RuleNode rule, KnowledgeNode condition, String conditionType, Object value) {
        this.rule = rule;
        this.condition = condition;
        this.conditionType = conditionType;
        this.value = value;
    }

    public RuleNode getRule() {
        return rule;
    }

    public void setRule(RuleNode rule) {
        this.rule = rule;
    }

    public KnowledgeNode getCondition() {
        return condition;
    }

    public void setCondition(KnowledgeNode condition) {
        this.condition = condition;
    }

    public String getConditionType() {
        return conditionType;
    }

    public void setConditionType(String conditionType) {
        this.conditionType = conditionType;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}
