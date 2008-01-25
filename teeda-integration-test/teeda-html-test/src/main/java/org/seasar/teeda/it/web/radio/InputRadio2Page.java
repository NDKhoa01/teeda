/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
package org.seasar.teeda.it.web.radio;

import org.seasar.teeda.extension.annotation.validator.Required;

/**
 * @author manhole
 */
public class InputRadio2Page {

	public String prerender() {
		return null;
	}

	@Required
	private Integer aaa;

	public Integer getAaa() {
		return aaa;
	}

	public void setAaa(final Integer aaa) {
		this.aaa = aaa;
	}

	public String doAction() {
		return null;
	}

}