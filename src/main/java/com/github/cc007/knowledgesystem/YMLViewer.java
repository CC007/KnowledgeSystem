/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cc007.knowledgesystem;

import com.github.cc007.knowledgesystem.model.knowledge.KnowledgeBase;
import com.github.cc007.knowledgesystem.model.rules.RuleBase;
import com.github.cc007.knowledgesystem.utils.ModelLoader;
import com.github.cc007.knowledgesystem.utils.YMLFileModelLoader;
import com.github.cc007.knowledgesystem.ymlviewer.ConditionConnection;
import com.github.cc007.knowledgesystem.ymlviewer.ConsequenceConnection;
import com.github.cc007.knowledgesystem.ymlviewer.KnowledgeNode;
import com.github.cc007.knowledgesystem.ymlviewer.RuleNode;
import com.github.cc007.knowledgesystem.ymlviewer.YMLViewerPanel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Rik
 */
public class YMLViewer {
    private final KnowledgeBase knowledgeBase;
    private final RuleBase ruleBase;

    public YMLViewer() {
        this.knowledgeBase = new KnowledgeBase();
        this.ruleBase = new RuleBase();
        ModelLoader.loadModel(ruleBase, knowledgeBase);
        JPanel panel = new YMLViewerPanel(knowledgeBase, ruleBase, 1200, 800);
        JFrame frame = new JFrame();
        frame.add(panel);
        frame.setSize(1200, 800);
        frame.setVisible(true);
    }
    
    
    public static void main(String[] args) {
        ModelLoader.setCurrentLoader(new YMLFileModelLoader("kennis.yml"));
        YMLViewer viewer = new YMLViewer();
    }
}
