package com.aidado.commoneditorviewer.client;

import com.aidado.commoneditorviewer.client.model.BaseImagePanel;
import com.aidado.commoneditorviewer.client.model.BasePagePanel;
import com.aidado.commoneditorviewer.client.model.BaseRootPanel;
import com.aidado.commoneditorviewer.client.model.BaseTextPanel;

public class PanelFactoryManager {

	private static PanelFactory FACTORY;

	public static void init(PanelFactory FACTORY) {
		PanelFactoryManager.FACTORY = FACTORY;
	}

	public static PanelFactory getFactory() {
		return FACTORY;
	}

	public interface PanelFactory {

		BaseRootPanel createRootPanel();

		BasePagePanel createPagePanel();

		BaseImagePanel createImagePanel();

		BaseTextPanel createTextPanel();
	}
}