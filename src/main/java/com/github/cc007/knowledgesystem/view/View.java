/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cc007.knowledgesystem.view;

import com.github.cc007.knowledgesystem.model.knowledge.KnowledgeBase;
import com.github.cc007.knowledgesystem.model.knowledge.items.KnowledgeItem;

/**
 *
 * @author Rik Schaaf aka CC007 (http://coolcat007.nl/)
 */
public interface View {

    public <T> KnowledgeItem<T> inquire(KnowledgeItem<T> item);

    public void showResult(KnowledgeItem goal, KnowledgeBase knowledgeBase);
}
