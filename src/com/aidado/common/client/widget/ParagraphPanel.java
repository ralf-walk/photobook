package com.aidado.common.client.widget;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.SimplePanel;

public class ParagraphPanel extends SimplePanel {

  public ParagraphPanel(String text, int fontSize) {
  	super((Element) Document.get().createPElement().cast());
		Style style = getElement().getStyle();
		style.setFontSize(fontSize, Unit.PX);
		style.setMargin(0, Unit.PX);
		style.setProperty("lineHeight", "1");
    getElement().setInnerText(SafeHtmlUtils.fromString(text != null ? text : "").asString());
  }
}