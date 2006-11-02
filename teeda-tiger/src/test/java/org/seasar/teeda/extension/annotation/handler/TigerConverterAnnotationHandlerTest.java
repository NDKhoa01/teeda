package org.seasar.teeda.extension.annotation.handler;

import java.util.Date;

import javax.faces.internal.ConverterResource;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.deployer.InstanceDefFactory;
import org.seasar.framework.container.impl.ComponentDefImpl;
import org.seasar.teeda.core.unit.TeedaTestCase;
import org.seasar.teeda.extension.annotation.convert.DateTimeConverter;
import org.seasar.teeda.extension.convert.TDateTimeConverter;

public class TigerConverterAnnotationHandlerTest extends TeedaTestCase {

	protected void setUp() {
		ComponentDef cd = new ComponentDefImpl(TDateTimeConverter.class,
				"TDateTimeConverter");
		cd.setInstanceDef(InstanceDefFactory.PROTOTYPE);
		register(cd);
		register(HogeBean.class, "hogeBean");
	}

	public void testTigerAnnotation() throws Exception {
		TigerConverterAnnotationHandler handler = new TigerConverterAnnotationHandler();
		handler.registerConverters("hogeBean");
		TDateTimeConverter converter = (TDateTimeConverter) ConverterResource
				.getConverter("#{hogeBean.aaa}");
		assertNotNull(converter);
		assertEquals("time", converter.getType());
	}

	public void testConstantAnnotation() throws Exception {
		TigerConverterAnnotationHandler handler = new TigerConverterAnnotationHandler();
		handler.registerConverters("hogeBean");
		TDateTimeConverter converter = (TDateTimeConverter) ConverterResource
				.getConverter("#{hogeBean.bbb}");
		assertNotNull(converter);
		assertEquals("date", converter.getType());
	}

	public void testGetterMethod() throws Exception {
		TigerConverterAnnotationHandler handler = new TigerConverterAnnotationHandler();
		handler.registerConverters("hogeBean");
		TDateTimeConverter converter = (TDateTimeConverter) ConverterResource
				.getConverter("#{hogeBean.ccc}");
		assertNotNull(converter);
		assertEquals("date", converter.getType());
	}

	public static class HogeBean {

		@DateTimeConverter(type = "time")
		private Date aaa;

		public static final String bbb_TDateTimeConverter = null;

		private Date bbb;

		private Date ccc;

		/**
		 * @return Returns the aaa.
		 */
		public Date getAaa() {
			return aaa;
		}

		/**
		 * @param aaa
		 *            The aaa to set.
		 */
		public void setAaa(Date aaa) {
			this.aaa = aaa;
		}

		/**
		 * @return Returns the bbb.
		 */
		public Date getBbb() {
			return bbb;
		}

		/**
		 * @param bbb
		 *            The bbb to set.
		 */
		public void setBbb(Date bbb) {
			this.bbb = bbb;
		}

		/**
		 * @return Returns the ccc.
		 */
		@DateTimeConverter
		public Date getCcc() {
			return ccc;
		}

		/**
		 * @param ccc
		 *            The ccc to set.
		 */
		public void setCcc(Date ccc) {
			this.ccc = ccc;
		}

	}
}