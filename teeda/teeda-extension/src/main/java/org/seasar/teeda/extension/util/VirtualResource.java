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
package org.seasar.teeda.extension.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.framework.util.InputStreamUtil;
import org.seasar.framework.util.OutputStreamUtil;
import org.seasar.framework.util.ResourceUtil;
import org.seasar.teeda.extension.render.html.THtmlHeadRenderer;

/**
 * @author higa
 * 
 */
public abstract class VirtualResource {

    private static final String VIRTUAL_PATH = "/teedaExtension/";

    private static final String NAME = THtmlHeadRenderer.class.getName();

    private static final String JS_KEY = NAME + ".JS_KEY";

    protected VirtualResource() {
    }

    public static Set getJSResources(FacesContext context) {
        return getResources(context, JS_KEY);
    }

    protected static Set getResources(FacesContext context, String key) {
        AssertionUtil.assertNotNull("context", context);
        AssertionUtil.assertNotNull("key", key);
        Map requestMap = context.getExternalContext().getRequestMap();
        Set resources = (Set) requestMap.get(key);
        if (resources == null) {
            resources = new LinkedHashSet();
            requestMap.put(key, resources);
        }
        return resources;
    }

    public static void addJSResource(FacesContext context, String path) {
        AssertionUtil.assertNotNull("context", context);
        AssertionUtil.assertNotNull("path", path);
        Set resources = getJSResources(context);
        resources.add(path);
    }

    public static String convertVirtualPath(FacesContext context, String path) {
        return context.getExternalContext().getRequestContextPath()
                + VIRTUAL_PATH + path;
    }

    public static boolean isVirtualPath(HttpServletRequest request) {
        return request.getRequestURI().indexOf(VIRTUAL_PATH) != -1;
    }

    public static void resolveVirtualPath(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        String path = getResourcePath(request);
        String lcPath = path.toLowerCase();
        if (lcPath.endsWith(".js")) {
            response.setContentType("text/javascript");
        } else if (lcPath.endsWith(".css")) {
            response.setContentType("text/css");
        } else if (lcPath.endsWith(".gif")) {
            response.setContentType("image/gif");
        } else if (lcPath.endsWith(".png")) {
            response.setContentType("image/png");
        } else if (lcPath.endsWith(".jpg") || lcPath.endsWith(".jpeg")) {
            response.setContentType("image/jpeg");
        } else if (lcPath.endsWith(".xml") || lcPath.endsWith(".xsl")) {
            response.setContentType("text/xml");
        }
        response.setDateHeader("Last-Modified", 0);
        Calendar expires = Calendar.getInstance();
        expires.add(Calendar.DAY_OF_YEAR, 7);
        response.setDateHeader("Expires", expires.getTimeInMillis());
        InputStream is = ResourceUtil.getResourceAsStream(path);
        OutputStream os = response.getOutputStream();
        try {
            InputStreamUtil.copy(is, os);
        } finally {
            InputStreamUtil.close(is);
            OutputStreamUtil.close(os);
        }
    }

    protected static String getResourcePath(HttpServletRequest request) {
        String uri = request.getRequestURI();
        int pos = uri.indexOf(VIRTUAL_PATH) + VIRTUAL_PATH.length();
        return uri.substring(pos);
    }
}