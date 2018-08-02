package com.aidado.viewer.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.VerticalAlign;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class Viewer {

	private static Viewer INSTANCE;
	private final ViewerPhotobook photobook;

	public Viewer(String photobookJson) {
		INSTANCE = this;
		FlowPanel tableDiv = new FlowPanel();
		tableDiv.getElement().getStyle().setHeight(100, Unit.PCT);
		tableDiv.getElement().getStyle().setProperty("display", "table");
		tableDiv.getElement().getStyle().setProperty("margin", "auto");
		tableDiv.getElement().getStyle().setProperty("textAlign", "center");

		// add photo-book
		photobook = new ViewerPhotobook(photobookJson);
		FlowPanel tableCellDiv = new FlowPanel();
		tableCellDiv.getElement().getStyle().setProperty("display", "table-cell");
		tableCellDiv.getElement().getStyle().setVerticalAlign(VerticalAlign.MIDDLE);
		tableCellDiv.add(photobook);

		// add anchors
		AnchorBox anchorBox = new AnchorBox();
		anchorBox.getElement().getStyle().setMarginTop(10, Unit.PX);
		tableCellDiv.add(anchorBox);

		tableDiv.add(tableCellDiv);
		RootPanel.get().add(tableDiv);
	}

	public static Viewer get() {
		return INSTANCE;
	}

	public ViewerPhotobook getPhotobook() {
		return photobook;
	}
}