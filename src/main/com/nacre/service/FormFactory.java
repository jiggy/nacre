package com.nacre.service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.nacre.service.vo.ComplexType;
import com.nacre.service.vo.NacreForm;
import com.nacre.service.vo.NacreField;
import com.nacre.service.vo.SimpleType;
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
	private static final String XSD_NAMESPACE = "http://www.w3.org/2001/XMLSchema";
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

	public ComplexType getComplexType(String name) {
		System.out.println("Getting form " + name);
		ComplexType vo = null;
		XSComplexType type = null;
		for (XSSchema schema : parsed.getSchemas()) {
			type = schema.getComplexType(name);
		}
		if (type != null && type.getContentType() != null) {
			XSParticle particle = type.getContentType().asParticle();
			vo = parseModelGroup(particle.getTerm().asModelGroup());
		}
		return vo;
	}

	private NacreField parseParticle(XSParticle particle) {
		NacreField vo = null;
		if (particle.getTerm().isModelGroup()) {
			System.out.println("particle is model group");
			vo = parseModelGroup(particle.getTerm().asModelGroup());
		}
		else if (particle.getTerm().isElementDecl()) {
			System.out.println("particle is element decl");
			vo = parseElement(particle.getTerm().asElementDecl());
		} else if (particle.getTerm().isModelGroupDecl()) {
			System.out.println("particle is model group decl");
			vo = parseModelGroup(particle.getTerm().asModelGroupDecl().getModelGroup());
		} else if (particle.getTerm().isWildcard()) {
			System.out.println("particle is wildcard");
			// ????
		}
		return vo;
	}
	
	private ComplexType parseModelGroup(XSModelGroup group) {
		ComplexType vo = new ComplexType();
		System.out.println(group.getChildren().length + " children");
		for (XSParticle child : group.getChildren()) {
			System.out.println("child is " + child.getTerm());
			NacreField field = parseParticle(child);//parseElement(child.getTerm().asElementDecl());
			vo.getFields().add(field);
		}
		return vo;
	}
	private NacreField parseElement(XSElementDecl elem) {
		NacreField field;
		System.out.println("\telem " + elem.getName() + " " + elem.getType().getName() + " ns: " + elem.getType().getTargetNamespace());
		if (elem.getType().getName() == null) {
			// anonymous restriction
			field = getRestriction(elem.getType().asSimpleType().asRestriction());
		} else if (!elem.getType().getTargetNamespace().equals(XSD_NAMESPACE)) {
			// user-defined type
			field = getComplexType(elem.getType().getName());
			if (field == null) {
				field = getSimpleType(elem.getType().getName());
			}
		} else {
			// simple type, no restriction
			field = new SimpleType();
			((SimpleType)field).setBaseType(elem.getType().getName());
		}
		field.setName(elem.getName());
		return field;
	}

	public NacreField getSimpleType(String name) {
		XSSimpleType type = null;
		for (XSSchema schema : parsed.getSchemas()) {
			type = schema.getSimpleType(name);
		}
		if (type != null && type.isRestriction()) {
			XSRestrictionSimpleType restriction = type.asRestriction();
			return getRestriction(restriction);
		} else {
			System.out.println("No simple type named " + name);
			return null;
		}
	}
	public NacreField getRestriction(XSRestrictionSimpleType restriction) {
		SimpleType field = new SimpleType();
		System.out.println("\t\t restriction " + restriction.getName() + ", base " + restriction.getBaseType().getName() + ", facets: " + restriction.getDeclaredFacets().size());
		for (XSFacet facet : restriction.getDeclaredFacets(XSFacet.FACET_MAXLENGTH)) {
			System.out.println("\t\t\t facet: " + facet.getValue());
			field.setMaxLength(new Integer(facet.getValue().toString()));
		}
		for (XSFacet facet : restriction.getDeclaredFacets(XSFacet.FACET_MINLENGTH)) {
			System.out.println("\t\t\t facet: " + facet.getValue());
			field.setMinLength(new Integer(facet.getValue().toString()));
		}
		for (XSFacet facet : restriction.getDeclaredFacets(XSFacet.FACET_PATTERN)) {
			System.out.println("\t\t\t facet: " + facet.getValue());
			field.setPattern(facet.getValue().toString());
		}
		field.setBaseType(restriction.getBaseType().getName());
		return field;
	}
	
}