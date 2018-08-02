package com.aidado.viewer.client;

import com.aidado.commoneditorviewer.client.PanelFactoryManager.PanelFactory;
import com.aidado.commoneditorviewer.client.model.BaseImagePanel;
import com.aidado.commoneditorviewer.client.model.BasePagePanel;
import com.aidado.commoneditorviewer.client.model.BaseRootPanel;
import com.aidado.commoneditorviewer.client.model.BaseTextPanel;
import com.aidado.viewer.client.model.ViewerImagePanel;
import com.aidado.viewer.client.model.ViewerPagePanel;
import com.aidado.viewer.client.model.ViewerRootPanel;
import com.aidado.viewer.client.model.ViewerTextPanel;

public class PanelFactoryViewer implements PanelFactory {

	@Override
	public BaseRootPanel createRootPanel() {
		return new ViewerRootPanel();
	}

  @Override
  public BaseTextPanel createTextPanel() {
    return new ViewerTextPanel();
  }

  @Override
  public BasePagePanel createPagePanel() {
    return new ViewerPagePanel();
  }

  @Override
  public BaseImagePanel createImagePanel() {
    return new ViewerImagePanel();
  }
}