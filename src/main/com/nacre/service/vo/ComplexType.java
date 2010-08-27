package com.nacre.service.vo;

import java.util.ArrayList;
import java.util.List;

public class ComplexType extends Field {
	
	private List<Field> fields;
	private CombinationType combinationType;

	public List<Field> getFields() {
		if (fields == null) {
			fields = new ArrayList<Field>();
		}
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	@Override
	public FieldType getFieldType() {
		return FieldType.ComplexType;
	}
	
	public void setCombinationType(CombinationType combinationType) {
		this.combinationType = combinationType;
	}

	public CombinationType getCombinationType() {
		return combinationType;
	}

	public enum CombinationType {
		and,or;
	}

}
