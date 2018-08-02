package com.aidado.editor.client.event;

import com.aidado.commoneditorviewer.client.model.Panel;
import com.aidado.editor.client.event.PanelRemovedEvent.PanelRemovedHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;


public class PanelRemovedEvent extends GwtEvent<PanelRemovedHandler> {

  public interface PanelRemovedHandler extends EventHandler {
    public void panelRemoved(Panel removedPanel);
  }

  public static final Type<PanelRemovedHandler> TYPE = new Type<PanelRemovedHandler>();

  private final Panel removedPanel;

  public PanelRemovedEvent(Panel removedPanel) {
    this.removedPanel = removedPanel;
  }

  @Override
  protected void dispatch(PanelRemovedHandler handler) {
    handler.panelRemoved(removedPanel);
  }

  @Override
  public Type<PanelRemovedHandler> getAssociatedType() {
    return TYPE;
  }
}