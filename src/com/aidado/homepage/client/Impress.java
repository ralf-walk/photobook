package com.aidado.homepage.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class Impress extends Composite {

	private static ImpressUiBinder uiBinder = GWT.create(ImpressUiBinder.class);

	interface ImpressUiBinder extends UiBinder<Widget, Impress> {
	}

	public Impress() {
		initWidget(uiBinder.createAndBindUi(this));
	}
}