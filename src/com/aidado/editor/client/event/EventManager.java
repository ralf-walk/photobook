package com.aidado.editor.client.event;

import com.aidado.commoneditorviewer.client.model.Panel;
import com.google.gwt.event.shared.SimpleEventBus;


public class EventManager extends SimpleEventBus {

	private static EventManager INSTANCE = new EventManager();
	
	public static EventManager get() {
		return INSTANCE;
	}
	
  public void firePanelUpdatedEvent(Panel clickedPanel) {
    PanelUpdatedEvent event = new PanelUpdatedEvent(clickedPanel);
    fireEvent(event);
  }

  public void firePanelRemovedEvent(Panel removedPanel) {
    PanelRemovedEvent event = new PanelRemovedEvent(removedPanel);
    fireEvent(event);
  }
}