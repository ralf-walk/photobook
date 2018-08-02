package com.aidado.editor.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.FlowPanel;

public class Editor extends FlowPanel {

	private EditorPhotobook photobook;

	public Editor(String photobookJson) {
		Style style = getElement().getStyle();
		style.setBackgroundColor("#C3D9FF");
		style.setProperty("borderRadius", "50px");
		photobook = new EditorPhotobook(photobookJson);

		add(new PhotobookControls());
		add(photobook);
	}

	public EditorPhotobook getPhotobook() {
		return photobook;
	}
}