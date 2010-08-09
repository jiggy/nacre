package com.nacre.service.vo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class FormVO {
	private List<Field> fields;
	private List<FormVO> children;// TODO
	public List<Field> getFields() {
		if (fields == null) {
			fields = new ArrayList<Field>();
		}
		return fields;
	}
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	public String toString() {
		return StringUtils.join(fields.toArray());
	}
	public static class Field {
		private String name;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		private String type;
		public String toString() {
			return name + ":" + type;
		}
	}
}
