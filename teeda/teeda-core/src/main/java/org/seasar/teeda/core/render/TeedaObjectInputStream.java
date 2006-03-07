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
package org.seasar.teeda.core.render;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

/**
 * @author shot
 */
public class TeedaObjectInputStream extends ObjectInputStream {

    public TeedaObjectInputStream(InputStream is) throws IOException {
        super(is);
    }

    protected Class resolveClass(ObjectStreamClass clazz) throws IOException,
            ClassNotFoundException {
        String clazzName = clazz.getName();
        try {
            return Class.forName(clazzName);
        } catch (ClassNotFoundException e) {
            return super.resolveClass(clazz);
        }
    }
}
