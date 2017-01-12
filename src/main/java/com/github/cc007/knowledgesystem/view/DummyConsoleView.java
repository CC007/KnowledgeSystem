/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cc007.knowledgesystem.view;

import com.github.cc007.knowledgesystem.model.knowledge.KnowledgeBase;
import com.github.cc007.knowledgesystem.model.knowledge.items.BooleanItem;
import com.github.cc007.knowledgesystem.model.knowledge.items.ChoiceSelectionItem;
import com.github.cc007.knowledgesystem.model.knowledge.items.FloatingPointItem;
import com.github.cc007.knowledgesystem.model.knowledge.items.IntegerItem;
import com.github.cc007.knowledgesystem.model.knowledge.items.KnowledgeItem;
import com.github.cc007.knowledgesystem.model.knowledge.items.StringItem;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 *
 * @author Rik Schaaf aka CC007 (http://coolcat007.nl/)
 */
public class DummyConsoleView implements View {

    @Override
    public <T> KnowledgeItem<T> inquire(KnowledgeItem<T> item) {
        Scanner in = new Scanner(System.in);
        KnowledgeItem newItem;
        System.out.println("[view] Find out which question to ask");
        System.out.println(item.getQuestion());
        if (item.hasTip()) {
            System.out.println(item.getTip());
        }
        switch (item.getOrigin()) {
            case CHOICESELECTION:
                System.out.println("[view]  Choice selection question selected");
                ChoiceSelectionItem csi = (ChoiceSelectionItem) item;
                int i = 0;
                for (String option : csi.getOptions()) {
                    System.out.println(i + ": " + option);
                    i++;
                }
                System.out.println();
                int num = in.nextInt();
                csi.setValue(num);
                newItem = csi;
                return newItem;
            case TEXTINPUT:
                System.out.println("[view]  Text input question selected");
                StringItem si = (StringItem) item;
                System.out.println();
                String textValue = in.next();
                si.setValue(textValue);
                newItem = si;
                return newItem;
            case INTEGERINPUT:
                System.out.println("[view]  Value input question selected ");
                if (item instanceof BooleanItem) {
                    System.out.println("(boolean)");
                    BooleanItem bi = (BooleanItem) item;
                    System.out.println();
                    boolean value = in.nextBoolean();
                    bi.setValue(value);
                    newItem = bi;
                    return newItem;
                }
                if (item instanceof IntegerItem) {
                    System.out.println("(int)");
                    IntegerItem ii = (IntegerItem) item;
                    System.out.println();
                    int value = in.nextInt();
                    ii.setValue(value);
                    newItem = ii;
                    return newItem;
                }
                if (item instanceof FloatingPointItem) {
                    System.out.println("(double)");
                    FloatingPointItem fpi = (FloatingPointItem) item;
                    System.out.println();
                    double value = in.nextDouble();
                    fpi.setValue(value);
                    newItem = fpi;
                    return newItem;
                }

        }
        return null;
    }

    @Override
    public void showResult(KnowledgeItem goal, KnowledgeBase knowledgeBase) {
        if (goal == null) {
            System.out.println("No goal could be reached");
        } else {
            System.out.println(goal.getGoalResponse());
        }
    }

}
