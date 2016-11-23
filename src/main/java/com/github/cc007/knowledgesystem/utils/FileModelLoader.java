/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cc007.knowledgesystem.utils;

import java.io.File;

/**
 *
 * @author Rik Schaaf aka CC007 (http://coolcat007.nl/)
 */
public abstract class FileModelLoader {

    protected final File file;

    public FileModelLoader(File file) {
        this.file = file;
    }

    public FileModelLoader(String fileName) {
        this.file = new File(fileName);
    }


}
