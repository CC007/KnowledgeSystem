/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cc007.knowledgesystem.utils;

import java.io.File;

/**
 *
 * @author annet
 */
public class YMLFileModelLoader extends FileModelLoader {
    
    public YMLFileModelLoader(File file) {
        super(file);
    }
    
    public YMLFileModelLoader(String fileName) {
        super(fileName);
    }
    
}
