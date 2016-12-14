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
import com.github.cc007.knowledgesystem.model.knowledge.items.MultipleChoiceSelectionItem;
import com.github.cc007.knowledgesystem.model.rules.RuleBase;
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
            Map<String, List> listsMap = new HashMap<>();
            for (YMLNode list : lists) {
                String name = list.getString("name");
                List<String> options = list.getNodeList("options").getStrings();
                listsMap.put(name, lists);                
            }
            
            knowledgeBase.setItem(new ChoiceSelectionItem("leerling", jaNeeList, KnowledgeOrigin.CHOICESELECTION, false).setQuestion("Gaat het om een minderjarige die naar school gaat?"));
            name, type, question, options, tip
            
            
            List<YMLNode> questions = root.getNodeList("questions").getNodes();
            for (YMLNode question : questions) {
                String name = question.getString("name");
                String type = question.getString("type");
                String questionText = question.getString("question");
                
                String tip = question.getString("tip");
                
                switch(type) {
                    case "choiceSelection": String options = question.getString("options");
                                            knowledgeBase.setItem(new ChoiceSelectionItem(name, listsMap.get(options), KnowledgeOrigin.CHOICESELECTION, false).setQuestion(questionText));
                                            break;
                    case "floatingPoint":   //TODO
                    case "multiChoiceSelection": String options = question.getString("options");
                                                 knowledgeBase.setItem(new MultipleChoiceSelectionItem(name, listsMap.get(options), KnowledgeOrigin.MULTICHOICESELECTION, false).setQuestion(questionText));
                                                 break;
                    
                } 
                
            }
            
            
            YMLNodeList goals = root.getNodeList("goals");
            YMLNodeList rules = root.getNodeList("rules");
            
            
            
            
            
            
            
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(YMLFileModelLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
 
    }
    
}
