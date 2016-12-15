/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cc007.knowledgesystem.utils;

import com.github.cc007.knowledgesystem.model.knowledge.KnowledgeBase;
import com.github.cc007.knowledgesystem.model.knowledge.KnowledgeOrigin;
import com.github.cc007.knowledgesystem.model.knowledge.items.BooleanItem;
import com.github.cc007.knowledgesystem.model.knowledge.items.ChoiceSelectionItem;
import com.github.cc007.knowledgesystem.model.knowledge.items.FloatingPointItem;
import com.github.cc007.knowledgesystem.model.knowledge.items.KnowledgeItem;
import com.github.cc007.knowledgesystem.model.knowledge.items.MultipleChoiceSelectionItem;
import com.github.cc007.knowledgesystem.model.rules.Rule;
import com.github.cc007.knowledgesystem.model.rules.RuleBase;
import com.github.cc007.knowledgesystem.model.rules.RuleBuilder;
import com.github.cc007.knowledgesystem.model.rules.conditions.Condition;
import com.github.cc007.knowledgesystem.model.rules.conditions.EqualityCondition;
import com.github.cc007.knowledgesystem.utils.yml.YMLFile;
import com.github.cc007.knowledgesystem.utils.yml.YMLNode;
import com.github.cc007.knowledgesystem.utils.yml.YMLNodeList;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author annet
 */
public class YMLFileModelLoader extends FileModelLoader {
    
    public YMLFileModelLoader(String fileName) {
        super(fileName);
        
    }
    
    public void load(RuleBase ruleBase, KnowledgeBase knowledgeBase){
        try {
            YMLFile knowledge = new YMLFile(fileName);
            YMLNode root = knowledge.getRootNode();
            List<YMLNode> lists = root.getNodeList("lists").getNodes();
            List<YMLNode> questions = root.getNodeList("questions").getNodes();
            List<YMLNode> goals = root.getNodeList("goals").getNodes();
            List<YMLNode> rules = root.getNodeList("rules").getNodes();
            Map<String, List> listsMap = new HashMap<>();
            
            // Read the different options list from the file
            // Store these in the HashMap
            for (YMLNode list : lists) {
                String name = list.getString("name");
                List<String> options = list.getNodeList("options").getStrings();
                listsMap.put(name, options);                
            }
           
            // Read all the questions from the file
            // Add these to the Knowledge Base (KB)
            for (YMLNode question : questions) {
                String questionName = question.getString("name");
                String questionType = question.getString("type");
                String questionText = question.getString("question");
                String questionOptions;
                
                String tip = question.getString("tip");
                
                switch(questionType) {
                    case "choiceSelection": questionOptions = question.getString("options");
                                            knowledgeBase.setItem(new ChoiceSelectionItem(questionName, listsMap.get(questionOptions), KnowledgeOrigin.CHOICESELECTION, false).setQuestion(questionText));
                                            break;
                    case "floatingPoint":   //TODO
                                            break;
                    case "multiChoiceSelection": questionOptions = question.getString("options");
                                                 knowledgeBase.setItem(new MultipleChoiceSelectionItem(questionName, listsMap.get(questionOptions), KnowledgeOrigin.MULTICHOICESELECTION, false).setQuestion(questionText));
                                                 break;
                } 
                
            }
            
            // Read all the goals from the file
            // Add these to the KB
            for (YMLNode goal : goals) {
                String name = goal.getString("name");
                String response = goal.getString("response");
                
                knowledgeBase.setItem(new BooleanItem(name, KnowledgeOrigin.INFERRED, true).setGoalResponse(response));
            }
            
            // Read all the rules from the file
            // Add these to the KB
            for (YMLNode rule : rules) {
                // We first retrieve the consequence
                String consequenceName = rule.getNode("consequence").getString("name");
                KnowledgeItem consequence = knowledgeBase.getItem(consequenceName).copy().setValue(true);
                // Make the consequence the base of the rule
                RuleBuilder newRule = new RuleBuilder(consequence);
                List<YMLNode> conditions = rule.getNodeList("conditions").getNodes();
                Condition newCondition;
                
                // A rule can have multiple conditions
                // We add each condition to the existing rule
                for (YMLNode condition : conditions) {
                    String conditionName = condition.getNode("condition").getString("name");
                    String conditionType = condition.getNode("condition").getString("type");
                    String conditionValue = condition.getNode("condition").getString("value");
                    
                    switch(conditionType) {
                    case "equals":          newCondition = new EqualityCondition(conditionName, conditionValue);
                                            newRule.addCondition(newCondition);
                                            break;

                    case "notContains":     // TODO
                                            //this.checkList(listsMap, condition, conditionName, conditionValue, newRule);
                                            break;
                    } 
                }
                // After the consequence and all of the conditions have been added,
                // we build the rule and add it to the rule base
                ruleBase.addRule(newRule.build());
            }

        } catch (IOException ex) {
            Logger.getLogger(YMLFileModelLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
 
    }
    
    private void checkList( Map<String, List> listsMap, YMLNode condition, String conditionName, String conditionValue, RuleBuilder newRule) {
        List<String> options = listsMap.get(conditionValue);
        for (String option : options) {
            // TODO
            // newRule.addCondition();
        }
    }
    
}
