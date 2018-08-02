package com.aidado.commoneditorviewer.client.model;

import java.util.ArrayList;
import java.util.List;

import com.aidado.commoneditorviewer.client.model.Model.Item;


public abstract class BaseRootPanel extends ExtensionPanel implements Item, HasBackground, HasTitle {

	private final List<BasePagePanel> pagePanelList = new ArrayList<BasePagePanel>();

	public List<BasePagePanel> getPagePanels() {
		return pagePanelList;
	}
}