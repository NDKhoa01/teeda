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
package org.seasar.teeda.core.exception;

/**
 * @author higa
 * @author Shinpei Ohtani
 *  
 */
public class LifecycleIdAlreadyExistRuntimeException extends
        ExtendFacesException {

    private static final long serialVersionUID = 3833469504771339572L;

    private String lifecycleId;

    public LifecycleIdAlreadyExistRuntimeException(String lifecycleId) {
        super("ETDA0015", new Object[] { lifecycleId });
        this.lifecycleId = lifecycleId;
    }

    public String getLifecycleId() {
        return lifecycleId;
    }
}