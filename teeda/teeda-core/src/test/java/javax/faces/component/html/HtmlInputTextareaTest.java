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
package javax.faces.component.html;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInputTest;
import javax.faces.context.FacesContext;

import org.seasar.teeda.core.mock.MockValueBinding;

/**
 * @author manhole
 */
public class HtmlInputTextareaTest extends UIInputTest {

    public void testSetGetAccesskey() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        component.setAccesskey("foo accesskey");
        assertEquals("foo accesskey", component.getAccesskey());
    }

    public void testSetGetAccesskey_ValueBinding() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar accesskey");
        component.setValueBinding("accesskey", vb);
        assertEquals("bar accesskey", component.getAccesskey());
        assertEquals("bar accesskey", component.getValueBinding("accesskey")
                .getValue(context));
    }

    public void testSetGetCols() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        component.setCols(245);
        assertEquals(245, component.getCols());
    }

    public void testSetGetCols_ValueBinding() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, new Integer(443));
        component.setValueBinding("cols", vb);
        assertEquals(443, component.getCols());
        assertEquals(new Integer(443), component.getValueBinding("cols")
                .getValue(context));
    }

    public void testSetGetDir() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        component.setDir("foo dir");
        assertEquals("foo dir", component.getDir());
    }

    public void testSetGetDir_ValueBinding() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar dir");
        component.setValueBinding("dir", vb);
        assertEquals("bar dir", component.getDir());
        assertEquals("bar dir", component.getValueBinding("dir").getValue(
                context));
    }

    public void testSetGetDisabled() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        component.setDisabled(true);
        assertEquals(true, component.isDisabled());
    }

    public void testSetGetDisabled_ValueBinding() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, Boolean.TRUE);
        component.setValueBinding("disabled", vb);
        assertEquals(true, component.isDisabled());
        assertEquals(Boolean.TRUE, component.getValueBinding("disabled")
                .getValue(context));
    }

    public void testSetGetLang() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        component.setLang("foo lang");
        assertEquals("foo lang", component.getLang());
    }

    public void testSetGetLang_ValueBinding() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar lang");
        component.setValueBinding("lang", vb);
        assertEquals("bar lang", component.getLang());
        assertEquals("bar lang", component.getValueBinding("lang").getValue(
                context));
    }

    public void testSetGetOnblur() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        component.setOnblur("foo onblur");
        assertEquals("foo onblur", component.getOnblur());
    }

    public void testSetGetOnblur_ValueBinding() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onblur");
        component.setValueBinding("onblur", vb);
        assertEquals("bar onblur", component.getOnblur());
        assertEquals("bar onblur", component.getValueBinding("onblur")
                .getValue(context));
    }

    public void testSetGetOnchange() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        component.setOnchange("foo onchange");
        assertEquals("foo onchange", component.getOnchange());
    }

    public void testSetGetOnchange_ValueBinding() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onchange");
        component.setValueBinding("onchange", vb);
        assertEquals("bar onchange", component.getOnchange());
        assertEquals("bar onchange", component.getValueBinding("onchange")
                .getValue(context));
    }

    public void testSetGetOnclick() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        component.setOnclick("foo onclick");
        assertEquals("foo onclick", component.getOnclick());
    }

    public void testSetGetOnclick_ValueBinding() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onclick");
        component.setValueBinding("onclick", vb);
        assertEquals("bar onclick", component.getOnclick());
        assertEquals("bar onclick", component.getValueBinding("onclick")
                .getValue(context));
    }

    public void testSetGetOndblclick() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        component.setOndblclick("foo ondblclick");
        assertEquals("foo ondblclick", component.getOndblclick());
    }

    public void testSetGetOndblclick_ValueBinding() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar ondblclick");
        component.setValueBinding("ondblclick", vb);
        assertEquals("bar ondblclick", component.getOndblclick());
        assertEquals("bar ondblclick", component.getValueBinding("ondblclick")
                .getValue(context));
    }

    public void testSetGetOnfocus() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        component.setOnfocus("foo onfocus");
        assertEquals("foo onfocus", component.getOnfocus());
    }

    public void testSetGetOnfocus_ValueBinding() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onfocus");
        component.setValueBinding("onfocus", vb);
        assertEquals("bar onfocus", component.getOnfocus());
        assertEquals("bar onfocus", component.getValueBinding("onfocus")
                .getValue(context));
    }

    public void testSetGetOnkeydown() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        component.setOnkeydown("foo onkeydown");
        assertEquals("foo onkeydown", component.getOnkeydown());
    }

    public void testSetGetOnkeydown_ValueBinding() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onkeydown");
        component.setValueBinding("onkeydown", vb);
        assertEquals("bar onkeydown", component.getOnkeydown());
        assertEquals("bar onkeydown", component.getValueBinding("onkeydown")
                .getValue(context));
    }

    public void testSetGetOnkeypress() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        component.setOnkeypress("foo onkeypress");
        assertEquals("foo onkeypress", component.getOnkeypress());
    }

    public void testSetGetOnkeypress_ValueBinding() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onkeypress");
        component.setValueBinding("onkeypress", vb);
        assertEquals("bar onkeypress", component.getOnkeypress());
        assertEquals("bar onkeypress", component.getValueBinding("onkeypress")
                .getValue(context));
    }

    public void testSetGetOnkeyup() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        component.setOnkeyup("foo onkeyup");
        assertEquals("foo onkeyup", component.getOnkeyup());
    }

    public void testSetGetOnkeyup_ValueBinding() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onkeyup");
        component.setValueBinding("onkeyup", vb);
        assertEquals("bar onkeyup", component.getOnkeyup());
        assertEquals("bar onkeyup", component.getValueBinding("onkeyup")
                .getValue(context));
    }

    public void testSetGetOnmousedown() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        component.setOnmousedown("foo onmousedown");
        assertEquals("foo onmousedown", component.getOnmousedown());
    }

    public void testSetGetOnmousedown_ValueBinding() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onmousedown");
        component.setValueBinding("onmousedown", vb);
        assertEquals("bar onmousedown", component.getOnmousedown());
        assertEquals("bar onmousedown", component
                .getValueBinding("onmousedown").getValue(context));
    }

    public void testSetGetOnmousemove() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        component.setOnmousemove("foo onmousemove");
        assertEquals("foo onmousemove", component.getOnmousemove());
    }

    public void testSetGetOnmousemove_ValueBinding() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onmousemove");
        component.setValueBinding("onmousemove", vb);
        assertEquals("bar onmousemove", component.getOnmousemove());
        assertEquals("bar onmousemove", component
                .getValueBinding("onmousemove").getValue(context));
    }

    public void testSetGetOnmouseout() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        component.setOnmouseout("foo onmouseout");
        assertEquals("foo onmouseout", component.getOnmouseout());
    }

    public void testSetGetOnmouseout_ValueBinding() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onmouseout");
        component.setValueBinding("onmouseout", vb);
        assertEquals("bar onmouseout", component.getOnmouseout());
        assertEquals("bar onmouseout", component.getValueBinding("onmouseout")
                .getValue(context));
    }

    public void testSetGetOnmouseover() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        component.setOnmouseover("foo onmouseover");
        assertEquals("foo onmouseover", component.getOnmouseover());
    }

    public void testSetGetOnmouseover_ValueBinding() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onmouseover");
        component.setValueBinding("onmouseover", vb);
        assertEquals("bar onmouseover", component.getOnmouseover());
        assertEquals("bar onmouseover", component
                .getValueBinding("onmouseover").getValue(context));
    }

    public void testSetGetOnmouseup() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        component.setOnmouseup("foo onmouseup");
        assertEquals("foo onmouseup", component.getOnmouseup());
    }

    public void testSetGetOnmouseup_ValueBinding() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onmouseup");
        component.setValueBinding("onmouseup", vb);
        assertEquals("bar onmouseup", component.getOnmouseup());
        assertEquals("bar onmouseup", component.getValueBinding("onmouseup")
                .getValue(context));
    }

    public void testSetGetOnselect() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        component.setOnselect("foo onselect");
        assertEquals("foo onselect", component.getOnselect());
    }

    public void testSetGetOnselect_ValueBinding() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onselect");
        component.setValueBinding("onselect", vb);
        assertEquals("bar onselect", component.getOnselect());
        assertEquals("bar onselect", component.getValueBinding("onselect")
                .getValue(context));
    }

    public void testSetGetReadonly() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        component.setReadonly(true);
        assertEquals(true, component.isReadonly());
    }

    public void testSetGetReadonly_ValueBinding() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, Boolean.TRUE);
        component.setValueBinding("readonly", vb);
        assertEquals(true, component.isReadonly());
        assertEquals(Boolean.TRUE, component.getValueBinding("readonly")
                .getValue(context));
    }

    public void testSetGetRows() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        component.setRows(222);
        assertEquals(222, component.getRows());
    }

    public void testSetGetRows_ValueBinding() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, new Integer(111));
        component.setValueBinding("rows", vb);
        assertEquals(111, component.getRows());
        assertEquals(new Integer(111), component.getValueBinding("rows")
                .getValue(context));
    }

    public void testSetGetStyle() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        component.setStyle("foo style");
        assertEquals("foo style", component.getStyle());
    }

    public void testSetGetStyle_ValueBinding() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar style");
        component.setValueBinding("style", vb);
        assertEquals("bar style", component.getStyle());
        assertEquals("bar style", component.getValueBinding("style").getValue(
                context));
    }

    public void testSetGetStyleClass() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        component.setStyleClass("foo styleClass");
        assertEquals("foo styleClass", component.getStyleClass());
    }

    public void testSetGetStyleClass_ValueBinding() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar styleClass");
        component.setValueBinding("styleClass", vb);
        assertEquals("bar styleClass", component.getStyleClass());
        assertEquals("bar styleClass", component.getValueBinding("styleClass")
                .getValue(context));
    }

    public void testSetGetTabindex() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        component.setTabindex("foo tabindex");
        assertEquals("foo tabindex", component.getTabindex());
    }

    public void testSetGetTabindex_ValueBinding() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar tabindex");
        component.setValueBinding("tabindex", vb);
        assertEquals("bar tabindex", component.getTabindex());
        assertEquals("bar tabindex", component.getValueBinding("tabindex")
                .getValue(context));
    }

    public void testSetGetTitle() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        component.setTitle("foo title");
        assertEquals("foo title", component.getTitle());
    }

    public void testSetGetTitle_ValueBinding() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar title");
        component.setValueBinding("title", vb);
        assertEquals("bar title", component.getTitle());
        assertEquals("bar title", component.getValueBinding("title").getValue(
                context));
    }

    public void testSetGetLabel() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        component.setLabel("label1");
        assertEquals("label1", component.getLabel());
    }

    public void testSetGetLabel_ValueBinding() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar label");
        component.setValueBinding("label", vb);
        assertEquals("bar label", component.getLabel());
        assertEquals("bar label", component.getValueBinding("label").getValue(
                context));
    }

    public void testSetGetWrap() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        component.setWrap("wrap1");
        assertEquals("wrap1", component.getWrap());
    }

    public void testSetGetWrap_ValueBinding() throws Exception {
        HtmlInputTextarea component = createHtmlInputTextarea();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar wrap");
        component.setValueBinding("wrap", vb);
        assertEquals("bar wrap", component.getWrap());
        assertEquals("bar wrap", component.getValueBinding("wrap").getValue(
                context));
    }

    private HtmlInputTextarea createHtmlInputTextarea() {
        return (HtmlInputTextarea) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new HtmlInputTextarea();
    }

}
