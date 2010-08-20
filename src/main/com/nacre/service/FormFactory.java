package com.nacre.service;

import java.net.URL;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.xml.sax.SAXException;

import com.nacre.service.vo.ComplexType;
import com.nacre.service.vo.Decoration;
import com.nacre.service.vo.Field;
import com.nacre.service.vo.Form;
import com.nacre.service.vo.FormAction;
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

	public Form getForm(String name) {
		Form form = new Form();
		form.setForm(getComplexType(name));
		FormAction saveAction = new FormAction();
		saveAction.setLabel("Save");
		saveAction.setUri("/save");
		form.getActions().add(saveAction);
		return form;
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
			vo.setMinOccurs(particle.getMinOccurs());
			vo.setMaxOccurs(particle.getMaxOccurs());
		}
		return vo;
	}

	private Field parseParticle(XSParticle particle) {
		Field vo = null;
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
			Field field = parseParticle(child);//parseElement(child.getTerm().asElementDecl());
			vo.getFields().add(field);
		}
		return vo;
	}
	private Field parseElement(XSElementDecl elem) {
		Field field;
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
		com.sun.xml.xsom.XSAnnotation annotation = elem.getAnnotation();
		if (annotation != null) {
			field.setDecoration((Decoration) annotation.getAnnotation());
		} else {
			// TODO this should be configurable via schema-level annotation, options for delimiter-base parsing as well
			Decoration dec = new Decoration();
			dec.setLabel(WordUtils.capitalize(StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(elem.getName()), " ")));
			field.setDecoration(dec);
		}
		field.setName(elem.getName());
		return field;
	}

	public Field getSimpleType(String name) {
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

	private Field getRestriction(XSRestrictionSimpleType restriction) {
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