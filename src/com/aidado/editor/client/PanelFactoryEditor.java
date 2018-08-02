package com.aidado.editor.client;

import com.aidado.commoneditorviewer.client.PanelFactoryManager.PanelFactory;
import com.aidado.commoneditorviewer.client.model.BaseImagePanel;
import com.aidado.commoneditorviewer.client.model.BasePagePanel;
import com.aidado.commoneditorviewer.client.model.BaseRootPanel;
import com.aidado.commoneditorviewer.client.model.BaseTextPanel;
import com.aidado.editor.client.model.EditorImagePanel;
import com.aidado.editor.client.model.EditorPagePanel;
import com.aidado.editor.client.model.EditorRootPanel;
import com.aidado.editor.client.model.EditorTextPanel;

public class PanelFactoryEditor implements PanelFactory {

	@Override
	public BaseRootPanel createRootPanel() {
		return new EditorRootPanel();
	}

	@Override
  public BasePagePanel createPagePanel() {
    return new EditorPagePanel();
  }

  @Override
  public BaseTextPanel createTextPanel() {
    return new EditorTextPanel();
  }

  @Override
  public BaseImagePanel createImagePanel() {
    return new EditorImagePanel();
  }
}