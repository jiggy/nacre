package com.nacre.service.vo;

import java.util.ArrayList;

import java.util.List;

public class Form {
	private List<FormAction> actions;
	private ComplexType form;

	public ComplexType getForm() {
		return form;
	}

	public void setForm(ComplexType form) {
		this.form = form;
	}

	public List<FormAction> getActions() {
		if (actions == null) {
			actions = new ArrayList<FormAction>();
		}
		return actions;
	}

	public void setActions(List<FormAction> actions) {
		this.actions = actions;
	}
}
