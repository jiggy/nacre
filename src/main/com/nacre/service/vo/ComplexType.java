package com.nacre.service.vo;

import java.util.ArrayList;
import java.util.List;

public class ComplexType extends Field {
	
	private List<Field> fields;

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

}
