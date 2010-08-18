package com.nacre.service.vo;

import java.util.ArrayList;

import java.util.List;

public class Form extends ComplexType {
	private List<FormAction> actions;

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
