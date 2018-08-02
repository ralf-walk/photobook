package com.aidado.common.client.widget;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class TitleComposite extends Composite {

	public TitleComposite() {
	}

	public TitleComposite(Widget widget, String title) {
		initWidget(widget, title);
	}

	public void initWidget(Widget widget, String title) {
		if (title != null) {
			FlowPanel content = new FlowPanel();
			HeadingElement headElement = Document.get().createHElement(1);
			Style headElementStyle = headElement.getStyle();
			headElement.setInnerText(title);
			headElementStyle.setColor("grey");
			headElementStyle.setMarginBottom(10, Unit.PX);
			headElementStyle.setPaddingLeft(5, Unit.PX);
			headElementStyle.setProperty("fontSize", "medium");
			headElementStyle.setBackgroundColor("#e3e8f3");
			content.getElement().appendChild(headElement);
			content.add(widget);
			super.initWidget(content);
		} else {
			super.initWidget(widget);
		}
	}
}