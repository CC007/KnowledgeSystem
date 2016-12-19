/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cc007.knowledgesystem;

import com.github.cc007.knowledgesystem.server.RESTHandler;
import com.github.cc007.knowledgesystem.server.Session;
import com.github.cc007.knowledgesystem.utils.ModelLoader;
import com.github.cc007.knowledgesystem.utils.YMLFileModelLoader;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 *
 * @author Rik Schaaf aka CC007 (http://coolcat007.nl/)
 */
public class ServerProgram {
    public static void main(String[] args) {   
        Logger.getLogger(Session.class.getName()).info("set yml file model loader as current loader");
        ModelLoader.setCurrentLoader(new YMLFileModelLoader("kennis.yml"));     
        Thread t = new Thread(new RESTHandler());
        t.start();
        Scanner in = new Scanner(System.in);
    }
}
