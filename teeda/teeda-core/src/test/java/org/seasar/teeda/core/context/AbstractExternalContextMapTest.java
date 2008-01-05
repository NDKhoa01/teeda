/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.teeda.core.context;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContext;

import junit.framework.TestCase;

import org.seasar.framework.mock.servlet.MockServletContext;
import org.seasar.framework.mock.servlet.MockServletContextImpl;

public class AbstractExternalContextMapTest extends TestCase {

    private MockServletContext context_;

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        context_ = new MockServletContextImpl(null);
    }

    /*
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Constructor for TestAbstractExternalContextMap.
     * @param arg0
     */
    public AbstractExternalContextMapTest(String arg0) {
        super(arg0);
    }

    public void testGet() {
        ExternalContextMap map = new ExternalContextMap(context_);
        map.put("a", "A");
        assertEquals("A", map.get("a"));
    }

    public void testPutAll() {
        ExternalContextMap map = new ExternalContextMap(context_);
        Map m = new HashMap();
        m.put("a", "A");
        m.put("b", "B");
        map.putAll(m);
        assertEquals("A", map.get("a"));
        assertEquals("B", map.get("b"));
    }

    public void testIsEmpty() {
        ExternalContextMap map = new ExternalContextMap(context_);
        assertTrue(map.isEmpty());
        map.put("a", "A");
        assertFalse(map.isEmpty());
    }

    public void testClear() {
        ExternalContextMap map = new ExternalContextMap(context_);
        map.put("c", "C");
        map.put("d", "D");
        assertNotNull(map.get("c"));
        map.clear();
        assertNull(map.get("c"));
    }

    public void testContainsKey() {
        ExternalContextMap map = new ExternalContextMap(context_);
        map.put("e", "E");
        assertTrue(map.containsKey("e"));
        assertFalse(map.containsKey("not_a_key"));
    }

    public void testContainsValue() {
        ExternalContextMap map = new ExternalContextMap(context_);
        map.put("f", "F");
        map.put("g", "F");
        map.put("h", "H");
        assertTrue(map.containsValue("F"));
        assertFalse(map.containsValue("not_a_value"));
    }

    public void testEntrySet() {
        ExternalContextMap map = new ExternalContextMap(context_);
        map.put("a", "A");
        map.put("b", "B");
        for (Iterator itr = map.entrySet().iterator(); itr.hasNext();) {
            Object o = itr.next();
            assertNotNull(o);
            assertTrue(o instanceof Map.Entry);
        }
    }

    public void testKeySet() {
        context_.setAttribute("a", "A");
        context_.setAttribute("b", "B");
        ExternalContextMap map = new ExternalContextMap(context_);
        for (Iterator itr = map.keySet().iterator(); itr.hasNext();) {
            Object o = itr.next();
            assertNotNull(o);
            assertTrue(o instanceof String);
        }

    }

    public void testRemove() {
        context_.setAttribute("i", "I");
        ExternalContextMap map = new ExternalContextMap(context_);
        String s = (String) map.get("i");
        assertNotNull(s);
        map.remove("i");
        assertNull(map.get("i"));
    }

    public void testValues() {
        context_.setAttribute("j", "1");
        context_.setAttribute("k", "2");
        context_.setAttribute("l", "3");
        ExternalContextMap map = new ExternalContextMap(context_);
        Collection c = map.values();
        assertEquals(3, c.size());
        assertTrue(c.contains("1"));
        assertTrue(c.contains("2"));
        assertTrue(c.contains("3"));
    }

    private static class ExternalContextMap extends AbstractExternalContextMap {

        private ServletContext context_;

        public ExternalContextMap(ServletContext context) {
            context_ = context;
        }

        protected Object getAttribute(String key) {
            return context_.getAttribute(key);
        }

        protected void setAttribute(String key, Object value) {
            context_.setAttribute(key, value);
        }

        protected Enumeration getAttributeNames() {
            return context_.getAttributeNames();
        }

        protected void removeAttribute(String key) {
            context_.removeAttribute(key);
        }

    }
}
