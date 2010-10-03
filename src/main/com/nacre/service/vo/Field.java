package com.nacre.service.vo;

import java.util.ArrayList;
import java.util.List;

public abstract class Field {
	private String id;
	private String name;
	private String namespace;
	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	private int minOccurs;
	private int maxOccurs;
	private Decoration decoration;
	private List<Field> attributes;

	public List<Field> getAttributes() {
		if (attributes == null) {
			attributes = new ArrayList<Field>();
		}
		return attributes;
	}

	public void setAttributes(List<Field> attributes) {
		this.attributes = attributes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Decoration getDecoration() {
		return decoration;
	}

	public void setDecoration(Decoration decoration) {
		this.decoration = decoration;
	}

	public int getMinOccurs() {
		return minOccurs;
	}
	
	public boolean isRequired() {
		return minOccurs > 0;
	}
	
	public boolean isAttribute() {
		return false;
	}

	public void setMinOccurs(int minOccurs) {
		this.minOccurs = minOccurs;
	}

	public int getMaxOccurs() {
		return maxOccurs;
	}

	public void setMaxOccurs(int maxOccurs) {
		this.maxOccurs = maxOccurs;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public abstract FieldType getFieldType();

	public static enum FieldType {
		ComplexType, SimpleType;
	}

}
