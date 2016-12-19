/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cc007.knowledgesystem.utils;

import com.github.cc007.knowledgesystem.model.knowledge.KnowledgeBase;
import com.github.cc007.knowledgesystem.model.rules.RuleBase;

/**
 *
 * @author Rik Schaaf aka CC007 (http://coolcat007.nl/)
 */
public abstract class ModelLoader {

    private static ModelLoader currentLoader;

    public static void setCurrentLoader(ModelLoader newLoader) {
        currentLoader = newLoader;
    }

    public static ModelLoader getCurrentLoader() {
        return currentLoader;
    }
    public static void loadModel(RuleBase ruleBase, KnowledgeBase knowledgeBase){
        currentLoader.load(ruleBase, knowledgeBase);
    }
    public abstract void load(RuleBase ruleBase, KnowledgeBase knowledgeBase);
}
