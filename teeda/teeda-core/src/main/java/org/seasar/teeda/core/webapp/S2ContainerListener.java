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
package org.seasar.teeda.core.webapp;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.framework.container.impl.HttpServletExternalContext;
import org.seasar.framework.container.impl.HttpServletExternalContextComponentDefRegister;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.StringUtil;

/**
 * @author manhole
 */
public class S2ContainerListener implements ServletContextListener {

    public static final String CONFIG_PATH_KEY = "org.seasar.framework.container.configPath";

    private static Logger logger = Logger.getLogger(S2ContainerListener.class);

    private void initializeContainer(ServletContext servletContext) {
        String configPath = servletContext.getInitParameter(CONFIG_PATH_KEY);
        if (!StringUtil.isEmpty(configPath)) {
            SingletonS2ContainerFactory.setConfigPath(configPath);
        }
        HttpServletExternalContext extCtx = new HttpServletExternalContext();
        extCtx.setApplication(servletContext);
        SingletonS2ContainerFactory.setExternalContext(extCtx);
        SingletonS2ContainerFactory
                .setExternalContextComponentDefRegister(new HttpServletExternalContextComponentDefRegister());
        SingletonS2ContainerFactory.init();
    }

    public void contextInitialized(ServletContextEvent event) {
        logger.debug("S2Container initialize start");
        ServletContext servletContext = event.getServletContext();
        try {
            initializeContainer(servletContext);
        } catch (RuntimeException e) {
            logger.log(e);
            throw e;
        }
        logger.debug("S2Container initialize end");
    }

    public void contextDestroyed(ServletContextEvent event) {
        SingletonS2ContainerFactory.destroy();
    }

}
