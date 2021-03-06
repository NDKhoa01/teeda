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
package javax.faces.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.render.RenderKit;
import javax.faces.render.Renderer;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.exception.TagNotFoundRuntimeException;

/**
 * @author manhole
 * @author shot
 */
public class UIComponentUtil {

    public static boolean isDisabled(UIComponent component) {
        return getPrimitiveBooleanAttribute(component,
                JsfConstants.DISABLED_ATTR);
    }

    public static String getStringAttribute(UIComponent component, String name) {
        return (String) component.getAttributes().get(name);
    }

    public static boolean getPrimitiveBooleanAttribute(UIComponent component,
            String name) {
        Object value = component.getAttributes().get(name);
        if (value == null) {
            return false;
        }
        return ((Boolean) value).booleanValue();
    }

    public static int getPrimitiveIntAttribute(UIComponent component,
            String name) {
        Object value = component.getAttributes().get(name);
        if (value == null) {
            return 0;
        }
        return ((Integer) value).intValue();
    }

    protected static String getNormalizeId(UIComponent component) {
        String id = component.getId();
        if (StringUtil.isEmpty(id)) {
            return id;
        }
        int indexOf = id.indexOf("-");
        if (indexOf < 0) {
            return id;
        }
        return id.substring(0, indexOf);
    }

    public static String getLabel(UIComponent component) {
        String label = (String) component.getAttributes().get(
                JsfConstants.LABEL_ATTR);
        if (label != null) {
            return label;
        }
        label = LabelUtil.getLabelValue(component.getId());
        if (label != null) {
            return label;
        }
        String title = (String) component.getAttributes().get(
                JsfConstants.TITLE_ATTR);
        if (title != null) {
            return title;
        }
        return component.getId();
    }

    public static void callValidators(FacesContext context, UIInput input,
            Object convertedValue) {
        Validator[] validators = input.getValidators();
        for (int i = 0; i < validators.length; ++i) {
            Validator validator = validators[i];
            try {
                validator.validate(context, input, convertedValue);
            } catch (ValidatorException e) {
                input.setValid(false);
                FacesMessage facesMessage = e.getFacesMessage();
                if (facesMessage != null) {
                    context
                            .addMessage(input.getClientId(context),
                                    facesMessage);
                }
            }
        }
        MethodBinding validatorBinding = input.getValidator();
        if (validatorBinding != null) {
            try {
                validatorBinding.invoke(context, new Object[] { context, input,
                        convertedValue });
            } catch (EvaluationException e) {
                input.setValid(false);
                Throwable cause = e.getCause();
                if (cause instanceof ValidatorException) {
                    FacesMessage facesMessage = ((ValidatorException) cause)
                            .getFacesMessage();
                    if (facesMessage != null) {
                        context.addMessage(input.getClientId(context),
                                facesMessage);
                    }
                } else {
                    throw e;
                }
            }
        }
    }

    public static UIForm findParentForm(final UIComponent component) {
        final UIComponent parent = findParentOrNull(component, UIForm.class);
        if (parent == null) {
            throw new TagNotFoundRuntimeException("form");
        }
        return (UIForm) parent;
    }

    public static UIComponent findParent(final UIComponent component,
            final Class parentClass) {
        final UIComponent parent = findParentOrNull(component, parentClass);
        if (parent != null) {
            return parent;
        }
        // TODO 例外をどうにかする
        throw new IllegalArgumentException("parent element not found [" +
                parentClass.getName() + "]");
    }

    public static UIComponent findParent(final UIComponent component,
            final Class parentClass, final String id) {
        final UIComponent parent = findParentOrNull(component, parentClass, id);
        if (parent != null) {
            return parent;
        }
        // TODO 例外をどうにかする
        throw new IllegalArgumentException("parent element not found [" + id +
                "]");
    }

    private static UIComponent findParentOrNull(final UIComponent component,
            final Class parentClass) {
        for (UIComponent parent = component.getParent(); parent != null; parent = parent
                .getParent()) {
            if (parentClass.isInstance(parent)) {
                return parent;
            }
        }
        return null;
    }

    private static UIComponent findParentOrNull(final UIComponent component,
            final Class parentClass, final String id) {
        for (UIComponent parent = component.getParent(); parent != null; parent = parent
                .getParent()) {
            if (parentClass.isInstance(parent) &&
                    id.equals(getNormalizeId(parent))) {
                return parent;
            }
        }
        return null;
    }

    public static UIComponent findDescendant(UIComponent component, Class clazz) {
        List children = component.getChildren();
        for (Iterator i = children.iterator(); i.hasNext();) {
            UIComponent child = (UIComponent) i.next();
            if (clazz.isInstance(child)) {
                return child;
            }
            UIComponent descendant = findDescendant(child, clazz);
            if (descendant != null) {
                return descendant;
            }
        }
        return null;
    }

    public static List collectDescendants(UIComponent component, Class clazz) {
        List list = new ArrayList();
        List children = component.getChildren();
        for (Iterator i = children.iterator(); i.hasNext();) {
            UIComponent child = (UIComponent) i.next();
            if (clazz.isInstance(child)) {
                list.add(child);
            }
            List l = collectDescendants(child, clazz);
            if (l != null) {
                list.addAll(l);
            }
        }
        return list;
    }

    public static Renderer getRenderer(FacesContext context,
            UIComponent component) {
        String rendererType = component.getRendererType();
        if (rendererType == null) {
            return null;
        }
        RenderKit renderKit = RenderKitUtil.getRenderKit(context);
        return renderKit.getRenderer(component.getFamily(), rendererType);
    }
}