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

import javax.faces.context.FacesContext;

import org.seasar.teeda.core.application.ActionListenerImpl;
import org.seasar.teeda.extension.html.PagePersistence;

/**
 * @author higa
 * 
 */
public class HtmlActionListener extends ActionListenerImpl {

    private PagePersistence pagePersistence;

    /**
     * @return Returns the pagePersistence.
     */
    public PagePersistence getPagePersistence() {
        return pagePersistence;
    }

    /**
     * @param pagePersistence The pagePersistence to set.
     */
    public void setPagePersistence(PagePersistence pagePersistence) {
        this.pagePersistence = pagePersistence;
    }

    protected void processAfterInvoke(FacesContext context, String fromAction,
            String outcome) {
        super.processAfterInvoke(context, fromAction, outcome);
        if (fromAction != null && fromAction.indexOf(".doFinish") > 0) {
            pagePersistence.removeSubApplicationPages(context);
        }
    }
}