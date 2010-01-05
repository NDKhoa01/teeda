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
package org.seasar.teeda.extension.taglib;

import org.seasar.teeda.core.taglib.html.GraphicImageTag;
import org.seasar.teeda.extension.component.html.THtmlGraphicImage;

/**
 * @author koichik
 * @since 1.0.12
 */
public class TGraphicImageTag extends GraphicImageTag {

    public String getComponentType() {
        return THtmlGraphicImage.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return THtmlGraphicImage.DEFAULT_RENDERER_TYPE;
    }

}
