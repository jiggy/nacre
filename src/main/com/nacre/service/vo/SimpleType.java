package com.nacre.service.vo;

public class SimpleType extends Field {
	private String baseType;
	private int maxLength;
	private int minLength;
	private String pattern;
	private String dflt;
	public String getDflt() {
		return dflt;
	}

	public void setDflt(String dflt) {
		this.dflt = dflt;
	}

	public String getFixed() {
		return fixed;
	}

	public void setFixed(String fixed) {
		this.fixed = fixed;
	}

	private String fixed;

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public int getMinLength() {
		return minLength;
	}

	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

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
