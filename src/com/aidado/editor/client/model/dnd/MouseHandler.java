package com.aidado.editor.client.model.dnd;

import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;

public class MouseHandler implements MouseDownHandler, MouseUpHandler, MouseMoveHandler {

	@Override
	public void onMouseMove(MouseMoveEvent event) {
		stopEvent(event);
	}

	@Override
	public void onMouseUp(MouseUpEvent event) {
		stopEvent(event);
	}

	@Override
	public void onMouseDown(MouseDownEvent event) {
		stopEvent(event);
	}

	private void stopEvent(MouseEvent<?> event) {
		event.stopPropagation();
		event.preventDefault();
	}
}