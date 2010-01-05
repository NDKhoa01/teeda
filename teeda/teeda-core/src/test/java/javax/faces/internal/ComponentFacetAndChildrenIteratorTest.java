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
package javax.faces.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import junit.framework.TestCase;

/**
 * @author shot
 */
public class ComponentFacetAndChildrenIteratorTest extends TestCase {

    public void testHasNext() {
        Map map = new HashMap();
        map.put("a", "1");
        ComponentFacetAndChildrenIterator itr = new ComponentFacetAndChildrenIterator(
                map, null);
        assertNotNull(itr.next());
        try {
            itr.next();
        } catch (NoSuchElementException e) {
            assertTrue(true);
        }

        List list = new ArrayList();
        list.add("c");
        itr = new ComponentFacetAndChildrenIterator(null, list);
        assertNotNull(itr.next());

        itr = new ComponentFacetAndChildrenIterator(map, list);
        assertNotNull(itr.next());
        assertNotNull(itr.next());
    }

}
