package com.aidado.homepage.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;


public class Features extends Composite {

	private static FeaturesUiBinder uiBinder = GWT.create(FeaturesUiBinder.class);

	interface FeaturesUiBinder extends UiBinder<Widget, Features> {
	}

	public Features() {
		initWidget(uiBinder.createAndBindUi(this));
	}
}
