package com.aidado.editor.client.event;

import com.aidado.commoneditorviewer.client.model.Panel;
import com.aidado.editor.client.event.PanelUpdatedEvent.PanelUpdatedHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;


public class PanelUpdatedEvent extends GwtEvent<PanelUpdatedHandler> {

  public interface PanelUpdatedHandler extends EventHandler {
    public void panelUpdated(Panel updatedPanel);
  }

  public static final Type<PanelUpdatedHandler> TYPE = new Type<PanelUpdatedHandler>();

  private final Panel updatedPanel;

  public PanelUpdatedEvent(Panel updatedPanel) {
    this.updatedPanel = updatedPanel;
  }

  @Override
  protected void dispatch(PanelUpdatedHandler handler) {
    handler.panelUpdated(updatedPanel);
  }

  @Override
  public Type<PanelUpdatedHandler> getAssociatedType() {
    return TYPE;
  }
}