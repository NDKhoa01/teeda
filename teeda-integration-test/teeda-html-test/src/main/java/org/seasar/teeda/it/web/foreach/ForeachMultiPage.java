/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
package org.seasar.teeda.it.web.foreach;

import java.util.ArrayList;
import java.util.List;

/**
 * @author manhole
 */
public class ForeachMultiPage {

    private FooDto[] fooItems;

    public String doSomething() {
        return null;
    }

    public String initialize() {
        List l = new ArrayList();
        {
            FooDto f = new FooDto();
            f.setFooNo(new Integer(1));
            f.setAaa("aa1");
            f.setBbb("bb1");
            l.add(f);
        }
        {
            FooDto f = new FooDto();
            f.setFooNo(new Integer(2));
            f.setAaa("aa2");
            f.setBbb("bb2");
            l.add(f);
        }
        {
            FooDto f = new FooDto();
            f.setFooNo(new Integer(3));
            f.setAaa("aa3");
            f.setBbb("bb3");
            l.add(f);
        }
        fooItems = new FooDto[l.size()];
        l.toArray(fooItems);

        return null;
    }

    public String prerender() {
        return null;
    }

    public FooDto[] getFooItems() {
        return fooItems;
    }

    public void setFooItems(FooDto[] fooItems) {
        this.fooItems = fooItems;
    }

    private String aaa;

    private String bbb;

    public String getAaa() {
        return aaa;
    }

    public void setAaa(String aaa) {
        this.aaa = aaa;
    }

    public String getBbb() {
        return bbb;
    }

    public void setBbb(String bbb) {
        this.bbb = bbb;
    }

}
