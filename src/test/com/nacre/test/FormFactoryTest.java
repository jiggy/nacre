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

		Field author = formFactory.query("/type::Article//element::byline");
		assertNotNull(author);
		assertEquals(author.getName(), "byline");
		assertEquals(author.getDecoration().getLabel(), "Byline");
	}
	
}
