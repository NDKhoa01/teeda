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
package org.seasar.teeda.core.context.portlet;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;

import org.seasar.teeda.core.context.AbstractUnmodifiableExternalContextMap;

/**
 * PortletRequestHeaderValuesMap is RequestHeaderValuesMap implementation for Portlet environment.
 * 
 * @author shinsuke
 * 
 */
public class PortletRequestHeaderValuesMap extends
        AbstractUnmodifiableExternalContextMap {

    private static final String[] EMPTY_STRING_ARRAY = new String[0];

    private final PortletRequest portletRequest_;

    private final Map valueCache_ = new HashMap();

    PortletRequestHeaderValuesMap(PortletRequest portletRequest) {
        portletRequest_ = portletRequest;
    }

    protected Object getAttribute(String key) {
        Object ret = valueCache_.get(key);
        if (ret == null) {
            valueCache_.put(key, ret = toStringArray(portletRequest_
                    .getProperties(key)));
        }

        return ret;
    }

    protected Enumeration getAttributeNames() {
        return portletRequest_.getPropertyNames();
    }

    private String[] toStringArray(Enumeration e) {
        if (e == null) {
            return EMPTY_STRING_ARRAY;
        }
        List list = new ArrayList();
        while (e.hasMoreElements()) {
            list.add(e.nextElement());
        }
        return (String[]) list.toArray(new String[list.size()]);
    }
}