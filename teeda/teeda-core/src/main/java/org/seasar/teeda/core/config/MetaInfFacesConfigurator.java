package org.seasar.teeda.core.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.faces.context.ExternalContext;

import org.seasar.framework.container.factory.ResourceResolver;
import org.seasar.framework.util.InputStreamUtil;
import org.seasar.framework.util.JarFileUtil;

public class MetaInfFacesConfigurator extends AbstractFacesConfigurator {

    private static final String WEB_INF_LIB = "/WEB-INF/lib/";
    
    private static final String JAR_POSTFIX = ".jar";
    
    private ExternalContext externalContext_;
    public MetaInfFacesConfigurator(ExternalContext externalContext) {
        externalContext_ = externalContext;
        setResourceResolver(new WebAppJarResourceResolver(externalContext_));
    }
        
    protected String getPath() {
        return WEB_INF_LIB;
    }

    protected static class WebAppJarResourceResolver implements ResourceResolver{

        private ExternalContext context_;
        public WebAppJarResourceResolver(ExternalContext context){
            context_ = context;
        }
        
        public InputStream getInputStream(String path) {
            
            Set resourcePaths = context_.getResourcePaths(path);
            if(resourcePaths == null){
                return null;
            }
            String jarPath = null;
            InputStream is = null;
            try{
                for(Iterator itr = resourcePaths.iterator();itr.hasNext();){
                    jarPath = (String)itr.next();
                    if(isJarFile(jarPath)){
                        JarFile jar = createJarInstance(jarPath);
                        JarEntry jarEntry = jar.getJarEntry("META-INF/faces-config.xml");
                        if(jarEntry != null){
                            is = JarFileUtil.getInputStream(jar, jarEntry);
                        }
                    }
                }
                return is;
            }finally{
                InputStreamUtil.close(is);
            }
        }
        
        public static boolean isJarFile(String path){
            return (path != null) && path.endsWith(JAR_POSTFIX);
        }
        
        protected static JarFile createJarInstance(String path){
            try{
                return new JarFile(path);
            }catch (IOException e){
                return null;
            }
        }
    }
}
