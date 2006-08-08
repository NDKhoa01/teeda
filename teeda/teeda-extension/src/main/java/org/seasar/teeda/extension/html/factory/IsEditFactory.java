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
package org.seasar.teeda.extension.html.factory;

import java.util.Map;

import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.PageDesc;

/**
 * @author shot
 */
public class IsEditFactory extends AbstractElementProcessorFactory {

    private static final String TAG_NAME = "outputText";

    private static final String IS_PARAM_PREFIX = "is";

    private static final String ISNOT_PARAM_PREFIX = "isNot";

    public boolean isMatch(ElementNode elementNode, PageDesc pageDesc,
            ActionDesc actionDesc) {
        String id = elementNode.getId();
        if (StringUtil.isEmpty(id)) {
            return false;
        }
        String tagName = elementNode.getTagName();
        return JsfConstants.DIV_ELEM.equalsIgnoreCase(tagName) && isIdMatch(id);
    }

    protected void customizeProperties(Map properties, ElementNode elementNode,
            PageDesc pageDesc, ActionDesc actionDesc) {
        super
                .customizeProperties(properties, elementNode, pageDesc,
                        actionDesc);
        String id = elementNode.getId();
        String pageName = pageDesc.getPageName();
        String s = null;
        String expression = null;
        if (StringUtil.startsWithIgnoreCase(id, IS_PARAM_PREFIX)) {
            s = id.substring(IS_PARAM_PREFIX.length());
            s = StringUtil.decapitalize(s);
            s = s + " == true";
            expression = getBindingExpression(pageName, s);
        } else if (StringUtil.startsWithIgnoreCase(id, ISNOT_PARAM_PREFIX)) {
            s = id.substring(ISNOT_PARAM_PREFIX.length());
            s = StringUtil.capitalize(s);
            s = s + " == false";
            expression = getBindingExpression(pageName, s);
        }
        properties.put(JsfConstants.RENDERER_TYPE_ATTR, expression);
    }

    protected String getTagName() {
        return TAG_NAME;
    }

    protected String getUri() {
        return JsfConstants.JSF_HTML_URI;
    }

    private boolean isIdMatch(String id) {
        return (StringUtil.startsWithIgnoreCase(id, IS_PARAM_PREFIX) || StringUtil
                .startsWithIgnoreCase(id, ISNOT_PARAM_PREFIX));
    }
}
