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

	@Test
	public void testQuery() throws SAXException {
		FormFactory formFactory = new FormFactory(this.getClass().getResource("/test.xsd"));
		Field art = formFactory.findComplexType("Article");
		assertNotNull(art);
		
		Form artForm = formFactory.getForm("Article");
		for (Field f : artForm.getForm().getFields()) {
			getField(f);
		}

		Field author = formFactory.query("/type::Article//element::byline");
		assertNotNull(author);
		assertEquals(author.getName(), "byline");
		assertEquals(author.getDecoration().getLabel(), "Byline");
	}
	
	private void getField(Field f) {
		path += "/" + f.getName();
		for (int i = 1; i < Math.max(1, f.getMinOccurs()); i++) {
			path += "[" + i + "]";
		}
	}
}
