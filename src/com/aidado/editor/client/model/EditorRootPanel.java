package com.aidado.editor.client.model;

import com.aidado.commoneditorviewer.client.model.BaseRootPanel;
import com.aidado.editor.client.Accessor;
import com.aidado.editor.client.event.EventManager;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.ui.Widget;


public class EditorRootPanel extends BaseRootPanel {

	private final ActionContainer ac = new ActionContainer();

	public EditorRootPanel() {
		Accessor.getContentFrame().getRoot().addDomHandler(ac, MouseUpEvent.getType());
  }

	@Override
	public void insert(Widget w, int left, int top, int beforeIndex) {
		Accessor.getContentFrame().getRoot().insert(w, left, top, beforeIndex);
	}

	@Override
	public int getOffsetHeight() {
		return Accessor.getContentFrame().getRoot().getOffsetHeight();
	}

	@Override
	public int getOffsetWidth() {
		return Accessor.getContentFrame().getRoot().getOffsetWidth();
	}

	@Override
	public Widget getHasBackgroundWidget() {
		return Accessor.getContentFrame().getRoot();
	}

	private class ActionContainer implements MouseUpHandler {

		@Override
		public void onMouseUp(MouseUpEvent event) {
			event.stopPropagation();
			event.preventDefault();
			EventManager.get().firePanelUpdatedEvent(EditorRootPanel.this);
		}
	}
}