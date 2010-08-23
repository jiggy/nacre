package com.nacre.service;

import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;

import javax.xml.namespace.NamespaceContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.xml.sax.SAXException;

import com.nacre.service.vo.ComplexType;
import com.nacre.service.vo.Decoration;
import com.nacre.service.vo.Field;
import com.nacre.service.vo.Form;
import com.nacre.service.vo.FormAction;
import com.nacre.service.vo.SimpleType;
import com.sun.xml.xsom.XSAnnotation;
import com.sun.xml.xsom.XSAttGroupDecl;
import com.sun.xml.xsom.XSAttributeDecl;
import com.sun.xml.xsom.XSAttributeUse;
import com.sun.xml.xsom.XSComplexType;
import com.sun.xml.xsom.XSComponent;
import com.sun.xml.xsom.XSContentType;
import com.sun.xml.xsom.XSElementDecl;
import com.sun.xml.xsom.XSFacet;
import com.sun.xml.xsom.XSIdentityConstraint;
import com.sun.xml.xsom.XSModelGroup;
import com.sun.xml.xsom.XSModelGroupDecl;
import com.sun.xml.xsom.XSNotation;
import com.sun.xml.xsom.XSParticle;
import com.sun.xml.xsom.XSRestrictionSimpleType;
import com.sun.xml.xsom.XSSchema;
import com.sun.xml.xsom.XSSchemaSet;
import com.sun.xml.xsom.XSSimpleType;
import com.sun.xml.xsom.XSTerm;
import com.sun.xml.xsom.XSWildcard;
import com.sun.xml.xsom.XSXPath;
import com.sun.xml.xsom.parser.XSOMParser;
import com.sun.xml.xsom.visitor.XSFunction;

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
	
	public Field query(String query) {
		XSComponent result = parsed.selectSingle(query, new NamespaceContext() {
			public String getNamespaceURI(String prefix) {
				return "http://www.nacre.com/test";
			}
			public String getPrefix(String namespaceURI) {
				return "";
			}
			public Iterator<String> getPrefixes(String namespaceURI) {
				return Arrays.asList(new String[]{""}).iterator();
			}
		});
		return result.apply(new XSFunction<Field>() {

			public Field annotation(XSAnnotation arg0) {
				System.out.println("Annotation?");
				// TODO Auto-generated method stub
				return null;
			}

			public Field attGroupDecl(XSAttGroupDecl arg0) {
				System.out.println("Attr group decl?");
				// TODO Auto-generated method stub
				return null;
			}

			public Field attributeDecl(XSAttributeDecl arg0) {
				System.out.println("Attr decl?");
				// TODO Auto-generated method stub
				return null;
			}

			public Field attributeUse(XSAttributeUse arg0) {
				System.out.println("Attr use?");
				// TODO Auto-generated method stub
				return null;
			}

			public Field complexType(XSComplexType arg0) {
				return getComplexType(arg0);
			}

			public Field facet(XSFacet arg0) {
				System.out.println("Facet ?");
				// TODO Auto-generated method stub
				return null;
			}

			public Field identityConstraint(XSIdentityConstraint arg0) {
				System.out.println("id constraint?");
				// TODO Auto-generated method stub
				return null;
			}

			public Field notation(XSNotation arg0) {
				System.out.println("Note?");
				// TODO Auto-generated method stub
				return null;
			}

			public Field schema(XSSchema arg0) {
				System.out.println("Schema?");
				// TODO Auto-generated method stub
				return null;
			}

			public Field xpath(XSXPath arg0) {
				System.out.println("XPath?");
				// TODO Auto-generated method stub
				return null;
			}

			public Field empty(XSContentType arg0) {
				System.out.println("content type?");
				// TODO Auto-generated method stub
				return null;
			}

			public Field particle(XSParticle arg0) {
				return parseParticle(arg0);
			}

			public Field simpleType(XSSimpleType arg0) {
				return getSimpleType(arg0);
			}

			public Field elementDecl(XSElementDecl arg0) {
				return parseTerm(arg0);
			}

			public Field modelGroup(XSModelGroup arg0) {
				return parseTerm(arg0);
			}

			public Field modelGroupDecl(XSModelGroupDecl arg0) {
				return parseTerm(arg0);
			}
			public Field wildcard(XSWildcard arg0) {
				System.out.println("wildcard?");
				// TODO Auto-generated method stub
				return null;
			}
		});
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
		ComplexType vo = null;
		XSComplexType type = null;
		for (XSSchema schema : parsed.getSchemas()) {
			type = schema.getComplexType(name);
		}
		if (type != null && type.getContentType() != null) {
			vo = getComplexType(type);
		}
		return vo;
	}

	private ComplexType getComplexType(XSComplexType type) {
		XSParticle particle = type.getContentType().asParticle();
		ComplexType vo = parseModelGroup(particle.getTerm().asModelGroup());
		vo.setMinOccurs(particle.getMinOccurs());
		vo.setMaxOccurs(particle.getMaxOccurs());
		vo.setName(type.getName());
		return vo;
	}

	private Field parseParticle(XSParticle particle) {
		Field vo = parseTerm(particle.getTerm());
		vo.setMaxOccurs(particle.getMaxOccurs());
		vo.setMinOccurs(particle.getMinOccurs());
		return vo;
	}
	private Field parseTerm(XSTerm term) {
		Field vo = null;
		if (term.isModelGroup()) {
			System.out.println("particle is model group");
			vo = parseModelGroup(term.asModelGroup());
		}
		else if (term.isElementDecl()) {
			System.out.println("particle is element decl");
			vo = parseElement(term.asElementDecl());
		} else if (term.isModelGroupDecl()) {
			System.out.println("particle is model group decl");
			vo = parseModelGroup(term.asModelGroupDecl().getModelGroup());
		} else if (term.isWildcard()) {
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
			Field field = parseParticle(child);
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
			field = findComplexType(elem.getType().getName());
			if (field == null) {
				field = findSimpleType(elem.getType().getName());
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

	public Field findSimpleType(String name) {
		XSSimpleType type = null;
		for (XSSchema schema : parsed.getSchemas()) {
			type = schema.getSimpleType(name);
		}
		if (type != null) {
			return getSimpleType(type);
		} else {
			System.out.println("No simple type named " + name);
			return null;
		}
	}

	private Field getSimpleType(XSSimpleType type) {
		if (type.isRestriction()) {
			XSRestrictionSimpleType restriction = type.asRestriction();
			return getRestriction(restriction);
		} else {
			SimpleType st = new SimpleType();
			st.setName(type.getName());
			st.setBaseType(type.getBaseType().getName());
			return st;
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