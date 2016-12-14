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

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Rik Schaaf aka CC007 (http://coolcat007.nl/)
 */
public class YMLNode {

    Map<String, Object> node;

    public YMLNode(Map<String, Object> node) {
        this.node = node;
    }

    public YMLNode getNode(String key) {
        return new YMLNode((Map<String, Object>) node.get(key));
    }

    public YMLNodeList getNodeList(String key) {
        return new YMLNodeList((List<Object>) node.get(key));
    }

    public String getString(String key) {
        return node.get(key).toString();
    }

    public int getInt(String key) {
        return Integer.parseInt(this.getString(key));
    }

    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(this.getString(key));
    }

    public Set<String> getKeys() {
        return node.keySet();
    }
}
