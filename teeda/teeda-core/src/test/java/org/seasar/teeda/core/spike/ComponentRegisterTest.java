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
package org.seasar.teeda.core.spike;

import junit.framework.TestCase;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.InitMethodDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.ConstantAnnotationHandler;
import org.seasar.framework.container.factory.S2ContainerFactory;

/**
 * @author manhole
 */
public class ComponentRegisterTest extends TestCase {

    public void test1() throws Exception {
        ConstantAnnotationHandler handler = new ConstantAnnotationHandler();
        ComponentDef cd = handler
                .createComponentDef(InitMethodBean.class, null);
        handler.appendInitMethod(cd);
        assertEquals(1, cd.getInitMethodDefSize());

        assertEquals("singleton", cd.getInstanceDef().getName());
        InitMethodDef initMethodDef = cd.getInitMethodDef(0);
        assertEquals("foo", initMethodDef.getMethodName());
    }

    public void test2() throws Exception {
        S2Container container = S2ContainerFactory.create(getClass()
                .getPackage().getName().replace('.', '/')
                + "/ComponentRegisterTest.dicon");
        ComponentDef cd = container.getComponentDef("initMethodBean");
        assertEquals(2, cd.getInitMethodDefSize());
        assertEquals("bar", cd.getInitMethodDef(0).getMethodName());
        assertEquals("foo", cd.getInitMethodDef(1).getMethodName());
    }

    public static class InitMethodBean {
        private int fooCalls_ = 0;

        public static final String INIT_METHOD = "foo";

        public void foo() {
            fooCalls_++;
        }

        public void bar() {
        }
    }

}
