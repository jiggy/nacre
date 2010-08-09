package com.nacre.service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;

import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.nacre.service.vo.FormVO;
import com.nacre.service.vo.FormVO.Field;
import com.sun.xml.xsom.XSComplexType;
import com.sun.xml.xsom.XSElementDecl;
import com.sun.xml.xsom.XSFacet;
import com.sun.xml.xsom.XSModelGroup;
import com.sun.xml.xsom.XSParticle;
import com.sun.xml.xsom.XSRestrictionSimpleType;
import com.sun.xml.xsom.XSSchema;
import com.sun.xml.xsom.XSSchemaSet;
import com.sun.xml.xsom.XSSimpleType;
import com.sun.xml.xsom.parser.XSOMParser;

public class FormFactory
{
	private XSSchemaSet parsed;
	public FormFactory(URL xsd) throws SAXException {
		XSOMParser parser = new XSOMParser();
		parser.setEntityResolver(new EntityResolver() {
			public InputSource resolveEntity(String publicId, String systemId)
					throws SAXException, IOException {
				System.out.println("looking for sysid: [" + systemId + "]");
				try {
					return new InputSource(new FileReader(new File(new URI(systemId))));
				} catch (URISyntaxException e) {
					System.err.println("failed to locate URI: " + systemId);
					return null;
				}
			}
		});
		parser.setErrorHandler(new ErrorHandler() {
			public void error(SAXParseException exception) throws SAXException {
				log(exception);
			}
			public void fatalError(SAXParseException exception)
					throws SAXException {
				log(exception);			
			}
			public void warning(SAXParseException exception)
					throws SAXException {
				log(exception);
			}
			private void log(SAXParseException exception) {
				System.out.println(exception.getMessage());
				exception.printStackTrace();
			}
		});
		parser.parse(xsd);
		parsed = parser.getResult();
	}
	public FormVO getComplexType(String name) {
		FormVO vo = null;
		XSComplexType type = null;
		for (XSSchema schema : parsed.getSchemas()) {
			type = schema.getComplexType(name);
		}
		if (type != null && type.getContentType() != null) {
			XSParticle particle = type.getContentType().asParticle();
			vo = parseParticle(particle);
		}
		return vo;
	}
	public void getSimpleType(String name) {
		XSSimpleType type = null;
		for (XSSchema schema : parsed.getSchemas()) {
			type = schema.getSimpleType(name);
		}
		if (type != null && type.isRestriction()) {
			XSRestrictionSimpleType restriction = type.asRestriction();
			getRestriction(restriction);
		} else {
			System.out.println("No simple type named " + name);
		}
	}
	public void getRestriction(XSRestrictionSimpleType restriction) {
		System.out.println("\t\t restriction " + restriction.getName() + ", base " + restriction.getBaseType().getName() + ", facets: " + restriction.getDeclaredFacets().size());
		for (XSFacet facet : restriction.getDeclaredFacets(XSFacet.FACET_MAXLENGTH)) {
			System.out.println("\t\t\t facet: " + facet.getValue());
		}		
	}
	public void foo() {
		Iterator<XSSchema> schemas = parsed.iterateSchema();
		while (schemas.hasNext()) {
			XSSchema schema = schemas.next();
			if (!schema.getTargetNamespace().equals("http://www.w3.org/2001/XMLSchema")) {
				System.out.println("tgt namespace: " + schema.getTargetNamespace());
				for (String simpleTypeName : schema.getSimpleTypes().keySet()) {
					XSSimpleType type = schema.getSimpleType(simpleTypeName);
					if (type.isSimpleType()) {
						System.out.print("simple type: " + simpleTypeName + ", facets: " + type.asRestriction().getDeclaredFacets().size() + " ");
						for (XSFacet facet : type.asRestriction().getDeclaredFacets()) {
							System.out.print(facet.getValue() + ",");
						}
						System.out.println();
					}
				}
				for (String complexTypeName : schema.getComplexTypes().keySet()) {
					XSComplexType type = schema.getComplexType(complexTypeName);
					System.out.println("Complex type : " + complexTypeName);
					XSParticle particle = type.getContentType().asParticle();
					parseParticle(particle);
				}
			}
		}
	}
	
	private FormVO parseParticle(XSParticle particle) {
		FormVO vo = null;
		if (particle.getTerm().isModelGroup()) {
			vo = parseModelGroup(particle.getTerm().asModelGroup());
		}
		else if (particle.getTerm().isElementDecl()) {
			parseElement(particle.getTerm().asElementDecl());
		} else if (particle.getTerm().isModelGroupDecl()) {
			// ????
		} else if (particle.getTerm().isWildcard()) {
			// ????
		}
		return vo;
	}
	
	private FormVO parseModelGroup(XSModelGroup group) {
		FormVO vo = new FormVO();
		for (XSParticle child : group.getChildren()) {
			//parseParticle(child);
			Field field = parseElement(child.getTerm().asElementDecl());
			vo.getFields().add(field);
		}
		return vo;
	}
	private Field parseElement(XSElementDecl elem) {
		Field field = new Field();
		System.out.println("\telem " + elem.getName() + " " + elem.getType().getName() + " restriction? " + elem.getType().asSimpleType().isRestriction());
		if (elem.getType().getName() == null) {
			getRestriction(elem.getType().asSimpleType().asRestriction());
		} else if (!elem.getType().getTargetNamespace().equals("http://www.w3.org/2001/XMLSchema")) {
			getComplexType(elem.getType().getName());
			getSimpleType(elem.getType().getName());
		}
		field.setName(elem.getName());
		field.setType(elem.getType().getName());
		return field;
	}
}