package com.aidado.common.client.widget;

import com.aidado.common.client.StyleSetter;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FlowPanel;

public class FuelbarPanel extends FlowPanel {
	
	private final FlowPanel fuelBar = new FlowPanel();

	public FuelbarPanel(int width, int height) {
		setPixelSize(width, height);
		Style fuelbarStyle = getElement().getStyle();
		fuelbarStyle.setProperty("border", "1px solid #000000");
		fuelbarStyle.setProperty("borderRadius", "5px");
		fuelbarStyle.setBackgroundColor("#FFFFFF");
		fuelbarStyle.setOverflow(Overflow.HIDDEN);
		StyleSetter.set(getElement(), "background-image", "linear-gradient(top, rgba(180, 180, 180, 0.2),	rgba(20, 20, 20, 0.3))", false, true);
		fuelBar.getElement().getStyle().setHeight(height - 2, Unit.PX);
		add(fuelBar);
	}
	
	public void setPct(int pct) {
		int maxColorValue = 255;
		int red = (int) (maxColorValue * (Math.min(100, pct * 2)/100.));
		int green = (int) (maxColorValue * ((100 - pct)/100.));
		fuelBar.getElement().getStyle().setBackgroundColor("rgb(" + red +  "," + green + ",0)");
		fuelBar.getElement().getStyle().setWidth(pct, Unit.PCT);
	}
}