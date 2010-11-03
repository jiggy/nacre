package com.nacre.service.vo;

public class SimpleType extends Field {
	private boolean isAttribute = false;
	private String baseType;
	private String dflt;
	private String fixed;

	private Integer maxLength;
	private Integer minLength;
	private Integer length;
	private Integer fractionDigits;
	private Integer totalDigits;
	private WhiteSpaceRestriction whitespace;
	private String pattern;
	// should these just be strings? data type should correspond to base type
	private Double minInclusive;
	private Double maxInclusive;
	private Double minExclusive;
	private Double maxExclusive;

	public String getDefault() {
		return dflt;
	}

	public void setDefault(String dflt) {
		this.dflt = dflt;
	}

	public String getFixed() {
		return fixed;
	}

	public void setFixed(String fixed) {
		this.fixed = fixed;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	public Integer getMinLength() {
		return minLength;
	}

	public void setMinLength(Integer minLength) {
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
	
	public static enum WhiteSpaceRestriction {
		preserve, collapse, replace;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getFractionDigits() {
		return fractionDigits;
	}

	public void setFractionDigits(Integer fractionDigits) {
		this.fractionDigits = fractionDigits;
	}

	public Integer getTotalDigits() {
		return totalDigits;
	}

	public void setTotalDigits(Integer totalDigits) {
		this.totalDigits = totalDigits;
	}

	public WhiteSpaceRestriction getWhitespace() {
		return whitespace;
	}

	public void setWhitespace(WhiteSpaceRestriction whitespace) {
		this.whitespace = whitespace;
	}

	public Double getMinInclusive() {
		return minInclusive;
	}

	public void setMinInclusive(Double minInclusive) {
		this.minInclusive = minInclusive;
	}

	public Double getMaxInclusive() {
		return maxInclusive;
	}

	public void setMaxInclusive(Double maxInclusive) {
		this.maxInclusive = maxInclusive;
	}

	public Double getMinExclusive() {
		return minExclusive;
	}

	public void setMinExclusive(Double minExclusive) {
		this.minExclusive = minExclusive;
	}

	public Double getMaxExclusive() {
		return maxExclusive;
	}

	public void setMaxExclusive(Double maxExclusive) {
		this.maxExclusive = maxExclusive;
	}

	public boolean isAttribute() {
		return isAttribute;
	}

	public void setAttribute(boolean isAttribute) {
		this.isAttribute = isAttribute;
	}

}
