package com.aidado.commoneditorviewer.client.model;

import java.util.ArrayList;
import java.util.List;

import com.aidado.commoneditorviewer.client.model.Model.Item;
import com.google.gwt.user.client.ui.Widget;


public abstract class BasePagePanel extends ExtensionPanel implements Item, HasBackground, HasImageBorder, HasCssBorder, HasBorderRadius, HasShadow {

	public BasePagePanel() {
	}
	
	@Override
	protected void update() {
		super.update();
		for (Panel p : getChildPanels()) {
			p.requestUpdate();
		}
	}

	public void addChildPanel(Panel childPanel) {
		add((Panel) childPanel);
	}

	public List<Panel> getChildPanels() {
		List<Panel> childList = new ArrayList<Panel>();
		for (int i = 0; i < getWidgetCount(); i++) {
			Widget child = getWidget(i);
			if (child instanceof Panel && child instanceof Item) {
				childList.add((Panel) child);
			}
		}
		return childList;
	}
}