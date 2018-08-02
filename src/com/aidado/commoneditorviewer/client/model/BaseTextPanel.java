package com.aidado.commoneditorviewer.client.model;

import com.aidado.commoneditorviewer.client.model.Model.Item;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;


public abstract class BaseTextPanel extends ExtensionPanel implements Item, HasCssBorder, HasImageBorder, HasShadow, HasBorderRadius, HasBackground, HasRotation, HasOpacity {

	private final Panel containerPanel = new Panel();
	protected final HTML htmlText = new HTML();

	public BaseTextPanel() {
		Style containerPanelStyle = containerPanel.getElement().getStyle();
		containerPanelStyle.setOverflow(Overflow.VISIBLE);
		containerPanelStyle.setWidth(100, Unit.PCT);
		containerPanelStyle.setHeight(100, Unit.PCT);

		Style htmlTextStyle = htmlText.getElement().getStyle();
		htmlTextStyle.setPadding(5, Unit.PX);
		htmlTextStyle.setWidth(100, Unit.PCT);
		htmlTextStyle.setHeight(100, Unit.PCT);
		htmlTextStyle.setOverflow(Overflow.HIDDEN);
		containerPanel.add(htmlText);

		add(containerPanel);
	}
	
	@Override
	public void setBorderRadius(int borderRadius) {
		super.setBorderRadius(borderRadius);
		String borderRadiusValue = new StringBuilder().append(borderRadius).append("px").toString();
		containerPanel.getElement().getStyle().setProperty("borderRadius", borderRadiusValue);
	}

	public String getHtmlText() {
		return htmlText.getHTML();
	}

	public void setHtmlText(String htmlText) {
		this.htmlText.setHTML(htmlText);
	}

	@Override
	public Widget getHasRotationWidget() {
		return containerPanel;
	}

	@Override
	public Widget getHasOpacityWidget() {
		return containerPanel;
	}

	@Override
  public Widget getHasImageBorderWidget() {
		return containerPanel;
  }

	@Override
  public Widget getHasBackgroundWidget() {
		return containerPanel;
  }

	@Override
  public Widget getHasShadowWidget() {
		return containerPanel;
  }

	@Override
  public Widget getHasCssBorderWidget() {
		return containerPanel;
  }
}