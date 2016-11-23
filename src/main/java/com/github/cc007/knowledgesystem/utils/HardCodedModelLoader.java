/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cc007.knowledgesystem.utils;

import com.github.cc007.knowledgesystem.model.knowledge.KnowledgeBase;
import com.github.cc007.knowledgesystem.model.knowledge.KnowledgeOrigin;
import com.github.cc007.knowledgesystem.model.knowledge.items.ChoiceSelectionItem;
import com.github.cc007.knowledgesystem.model.knowledge.items.FloatingPointItem;
import com.github.cc007.knowledgesystem.model.knowledge.items.IntegerItem;
import com.github.cc007.knowledgesystem.model.knowledge.items.KnowledgeItem;
import com.github.cc007.knowledgesystem.model.knowledge.items.StringItem;
import com.github.cc007.knowledgesystem.model.rules.RuleBase;
import com.github.cc007.knowledgesystem.model.rules.RuleBuilder;
import com.github.cc007.knowledgesystem.model.rules.conditions.Condition;
import com.github.cc007.knowledgesystem.model.rules.conditions.EqualityCondition;
import com.github.cc007.knowledgesystem.model.rules.conditions.ValueCondition;
import com.github.cc007.knowledgesystem.model.rules.conditions.ValueOperator;
import java.util.Arrays;

/**
 *
 * @author Rik Schaaf aka CC007 (http://coolcat007.nl/)
 */
public class HardCodedModelLoader extends ModelLoader {

    @Override
    public void load(RuleBase ruleBase, KnowledgeBase knowledgeBase) {
        //fill the knowledge base with the variables
        knowledgeBase.setItem(new IntegerItem("rpm", 1500, KnowledgeOrigin.GIVEN, false));
        knowledgeBase.setItem(new StringItem("alarm", KnowledgeOrigin.INFERRED, false));
        knowledgeBase.setItem(new ChoiceSelectionItem("leaking", Arrays.asList("yes", "no"), KnowledgeOrigin.CHOICESELECTION, false));
        knowledgeBase.setItem(new StringItem("problem", KnowledgeOrigin.INFERRED, false));
        knowledgeBase.setItem(new FloatingPointItem("OVE", KnowledgeOrigin.VALUEINPUT, false));
        knowledgeBase.setItem(new StringItem("hello", KnowledgeOrigin.INFERRED, true));
        
        //fill the rule base with the rules
        Condition moreThan1000RPM = new ValueCondition("rpm", 1000, ValueOperator.GREATER);
        Condition alarmIsNotOn = new EqualityCondition("alarm", "on", false);
        KnowledgeItem alarmOn = new StringItem((StringItem) knowledgeBase.getItem("alarm"), "on");
        ruleBase.addRule(new RuleBuilder(alarmOn).addCondition(alarmIsNotOn).addCondition(moreThan1000RPM).build());
        Condition alarmIsOn = new EqualityCondition("alarm", "on", true);
        Condition pumpIsLeaking = new EqualityCondition("leaking", 0, true);
        Condition hasNoBrokenSealProblem = new EqualityCondition("problem", "broken seal", false);
        KnowledgeItem brokenSealProblem = new StringItem((StringItem) knowledgeBase.getItem("problem"), "broken seal");
        ruleBase.addRule(new RuleBuilder(brokenSealProblem).addCondition(hasNoBrokenSealProblem).addCondition(alarmIsOn).addCondition(pumpIsLeaking).build());
        Condition ove9000 = new ValueCondition("OVE", 9000.0, ValueOperator.GREATER);
        Condition hasBrokenSealProblem = new EqualityCondition("problem", "broken seal", true);
        KnowledgeItem helloWorld = new StringItem((StringItem) knowledgeBase.getItem("hello"), "world");
        ruleBase.addRule(new RuleBuilder(helloWorld).addCondition(ove9000).addCondition(hasBrokenSealProblem).build());
    }
}
