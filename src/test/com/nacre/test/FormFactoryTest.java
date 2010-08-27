package com.nacre.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.nacre.service.FormFactory;
import com.nacre.service.vo.Field;
import com.nacre.service.vo.Form;

public class FormFactoryTest {
	String path = "/Article";
	String id = "";
	FormFactory formFactory = null;

	public void setup() {
		try {
			formFactory = new FormFactory(this.getClass().getResource("/test.xsd"));
		} catch (SAXException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetForm() {
		setup();
		Form art = formFactory.getForm("Article");
		assertNotNull(art);
	}
	
	@Test
	public void testQuery() {
		setup();

		Field ketchup = formFactory.query("/Article/ketchupOrMustard/ketchup");
		assertNotNull(ketchup);
		assertEquals(ketchup.getName(), "ketchup");
		assertEquals(ketchup.getDecoration().getLabel(), "Ketchup");
		
		Field headline = formFactory.query("/Article/headline");
		assertNotNull(headline);
		assertEquals(3, headline.getMaxOccurs());
		
		Field service = formFactory.query("/Article/byline@service");
		assertNotNull(service);
		assertTrue(service.isAttribute());
	}
	
}
