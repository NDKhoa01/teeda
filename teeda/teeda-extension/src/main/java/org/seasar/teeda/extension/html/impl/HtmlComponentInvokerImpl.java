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
 */
package org.seasar.teeda.extension.html.impl;

import java.io.IOException;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.internal.scope.SubApplicationScope;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.AssertionUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.util.DIContainerUtil;
import org.seasar.teeda.core.util.ErrorPageManager;
import org.seasar.teeda.core.util.MethodBindingUtil;
import org.seasar.teeda.core.util.NavigationHandlerUtil;
import org.seasar.teeda.core.util.NullErrorPageManagerImpl;
import org.seasar.teeda.extension.exception.IllegalPageTransitionException;
import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.ActionDescCache;
import org.seasar.teeda.extension.html.HtmlComponentInvoker;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.html.PageDescCache;

/**
 * @author higa
 * 
 */
public class HtmlComponentInvokerImpl implements HtmlComponentInvoker {

    private static Logger logger = Logger
            .getLogger(HtmlComponentInvokerImpl.class);

    private NamingConvention namingConvention;

    public static final String errorManager_BINDING = "bindingType=may";

    private ErrorPageManager errorPageManager = new NullErrorPageManagerImpl();

    private PageDescCache pageDescCache;

    private ActionDescCache actionDescCache;

    /**
     * @return Returns the namingConvention.
     */
    public NamingConvention getNamingConvention() {
        return namingConvention;
    }

    /**
     * @param namingConvention The namingConvention to set.
     */
    public void setNamingConvention(NamingConvention namingConvention) {
        this.namingConvention = namingConvention;
    }

    /**
     * @param errorPageManager The errorPageManager to set.
     */
    public void setErrorPageManager(ErrorPageManager errorPageManager) {
        this.errorPageManager = errorPageManager;
    }

    /**
     * @return Returns the actionDescCache.
     */
    public ActionDescCache getActionDescCache() {
        return actionDescCache;
    }

    /**
     * @param actionDescCache The actionDescCache to set.
     */
    public void setActionDescCache(ActionDescCache actionDescCache) {
        this.actionDescCache = actionDescCache;
    }

    /**
     * @return Returns the pageDescCache.
     */
    public PageDescCache getPageDescCache() {
        return pageDescCache;
    }

    /**
     * @param pageDescCache The pageDescCache to set.
     */
    public void setPageDescCache(PageDescCache pageDescCache) {
        this.pageDescCache = pageDescCache;
    }

    public String invokeInitialize(FacesContext context, String componentName) {
        return invoke(context, componentName, INITIALIZE);
    }

    public String invokePrerender(FacesContext context, String componentName) {
        return invoke(context, componentName, PRERENDER);
    }

    public String invoke(FacesContext context, String componentName,
            String methodName) {
        AssertionUtil.assertNotNull("context", context);
        AssertionUtil.assertNotNull("methodName", methodName);
        if (componentName == null) {
            return null;
        }
        String next = null;
        String fromAction = MethodBindingUtil.getFromAction(componentName,
                methodName);
        String pageSuffix = namingConvention.getPageSuffix();
        if (!DIContainerUtil.hasComponent(componentName)) {
            return null;
        }
        Object component = DIContainerUtil.getComponent(componentName);
        Class componentClass = component.getClass();
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(componentClass);
        Object ret = null;
        try {
            ret = beanDesc.invoke(component, methodName, null);
            if ("doFinish".equals(methodName)) {
                SubApplicationScope.removeContext(context);
            }
            if (ret instanceof Class) {
                Class retClass = (Class) ret;
                if (retClass != null
                        && !retClass.getName().endsWith(pageSuffix)) {
                    throw new IllegalPageTransitionException(retClass);
                }
                next = getNextPageTransition(retClass);

            } else {
                next = (String) ret;
            }
            NavigationHandlerUtil.handleNavigation(context, fromAction, next);
        } catch (Exception e) {
            try {
                ExternalContext extContext = context.getExternalContext();
                if (errorPageManager.handleException(e, context, extContext)) {
                    context.responseComplete();
                } else {
                    throw new EvaluationException(e);
                }
            } catch (IOException ioe) {
                logger.log(ioe);
                throw new EvaluationException(e);
            }
        }
        if (INITIALIZE.equals(methodName)) {
            setInitialized(context, true);
        }
        return next;
    }

    public boolean isInitialized(FacesContext context) {
        Map requestMap = context.getExternalContext().getRequestMap();
        Boolean initialized = (Boolean) requestMap.get(getClass().getName());
        if (initialized == null) {
            return false;
        }
        return initialized.booleanValue();
    }

    protected void setInitialized(FacesContext context, boolean initialized) {
        Map requestMap = context.getExternalContext().getRequestMap();
        requestMap.put(getClass().getName(), Boolean.valueOf(initialized));
    }

    protected String getNextPageTransition(Class toPageClass) {
        String className = toPageClass.getName();
        String pageName = namingConvention
                .fromClassNameToComponentName(className);
        String path = namingConvention.fromPageNameToPath(pageName);
        String viewRootPath = namingConvention.getViewRootPath();
        if (!viewRootPath.endsWith("/")) {
            viewRootPath = viewRootPath + "/";
        }
        path = path.substring(viewRootPath.length());
        path = StringUtil.rtrim(path, namingConvention.getViewExtension());
        return path.replaceAll("/", "_");
    }

    public String getComponentName(String path, String methodName) {
        String componentName = null;
        PageDesc pageDesc = pageDescCache.getPageDesc(path);
        if (pageDesc != null && pageDesc.hasMethod(methodName)) {
            componentName = pageDesc.getPageName();
        }
        if (componentName == null) {
            ActionDesc actionDesc = actionDescCache.getActionDesc(path);
            if (actionDesc != null && actionDesc.hasMethod(methodName)) {
                componentName = actionDesc.getActionName();
            }
        }
        return componentName;
    }
}
