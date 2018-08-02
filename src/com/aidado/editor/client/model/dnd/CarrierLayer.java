package com.aidado.editor.client.model.dnd;

import com.aidado.commoneditorviewer.client.model.Panel;
import com.aidado.editor.client.Accessor;
import com.aidado.editor.client.event.EventManager;
import com.aidado.editor.client.event.PanelUpdatedEvent;
import com.aidado.editor.client.event.PanelUpdatedEvent.PanelUpdatedHandler;
import com.aidado.editor.client.model.EditorPagePanel;
import com.aidado.editor.client.model.EditorRootPanel;
import com.aidado.editor.client.model.dnd.CarrierPanel.CarrierAwarePanel;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;


public class CarrierLayer extends AbsolutePanel {

  private final ActionContainer ac = new ActionContainer();
  private final  CarrierDragPanel carrierDragPanel = new CarrierDragPanel(this);
  private final  CarrierActivePanel carrierActivePanel = new CarrierActivePanel(this);

  public CarrierLayer() {
    getElement().getStyle().setOverflow(Overflow.VISIBLE);
    addDomHandler(ac, MouseDownEvent.getType());
    addDomHandler(ac, MouseUpEvent.getType());
    addDomHandler(ac, MouseMoveEvent.getType());
    EventManager.get().addHandler(PanelUpdatedEvent.TYPE, ac);
  }
  
  public Panel getActivePanel() {
  	return carrierActivePanel.getPanel();
  }

  public void initDragCarrier(CarrierAwarePanel panel, NativeEvent mouseDownEvent) {
    carrierDragPanel.init(panel);
    DomEvent.fireNativeEvent(mouseDownEvent, carrierDragPanel);
  }
  
  public void initActivateCarrier(CarrierAwarePanel panel) {
    carrierActivePanel.init(panel);
    EventManager.get().firePanelUpdatedEvent((Panel)panel);
  }

  private class ActionContainer implements MouseDownHandler, MouseUpHandler, MouseMoveHandler, PanelUpdatedHandler {

  	@Override
    public void onMouseDown(MouseDownEvent event) {
      event.stopPropagation();
      event.preventDefault();
      Panel pagePanel = Accessor.getPhotobook().getCurrentPage();
      Panel panel = getHighestPossiblePanel(event.getRelativeX(pagePanel.getElement()), event.getRelativeY(pagePanel.getElement()));
      if (panel instanceof CarrierAwarePanel) {
        initDragCarrier((CarrierAwarePanel) panel, event.getNativeEvent());
      } else {
        carrierDragPanel.remove();
        EventManager.get().firePanelUpdatedEvent(Accessor.getPhotobook().getCurrentPage());
      }
    }

    public Panel getHighestPossiblePanel(int pageLeft, int pageTop) {
      EditorPagePanel pagePanel = (EditorPagePanel)Accessor.getPhotobook().getCurrentPage();
      Panel result = pagePanel;

      for (Panel child : pagePanel.getChildPanels()) {
        int childLeft = pagePanel.getDistanceLeft(child);
        int childTop = pagePanel.getDistanceTop(child);
        if (pageLeft >= childLeft && pageTop >= childTop && pageLeft <= childLeft + child.getOffsetWidth() && pageTop <= childTop + child.getOffsetHeight()) {
          result = child;
        }
      }
      return result;
    }

    @Override
    public void panelUpdated(Panel updatedPanel) {
      if (updatedPanel instanceof EditorPagePanel || updatedPanel instanceof EditorRootPanel) {
        carrierActivePanel.remove();
      }
    }

    @Override
    public void onMouseMove(MouseMoveEvent event) {
      event.stopPropagation();
      event.preventDefault();
    }

    @Override
    public void onMouseUp(MouseUpEvent event) {
      event.stopPropagation();
      event.preventDefault();
    }
  }
}