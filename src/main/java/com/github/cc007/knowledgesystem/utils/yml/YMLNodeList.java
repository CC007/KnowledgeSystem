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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Rik Schaaf aka CC007 (http://coolcat007.nl/)
 */
public class YMLNodeList {

    List<Object> nodes;

    public YMLNodeList(List<Object> nodes) {
        this.nodes = nodes;
    }

    public YMLNode getNode(int index) {
        return new YMLNode((Map<String, Object>) nodes.get(index));
    }

    public int getInt(int index) {
        return Integer.parseInt(getString(index));
    }

    public boolean getBoolean(int index) {
        return Boolean.parseBoolean(getString(index));
    }

    public String getString(int index) {
        return (String) nodes.get(index);
    }

    public int size() {
        return nodes.size();
    }

    public List<YMLNode> getNodes() {
        List<YMLNode> resultNodes = new ArrayList<>();
        for (Object node : nodes) {
            resultNodes.add(new YMLNode((Map<String, Object>) node));
        }
        return resultNodes;
    }

    public List<Integer> getIntegers() {
        List<Integer> resultNodes = new ArrayList<>();
        for (Object node : nodes) {
            resultNodes.add(Integer.parseInt((String) node));
        }
        return resultNodes;
    }

    public List<Boolean> getBooleans() {
        List<Boolean> resultNodes = new ArrayList<>();
        for (Object node : nodes) {
            resultNodes.add(Boolean.parseBoolean((String) node));
        }
        return resultNodes;
    }

    public List<String> getStrings() {
        List<String> resultNodes = new ArrayList<>();
        for (Object node : nodes) {
            resultNodes.add((String) node);
        }
        return resultNodes;
    }

}
