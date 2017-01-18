/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cc007.knowledgesystem.ymlviewer;

import com.github.cc007.knowledgesystem.model.knowledge.KnowledgeBase;
import com.github.cc007.knowledgesystem.model.rules.RuleBase;
import com.github.cc007.knowledgesystem.model.rules.conditions.Condition;
import com.github.cc007.knowledgesystem.model.rules.conditions.EqualityCondition;
import com.github.cc007.knowledgesystem.model.rules.conditions.InclusionCondition;
import com.github.cc007.knowledgesystem.model.rules.conditions.IsSetCondition;
import com.github.cc007.knowledgesystem.model.rules.conditions.ValueCondition;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.HeadlessException;
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
public class YMLViewerPanel extends JPanel {

    private KnowledgeBase kb;
    private RuleBase rb;
    private int width;
    private int height;

    private Map<String, KnowledgeNode> goalNodes = new HashMap<>();
    private Map<String, KnowledgeNode> knowledgeNodes = new HashMap<>();
    private Map<Integer, RuleNode> ruleNodes = new HashMap<>();
    private List<ConditionConnection> conditionConnections = new ArrayList<>();
    private List<ConsequenceConnection> consequenceConnections = new ArrayList<>();

    public YMLViewerPanel(KnowledgeBase kb, RuleBase rb, int width, int height) {
        this.kb = kb;
        this.rb = rb;
        this.width = width;
        this.height = height;
        kb.forEach((item) -> {
            KnowledgeNode node = new KnowledgeNode(item.getName(), item.getType());
            if (item.isGoal()) {
                goalNodes.put(item.getName(), node);
            }
            knowledgeNodes.put(item.getName(), node);
        });

        for (int i = 0; i < rb.size(); i++) {
            RuleNode node = new RuleNode(i);
            KnowledgeNode consNode = knowledgeNodes.get(rb.getRule(i).getConsequence().getName());
            ruleNodes.put(i, node);
            for (Condition condition : rb.getRule(i).getConditions()) {
                KnowledgeNode condNode = knowledgeNodes.get(condition.getKnowledgeItemName());
                String type = null;
                Object value = null;
                if (condition instanceof EqualityCondition) {
                    if (((EqualityCondition) condition).isEqual()) {
                        type = "equals";
                    } else {
                        type = "notEquals";
                    }
                    value = ((EqualityCondition) condition).getValue();
                } else if (condition instanceof ValueCondition) {
                    switch (((ValueCondition) condition).getOperator()) {
                        case EQUAL:
                            type = "equals";
                            break;
                        case NOTEQUAL:
                            type = "notEquals";
                            break;
                        case GREATER:
                            type = "greater";
                            break;
                        case LESS:
                            type = "less";
                            break;
                    }
                    value = ((ValueCondition) condition).getValue();
                } else if (condition instanceof IsSetCondition) {
                    type = "isset";
                } else if (condition instanceof InclusionCondition) {
                    if (((InclusionCondition) condition).isIncluded()) {
                        type = "contains";
                    } else {
                        type = "notContains";
                    }
                    value = ((InclusionCondition) condition).getIndex();
                }
                conditionConnections.add(new ConditionConnection(node, condNode, type, value));
            }
            consequenceConnections.add(new ConsequenceConnection(node, consNode, rb.getRule(i).getConsequence().getValue()));
        }

    }

    @Override
    public void paintComponents(Graphics g) {
        int goalCount = goalNodes.size();
        int goalWidth = width / goalCount;
        int i = 0;
        for (KnowledgeNode node : goalNodes.values()) {
            g.setColor(Color.BLACK);
            g.drawRect((int) ((i + 0.5) * goalWidth), 20, 40, 30);
            i++;
        }
        g.drawRect(width, WIDTH, WIDTH, WIDTH);

    }

}
