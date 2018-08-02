package com.aidado.viewer.client.model;

import com.aidado.commoneditorviewer.client.model.BaseRootPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class ViewerRootPanel extends BaseRootPanel {

	@Override
	public Widget getHasBackgroundWidget() {
		return RootPanel.get();
	}

	@Override
	public void setTitle(String title) {
		String htmlTitle = title != null && title.trim().length() > 0 ? title + " &ndash; aidado" : "aidado &ndash; Your online Photobook";
		RootPanel.get("pageTitle").getElement().setInnerHTML(htmlTitle);
	}
}