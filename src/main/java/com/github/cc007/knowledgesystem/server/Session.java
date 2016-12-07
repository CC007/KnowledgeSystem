/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cc007.knowledgesystem.server;

import com.github.cc007.knowledgesystem.controller.InferenceSystem;
import com.github.cc007.knowledgesystem.model.knowledge.KnowledgeBase;
import com.github.cc007.knowledgesystem.model.knowledge.items.KnowledgeItem;
import com.github.cc007.knowledgesystem.model.rules.RuleBase;
import com.github.cc007.knowledgesystem.utils.HardCodedModelLoader;
import com.github.cc007.knowledgesystem.utils.ModelLoader;
import com.github.cc007.knowledgesystem.view.DummyConsoleView;
import com.github.cc007.knowledgesystem.view.RESTView;
import com.github.cc007.knowledgesystem.view.View;

/**
 *
 * @author Rik Schaaf aka CC007 (http://coolcat007.nl/)
 */
public class Session implements Runnable {

    private final KnowledgeBase knowledgeBase;
    private final RuleBase ruleBase;
    private final RESTView view;

    public Session(RESTHandler restHandler) {
        this.knowledgeBase = new KnowledgeBase();
        this.ruleBase = new RuleBase();
        ModelLoader.setCurrentRuleLoader(new HardCodedModelLoader());
        ModelLoader.loadModel(ruleBase, knowledgeBase);
        this.view = new RESTView(restHandler);
    }

    @Override
    public void run() {
        InferenceSystem system = new InferenceSystem(ruleBase, knowledgeBase, view);
        system.start();
    }

    public RESTView getView() {
        return view;
    }

}
