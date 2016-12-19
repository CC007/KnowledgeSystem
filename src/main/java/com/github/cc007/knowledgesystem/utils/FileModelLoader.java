/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cc007.knowledgesystem.utils;

/**
 *
 * @author Rik Schaaf aka CC007 (http://coolcat007.nl/)
 */
public abstract class FileModelLoader extends ModelLoader{

    protected final String fileName;

    public FileModelLoader(String fileName) {
        this.fileName = fileName;
    }

}
