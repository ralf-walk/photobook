package com.aidado.editor.client;

import com.aidado.common.client.contentframe.ContentFrame;

public class Accessor {

	public static ContentFrame getContentFrame() {
		return EditorEntryPoint.getContentFrame();
	}

	public static Editor getEditor() {
		return (Editor)getContentFrame().getContent();
	}
	
	public static EditorPhotobook getPhotobook() {
		return getEditor().getPhotobook();
	}
}