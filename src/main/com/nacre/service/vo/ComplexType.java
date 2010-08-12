package com.nacre.service.vo;

import java.util.ArrayList;
import java.util.List;

public class ComplexType extends NacreField {
	
	private List<NacreField> fields;

	public List<NacreField> getFields() {
		if (fields == null) {
			fields = new ArrayList<NacreField>();
		}
		return fields;
	}

	public void setFields(List<NacreField> fields) {
		this.fields = fields;
	}

	@Override
	public FieldType getFieldType() {
		return FieldType.ComplexType;
	}

}
