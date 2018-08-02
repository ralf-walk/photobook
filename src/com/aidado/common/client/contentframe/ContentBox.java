package com.aidado.common.client.contentframe;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ContentBox extends Composite {

	private static ContentBoxUiBinder uiBinder = GWT.create(ContentBoxUiBinder.class);

	interface ContentBoxUiBinder extends UiBinder<Widget, ContentBox> {
	}

	@UiField(provided = true)
	final Widget content;

	@UiField(provided = true)
	final Widget asideContent;

	public ContentBox(Widget content, Widget asideContent) {
		this.content = content;
		this.asideContent = asideContent;
		initWidget(uiBinder.createAndBindUi(this));
	}
}