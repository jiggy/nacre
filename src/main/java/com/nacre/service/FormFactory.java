package com.nacre.service;

import java.net.URL;

import org.apache.commons.lang.StringUtils;
import org.xml.sax.SAXException;

import com.nacre.service.vo.ComplexType;
import com.nacre.service.vo.Field;
import com.nacre.service.vo.Form;
import com.nacre.service.vo.FormAction;
import com.nacre.service.vo.Field.FieldType;
import com.sun.xml.xsom.XSComplexType;
import com.sun.xml.xsom.XSSchema;
import com.sun.xml.xsom.XSSchemaSet;
import com.sun.xml.xsom.XSSimpleType;
import com.sun.xml.xsom.parser.XSOMParser;

public class FormFactory
{
	static final String XSD_NAMESPACE = "http://www.w3.org/2001/XMLSchema";
	private XSSchemaSet parsed;
	public FormFactory(URL... xsd) throws SAXException {
		XSOMParser parser = new XSOMParser();
		parser.setEntityResolver(ParsingUtils.nacreXSOMEntityResolver);
		parser.setErrorHandler(ParsingUtils.nacreXSOMErrorHandler);
		parser.setAnnotationParser(ParsingUtils.nacreXSOMAnnotationParserFactory);
		for (URL schema : xsd) {
			// TODO check last modified timestamp and only reload if necessary
			parser.parse(schema);
		}
		parsed = parser.getResult();
	}
	
	public Field query(String query) {
		// TODO this should ideally use SCD, but the implementation seems too buggy to work
		System.out.println("Query for " + query);
		String[] tokens = StringUtils.split(query, "/@");
		if (tokens.length > 0) {
			ComplexType ct = findComplexType(tokens[0]);
			if (tokens.length > 1) {
				Field f = null;
				for (int i = 1; i < tokens.length; i++) {
					boolean found = false;
					for (Field child : ct.getFields()) {
						System.out.println("compare " + tokens[i] + " with " + child.getName());
						if (child.getName().equals(tokens[i])) {
							f = child;
							found = true;
							break;
						}
					}
					if (!found) {
						return null;
					}
					if (f.getFieldType().equals(FieldType.ComplexType)) {
						ct = (ComplexType) f;
					}
				}
				return f;
			} else {
				return ct;
			}
		}
		return null;
	}

	public Form getForm(String name) {
		Form form = new Form();
		form.setForm(findComplexType(name));
		FormAction saveAction = new FormAction();
		saveAction.setLabel("Save");
		saveAction.setUri("/save");
		form.getActions().add(saveAction);
		return form;
	}
	
	public ComplexType findComplexType(String name) {
		System.out.println("Getting form " + name);
		XSComplexType type = null;
		for (XSSchema schema : parsed.getSchemas()) {
			type = schema.getComplexType(name);
		}
		return (ComplexType) type.apply(ParsingUtils.nacreXSOMFunction);
	}

	public Field findSimpleType(String name) {
		XSSimpleType type = null;
		for (XSSchema schema : parsed.getSchemas()) {
			type = schema.getSimpleType(name);
		}
		if (type != null) {
			return type.apply(ParsingUtils.nacreXSOMFunction);
		} else {
			System.out.println("No simple type named " + name);
			return null;
		}
	}
}