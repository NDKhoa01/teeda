/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.html.impl;

import java.io.File;

import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.ResourceUtil;

import junit.framework.TestCase;

/**
 * @author higa
 *
 */
public class ActionDescImplTest extends TestCase {
	
    public void testIsValid() throws Exception {
        ActionDescImpl ad = new ActionDescImpl(FooAction.class, "fooAction");
        assertTrue("1", ad.isValid("doAaa"));
        assertFalse("2", ad.isValid("xxx"));
        assertFalse("3", ad.isValid(null));
    }

    public void testIsModified() throws Exception {
        File file = ResourceUtil.getResourceAsFile(
                ClassUtil.getResourcePath(FooAction.class));
        ActionDescImpl ad = new ActionDescImpl(FooAction.class, "fooAction", file);
        assertFalse("1", ad.isModified());
        Thread.sleep(1000);
        file.setLastModified(System.currentTimeMillis());
        assertTrue("2", ad.isModified());
    }
}