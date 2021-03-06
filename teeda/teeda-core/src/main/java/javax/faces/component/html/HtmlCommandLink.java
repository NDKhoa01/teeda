/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
package javax.faces.component.html;

import javax.faces.component.ComponentUtil_;
import javax.faces.component.UICommand;
import javax.faces.context.FacesContext;

import org.seasar.teeda.core.JsfConstants;

/**
 * @author shot
 */
public class HtmlCommandLink extends UICommand {

    public static final String COMPONENT_TYPE = "javax.faces.HtmlCommandLink";

    private static final String DEFAULT_RENDERER_TYPE = "javax.faces.Link";

    private String accesskey = null;

    private String charset = null;

    private String coords = null;

    private String dir = null;

    private String hreflang = null;

    private String lang = null;

    private String onblur = null;

    private String onclick = null;

    private String ondblclick = null;

    private String onfocus = null;

    private String onkeydown = null;

    private String onkeypress = null;

    private String onkeyup = null;

    private String onmousedown = null;

    private String onmousemove = null;

    private String onmouseout = null;

    private String onmouseover = null;

    private String onmouseup = null;

    private String rel = null;

    private String rev = null;

    private String shape = null;

    private String style = null;

    private String styleClass = null;

    private String tabindex = null;

    private String target = null;

    private String title = null;

    private String type = null;

    public HtmlCommandLink() {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public void setAccesskey(String accesskey) {
        this.accesskey = accesskey;
    }

    public String getAccesskey() {
        if (accesskey != null) {
            return accesskey;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ACCESSKEY_ATTR);
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getCharset() {
        if (charset != null) {
            return charset;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.CHARSET_ATTR);
    }

    public void setCoords(String coords) {
        this.coords = coords;
    }

    public String getCoords() {
        if (coords != null) {
            return coords;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.COORDS_ATTR);
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getDir() {
        if (dir != null) {
            return dir;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.DIR_ATTR);
    }

    public void setHreflang(String hreflang) {
        this.hreflang = hreflang;
    }

    public String getHreflang() {
        if (hreflang != null) {
            return hreflang;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.HREFLANG_ATTR);
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLang() {
        if (lang != null) {
            return lang;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.LANG_ATTR);
    }

    public void setOnblur(String onblur) {
        this.onblur = onblur;
    }

    public String getOnblur() {
        if (onblur != null) {
            return onblur;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ONBLUR_ATTR);
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    public String getOnclick() {
        if (onclick != null) {
            return onclick;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ONCLICK_ATTR);
    }

    public void setOndblclick(String ondblclick) {
        this.ondblclick = ondblclick;
    }

    public String getOndblclick() {
        if (ondblclick != null) {
            return ondblclick;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ONDBLCLICK_ATTR);
    }

    public void setOnfocus(String onfocus) {
        this.onfocus = onfocus;
    }

    public String getOnfocus() {
        if (onfocus != null) {
            return onfocus;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ONFOCUS_ATTR);
    }

    public void setOnkeydown(String onkeydown) {
        this.onkeydown = onkeydown;
    }

    public String getOnkeydown() {
        if (onkeydown != null) {
            return onkeydown;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ONKEYDOWN_ATTR);
    }

    public void setOnkeypress(String onkeypress) {
        this.onkeypress = onkeypress;
    }

    public String getOnkeypress() {
        if (onkeypress != null) {
            return onkeypress;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ONKEYPRESS_ATTR);
    }

    public void setOnkeyup(String onkeyup) {
        this.onkeyup = onkeyup;
    }

    public String getOnkeyup() {
        if (onkeyup != null) {
            return onkeyup;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ONKEYUP_ATTR);
    }

    public void setOnmousedown(String onmousedown) {
        this.onmousedown = onmousedown;
    }

    public String getOnmousedown() {
        if (onmousedown != null) {
            return onmousedown;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ONMOUSEDOWN_ATTR);
    }

    public void setOnmousemove(String onmousemove) {
        this.onmousemove = onmousemove;
    }

    public String getOnmousemove() {
        if (onmousemove != null) {
            return onmousemove;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ONMOUSEMOVE_ATTR);
    }

    public void setOnmouseout(String onmouseout) {
        this.onmouseout = onmouseout;
    }

    public String getOnmouseout() {
        if (onmouseout != null) {
            return onmouseout;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ONMOUSEOUT_ATTR);
    }

    public void setOnmouseover(String onmouseover) {
        this.onmouseover = onmouseover;
    }

    public String getOnmouseover() {
        if (onmouseover != null) {
            return onmouseover;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ONMOUSEOVER_ATTR);
    }

    public void setOnmouseup(String onmouseup) {
        this.onmouseup = onmouseup;
    }

    public String getOnmouseup() {
        if (onmouseup != null) {
            return onmouseup;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ONMOUSEUP_ATTR);
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getRel() {
        if (rel != null) {
            return rel;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.REL_ATTR);
    }

    public void setRev(String rev) {
        this.rev = rev;
    }

    public String getRev() {
        if (rev != null) {
            return rev;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.REV_ATTR);
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getShape() {
        if (shape != null) {
            return shape;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.SHAPE_ATTR);
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getStyle() {
        if (style != null) {
            return style;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.STYLE_ATTR);
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        if (styleClass != null) {
            return styleClass;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.STYLE_CLASS_ATTR);
    }

    public void setTabindex(String tabindex) {
        this.tabindex = tabindex;
    }

    public String getTabindex() {
        if (tabindex != null) {
            return tabindex;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.TABINDEX_ATTR);
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTarget() {
        if (target != null) {
            return target;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.TARGET_ATTR);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        if (title != null) {
            return title;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.TITLE_ATTR);
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        if (type != null) {
            return type;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.TYPE_ATTR);
    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[28];
        values[0] = super.saveState(context);
        values[1] = accesskey;
        values[2] = charset;
        values[3] = coords;
        values[4] = dir;
        values[5] = hreflang;
        values[6] = lang;
        values[7] = onblur;
        values[8] = onclick;
        values[9] = ondblclick;
        values[10] = onfocus;
        values[11] = onkeydown;
        values[12] = onkeypress;
        values[13] = onkeyup;
        values[14] = onmousedown;
        values[15] = onmousemove;
        values[16] = onmouseout;
        values[17] = onmouseover;
        values[18] = onmouseup;
        values[19] = rel;
        values[20] = rev;
        values[21] = shape;
        values[22] = style;
        values[23] = styleClass;
        values[24] = tabindex;
        values[25] = target;
        values[26] = title;
        values[27] = type;
        return ((Object) (values));
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        accesskey = (String) values[1];
        charset = (String) values[2];
        coords = (String) values[3];
        dir = (String) values[4];
        hreflang = (String) values[5];
        lang = (String) values[6];
        onblur = (String) values[7];
        onclick = (String) values[8];
        ondblclick = (String) values[9];
        onfocus = (String) values[10];
        onkeydown = (String) values[11];
        onkeypress = (String) values[12];
        onkeyup = (String) values[13];
        onmousedown = (String) values[14];
        onmousemove = (String) values[15];
        onmouseout = (String) values[16];
        onmouseover = (String) values[17];
        onmouseup = (String) values[18];
        rel = (String) values[19];
        rev = (String) values[20];
        shape = (String) values[21];
        style = (String) values[22];
        styleClass = (String) values[23];
        tabindex = (String) values[24];
        target = (String) values[25];
        title = (String) values[26];
        type = (String) values[27];
    }
}
