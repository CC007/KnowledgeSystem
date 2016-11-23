/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cc007.knowledgesystem;

import com.github.cc007.knowledgesystem.controller.InferenceSystem;
import com.github.cc007.knowledgesystem.model.knowledge.KnowledgeBase;
import com.github.cc007.knowledgesystem.model.rules.RuleBase;
import com.github.cc007.knowledgesystem.utils.HardCodedModelLoader;
import com.github.cc007.knowledgesystem.utils.ModelLoader;
import com.github.cc007.knowledgesystem.view.DummyConsoleView;
import com.github.cc007.knowledgesystem.view.View;

/**
 *
 * @author Rik Schaaf aka CC007 (http://coolcat007.nl/)
 */
public class Program {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        KnowledgeBase knowledgeBase = new KnowledgeBase();
        RuleBase ruleBase = new RuleBase();
        View view = new DummyConsoleView();
        ModelLoader.setCurrentRuleLoader(new HardCodedModelLoader());
        ModelLoader.loadModel(ruleBase, knowledgeBase);
        InferenceSystem system = new InferenceSystem(ruleBase, knowledgeBase, view);
        system.start();
    }
    
}
