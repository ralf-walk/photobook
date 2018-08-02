package com.aidado.viewer.client.slideshow;

import com.aidado.common.client.widget.RatioKeepingImage;
import com.aidado.viewer.client.model.ViewerImagePanel;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;

public class ImageTitlePanel extends FlowPanel {

	private final RatioKeepingImage image = new RatioKeepingImage(RatioKeepingImage.Mode.SHRINK_IN_PARENT);
	private final SimplePanel titlePanel = new SimplePanel();
	private final Label title = new Label();

	public ImageTitlePanel(ViewerImagePanel imagePanel) {
		Style style = getElement().getStyle();
		style.setWidth(100, Unit.PCT);
		style.setHeight(100, Unit.PCT);
		style.setPosition(Position.FIXED);
		style.setBackgroundColor("#000000");

		image.setUrl(imagePanel.getImageUrl());
		image.getElement().getStyle().setProperty("margin", "auto");
		image.getElement().getStyle().setPosition(Position.ABSOLUTE);
		add(image);

		title.setText(imagePanel.getTitle());			
		title.getElement().getStyle().setFontSize(2, Unit.EM);
		title.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		title.getElement().getStyle().setColor("#CCCCCC");
		title.getElement().getStyle().setOverflow(Overflow.VISIBLE);
		title.getElement().getStyle().setProperty("textShadow", "0 0 2px #000000");
		title.getElement().getStyle().setProperty("margin", "auto");
		title.getElement().getStyle().setProperty("textAlign", "center");
		titlePanel.getElement().getStyle().setPosition(Position.FIXED);
		titlePanel.getElement().getStyle().setOverflow(Overflow.VISIBLE);
		titlePanel.getElement().getStyle().setBottom(10, Unit.PX);
		titlePanel.getElement().getStyle().setWidth(100, Unit.PCT);
		titlePanel.add(title);
		add(titlePanel);
		update();
	}

	@Override
	protected void onLoad() {
		super.onLoad();
		update();
	}

	public void update() {
		image.update();
		title.setWidth(image.getWidth() + "px");
	}
}