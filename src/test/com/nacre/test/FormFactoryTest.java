package com.nacre.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.nacre.service.FormFactory;
import com.nacre.service.vo.Field;

public class FormFactoryTest {
	@Test
	public void testQuery() throws SAXException {
		FormFactory formFactory = new FormFactory(this.getClass().getResource("/test.xsd"));
		Field art = formFactory.findComplexType("Article");
		assertNotNull(art);

		Field author = formFactory.query("/type::Article//element::byline//element::author");
		assertNotNull(author);
		assertEquals(author.getName(), "author");
		assertEquals(author.getDecoration().getLabel(), "Author");
	}
}
