package com.aidado.homepage.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class AGB extends Composite {

	private static AGBUiBinder uiBinder = GWT.create(AGBUiBinder.class);

	interface AGBUiBinder extends UiBinder<Widget, AGB> {
	}

	public AGB() {
		initWidget(uiBinder.createAndBindUi(this));
	}
}