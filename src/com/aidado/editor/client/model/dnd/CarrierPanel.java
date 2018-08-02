package com.aidado.editor.client.model.dnd;

import com.aidado.commoneditorviewer.client.model.HasPanel;
import com.aidado.commoneditorviewer.client.model.Panel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public abstract class CarrierPanel extends Panel {

	public interface CarrierAwarePanel extends IsWidget, HasPanel {
		boolean isDropPossible(Widget w, boolean draggingStarted);

		void onDrop(Widget w);

		void onClick();
	}

	protected final CarrierLayer carrierLayer;
	protected Panel panel;

	protected CarrierPanel(CarrierLayer carrierLayer) {
		this.carrierLayer = carrierLayer;
	}

	public void init(CarrierAwarePanel panel) {
		remove();
		carrierLayer.add(this);
		setWidth(panel.getWidth());
		setHeight(panel.getHeight());
		setLeft(panel.getLeft());
		setTop(panel.getTop());
		this.panel = (Panel) panel;
		requestUpdate();
	}

	public void remove() {
		if (isAttached()) {
			panel = null;
			removeFromParent();
		}
	}

	public Panel getPanel() {
		return panel;
	}

	@Override
	public void setPixelSize(int width, int height) {
		super.setPixelSize(width, height);
		if (panel != null) {
			panel.setPixelSize(width, height);
		}
	}

	@Override
	public void setLeft(int left) {
		super.setLeft(left);
		if (panel != null) {
			panel.setLeft(left);
		}
	}

	@Override
	public void setTop(int top) {
		super.setTop(top);
		if (panel != null) {
			panel.setTop(top);
		}
	}

	@Override
	public void setWidth(int width) {
		super.setWidth(width);
		if (panel != null) {
			panel.setWidth(width);
		}
	}

	@Override
	public void setHeight(int height) {
		super.setHeight(height);
		if (panel != null) {
			panel.setHeight(height);
		}
	}

	@Override
	public void setZIndex(int zIndex) {
		super.setZIndex(zIndex);
		if (panel != null) {
			panel.setZIndex(zIndex - 5);
		}
	}

	@Override
	public void clearZIndex() {
		super.clearZIndex();
		if (panel != null) {
			panel.clearZIndex();
		}
	}
}