/*
 * The MIT License
 *
 * Copyright 2015 Rik Schaaf aka CC007 <http://coolcat007.nl/>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.github.cc007.knowledgesystem.utils.yml;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author Rik Schaaf aka CC007 (http://coolcat007.nl/)
 */
public class YMLFile {

    Map<String, Object> root;

    public YMLFile() throws IOException {
        this("categories.yml");
    }

    public YMLFile(String filename) throws IOException {
        String result;
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream is = classLoader.getResourceAsStream(filename);
        result = IOUtils.toString(is);
        is.close();
        this.root = (Map) (new Yaml().load(result));
    }

    public YMLNode getRootNode() {
        return new YMLNode(root);
    }

    public Map<String, Object> getRootMap() {
        return root;
    }

    public static Map<String, Object> getNode(String key, Map<String, Object> node) {
        return (Map) node.get(key);
    }

    public static List<Object> getList(String key, Map<String, Object> node) {
        return (List) node.get(key);
    }

    public static String getString(String key, Map<String, Object> node) {
        return node.get(key).toString();
    }

    public static int getInt(String key, Map<String, Object> node) {
        return Integer.parseInt(getString(key, node));
    }

    public static boolean getBoolean(String key, Map<String, Object> node) {
        return Boolean.parseBoolean(getString(key, node));
    }
}
