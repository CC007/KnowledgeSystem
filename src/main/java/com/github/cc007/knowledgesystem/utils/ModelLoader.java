/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cc007.knowledgesystem.utils;

import com.github.cc007.knowledgesystem.model.knowledge.KnowledgeBase;
import com.github.cc007.knowledgesystem.model.rules.RuleBase;
import java.io.File;
import java.net.URI;

/**
 *
 * @author Rik Schaaf aka CC007 (http://coolcat007.nl/)
 */
public abstract class ModelLoader {

    private static ModelLoader currentLoader;

    public static void setCurrentRuleLoader(ModelLoader newLoader) {
        currentLoader = newLoader;
    }

    public static ModelLoader getCurrentRuleLoader() {
        return currentLoader;
    }
    public static void loadModel(RuleBase ruleBase, KnowledgeBase knowledgeBase){
        getCurrentRuleLoader().load(ruleBase, knowledgeBase);
    }
    public abstract void load(RuleBase ruleBase, KnowledgeBase knowledgeBase);
}
