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
import com.github.cc007.knowledgesystem.model.knowledge.items.IntegerItem;
import com.github.cc007.knowledgesystem.model.knowledge.items.KnowledgeItem;
import com.github.cc007.knowledgesystem.model.knowledge.items.MultipleChoiceSelectionItem;
import com.github.cc007.knowledgesystem.model.rules.RuleBase;
import com.github.cc007.knowledgesystem.model.rules.RuleBuilder;
import com.github.cc007.knowledgesystem.model.rules.conditions.Condition;
import com.github.cc007.knowledgesystem.model.rules.conditions.EqualityCondition;
import com.github.cc007.knowledgesystem.model.rules.conditions.InclusionCondition;
import com.github.cc007.knowledgesystem.utils.yml.YMLFile;
import com.github.cc007.knowledgesystem.utils.yml.YMLNode;
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

    private final Map<String, List> listsMap;

    private static final Logger log = Logger.getLogger(YMLFileModelLoader.class.getName());

    public YMLFileModelLoader(String fileName) {
        super(fileName);
        this.listsMap = new HashMap<>();

    }

    public void load(RuleBase ruleBase, KnowledgeBase knowledgeBase) {
        try {
            log.info("Open YAML file");
            YMLFile knowledge = new YMLFile(fileName);
            log.info("Get the YAML root node");
            YMLNode root = knowledge.getRootNode();

            // Read the different options list from the file
            // Store these in the HashMap
            setLists(root);

            // Read all the questions from the file
            // Add these to the Knowledge Base (KB)
            setQuestions(root, knowledgeBase);

            // Read all the goals from the file
            // Add these to the KB
            setGoals(root, knowledgeBase);

            // Read all the rules from the file
            // Add these to the RuleBase (RB)
            // Add previously unknown knowledge items to the KB
            setRules(root, knowledgeBase, ruleBase);

        } catch (IOException | NullPointerException | UnsupportedOperationException ex) {
            Logger.getLogger(YMLFileModelLoader.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void setLists(YMLNode root) {
        log.info("Set the lists");
        List<YMLNode> lists = root.getNodeList("lists").getNodes();

        for (YMLNode list : lists) {
            String name = list.getString("name");
            List<String> options = list.getNodeList("options").getStrings();
            listsMap.put(name, options);
        }
    }

    private void setQuestions(YMLNode root, KnowledgeBase knowledgeBase) {
        log.info("Put the question knowledge items into the knowledge base");
        List<YMLNode> questions = root.getNodeList("questions").getNodes();

        for (YMLNode question : questions) {
            String questionName = question.getString("name");
            String questionType = question.getString("type");
            String questionText = question.getString("question");
            String questionTip = question.getString("tip");
            String questionOptions;
            log.info(questionType);
            KnowledgeItem newItem = null;
            switch (questionType) {
                case "choiceSelection":
                    questionOptions = question.getString("options");
                    newItem = new ChoiceSelectionItem(questionName, listsMap.get(questionOptions), KnowledgeOrigin.CHOICESELECTION, false);
                    break;
                case "multipleChoiceSelection":
                    questionOptions = question.getString("options");
                    newItem = new MultipleChoiceSelectionItem(questionName, listsMap.get(questionOptions), KnowledgeOrigin.MULTICHOICESELECTION, false);
                    break;
                //TODO other types
            }
            newItem.setQuestion(questionText);
            if (!"".equals(questionTip)) {
                newItem.setTip(questionTip);
            }

            knowledgeBase.setItem(newItem);
        }
    }

    private void setGoals(YMLNode root, KnowledgeBase knowledgeBase) {
        log.info("Put the goal knowledge items into the knowledge base");
        List<YMLNode> goals = root.getNodeList("goals").getNodes();

        for (YMLNode goal : goals) {
            String name = goal.getString("name");
            String response = goal.getString("response");

            //TODO other types
            knowledgeBase.setItem(new BooleanItem(name, KnowledgeOrigin.INFERRED, true).setGoalResponse(response));
        }
    }

    private void setRules(YMLNode root, KnowledgeBase knowledgeBase, RuleBase ruleBase) {
        List<YMLNode> rules = root.getNodeList("rules").getNodes();
        for (YMLNode rule : rules) {
            // We first retrieve the knowledge item
            String consequenceName = rule.getNode("consequence").getString("name");
            String consequenceType = rule.getNode("consequence").getString("type");
            KnowledgeItem knowledgeItem = knowledgeBase.getItem(consequenceName);
            if (knowledgeItem == null) {
                switch (consequenceType) {
                    case "boolean":
                        knowledgeItem = new BooleanItem(consequenceName, KnowledgeOrigin.INFERRED, false);
                        break;
                    case "integer":
                        knowledgeItem = new IntegerItem(consequenceName, KnowledgeOrigin.INFERRED, false);
                        break;
                    //TODO other types
                    default:
                        throw new UnsupportedOperationException("Only boolean supported up until now");

                }
                knowledgeBase.setItem(knowledgeItem);
            }

            // Then we create a copy of the item to be used as consequence and set its value
            KnowledgeItem consequence = knowledgeItem.copy();
            switch (consequenceType) {
                case "boolean":
                    consequence.setValue(rule.getNode("consequence").getBoolean("value"));
                    break;
                //TODO other types
            }

            // Make the consequence the base of the rule and add the condition that the consequence isn't true yet
            RuleBuilder newRule = new RuleBuilder(consequence);

            // A rule can have multiple conditions
            // We add each condition to the existing rule
            List<YMLNode> conditions = rule.getNodeList("conditions").getNodes();
            for (YMLNode condition : conditions) {
                Condition newCondition = null;
                String conditionName = condition.getString("name");
                String conditionType = condition.getString("type");
                Object conditionValue = condition.getString("value");
                KnowledgeItem item = knowledgeBase.getItem(conditionName);
                if (item instanceof ChoiceSelectionItem) {
                    conditionValue = ((ChoiceSelectionItem) item).getIndex((String) conditionValue);
                } else if (item instanceof MultipleChoiceSelectionItem) {
                    conditionValue = ((MultipleChoiceSelectionItem) item).getIndices((List<String>) conditionValue);
                }
                switch (conditionType) {
                    case "equals":
                        newCondition = new EqualityCondition(conditionName, conditionValue);
                        break;
                    case "notEquals":
                        newCondition = new EqualityCondition(conditionName, conditionValue, false);
                        break;
                    case "contains":
                        newCondition = new InclusionCondition(conditionName, knowledgeBase, (String) conditionValue);
                        break;
                    case "notContains":
                        newCondition = new InclusionCondition(conditionName, knowledgeBase, (String) conditionValue, false);
                        break;
                    default:
                        throw new UnsupportedOperationException("Value conditions not implemented yet");
                    //TODO value conditions
                }
                newRule.addCondition(newCondition);
            }
            // After the consequence and all of the conditions have been added,
            // we build the rule and add it to the rule base
            ruleBase.addRule(newRule.build());
        }
    }
}
