package com.nacre.service.vo;

public class SimpleType extends NacreField {
	private String baseType;

	public String getBaseType() {
		return baseType;
	}

	public void setBaseType(String baseType) {
		this.baseType = baseType;
	}

	@Override
	public FieldType getFieldType() {
		return FieldType.SimpleType;
	}

}
