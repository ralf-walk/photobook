package com.aidado.editor.client.dialog;

import com.aidado.common.client.widget.CommonDialog;
import com.aidado.commoneditorviewer.client.model.HasPanel;
import com.aidado.commoneditorviewer.client.model.Model;
import com.aidado.commoneditorviewer.client.model.Panel;
import com.aidado.editor.client.event.EventManager;
import com.aidado.editor.client.locale.MessageBundle;

public abstract class ModelAwareDialog<T extends Model> extends CommonDialog {

	protected static final MessageBundle EM = MessageBundle.INSTANCE;

  public ModelAwareDialog(String caption) {
    super(caption);
  }

  public ModelAwareDialog(String caption, int width, int height) {
    super(caption);
  }

  private T model;

  protected T getModel() {
    return model;
  }
  
  protected void setModel(T model) {
    this.model = model;
  }

  @Override
  public void hide() {
    if (model != null && model instanceof HasPanel) {
    	EventManager.get().firePanelUpdatedEvent((Panel) model);
    }
    super.hide();
  }
}