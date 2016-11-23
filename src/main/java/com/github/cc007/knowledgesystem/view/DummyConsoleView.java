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
        switch (item.getOrigin()) {
            case CHOICESELECTION:
                System.out.println("[view]  Choice selection question selected");
                ChoiceSelectionItem csi = (ChoiceSelectionItem) item;
                System.out.println("Choose one of the following for " + csi.getName() + ":");
                int i = 0;
                for (String option : csi.getOptions()) {
                    System.out.println(i + ": " + option);
                    i++;
                }
                System.out.println();
                int num = in.nextInt();
                newItem = new ChoiceSelectionItem(csi, num);
                return newItem;
            case TEXTINPUT:
                System.out.println("[view]  Text input question selected");
                StringItem si = (StringItem) item;
                System.out.println("Give the text value of " + si.getName());
                System.out.println();
                String textValue = in.next();
                newItem = new StringItem(si, textValue);
                return newItem;
            case VALUEINPUT:
                System.out.print("[view]  Value input question selected ");
                if (item instanceof BooleanItem) {
                    System.out.println("(boolean)");
                    BooleanItem bi = (BooleanItem) item;
                    System.out.println("Give the boolean value of " + bi.getName());
                    System.out.println();
                    boolean value = in.nextBoolean();
                    newItem = new BooleanItem(bi, value);
                    return newItem;
                }
                if (item instanceof IntegerItem) {
                    System.out.println("(int)");
                    IntegerItem ii = (IntegerItem) item;
                    System.out.println("Give the integer value of " + ii.getName());
                    System.out.println();
                    int value = in.nextInt();
                    newItem = new IntegerItem(ii, value);
                    return newItem;
                }
                if (item instanceof FloatingPointItem) {
                    System.out.println("(double)");
                    FloatingPointItem fpi = (FloatingPointItem) item;
                    System.out.println("Give the decimal value of " + fpi.getName());
                    System.out.println();
                    double value = in.nextDouble();
                    newItem = new FloatingPointItem(fpi, value);
                    return newItem;
                }

        }
        return null;
    }

    @Override
    public void showResult(KnowledgeItem goal, KnowledgeBase knowledgeBase) {
        System.out.println("The value of the goal variable " + goal.getName() + " is: " + goal.getValue());
    }

}
