/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
 */package org.seasar.teeda.extension.util;

import junit.framework.TestCase;

import org.seasar.framework.convention.impl.NamingConventionImpl;
import org.seasar.teeda.extension.util.aaa.AaaPage;
import org.seasar.teeda.extension.util.bbb.BbbPage;

public class PageTransitionUtilTest extends TestCase {

    public void test1() throws Exception {
        String s = PageTransitionUtil.getNextPageTransition(HogePage.class,
                FooPage.class, new NamingConventionImpl() {
                    public String fromPageNameToPath(String pageName) {
                        return "/aaa/bbb/ccc.html";
                    }
                });
        assertEquals("ccc", s);
    }

    public void test2() throws Exception {
        String s = PageTransitionUtil.getNextPageTransition(AaaPage.class,
                BbbPage.class, new NamingConventionImpl() {
                    public String fromPageNameToPath(String pageName) {
                        return "/view/aaa/bbb/ccc.html";
                    }
                });
        assertEquals("aaa_bbb_ccc", s);
    }

    public static class HogePage {

    }

    public static class FooPage {

    }
}