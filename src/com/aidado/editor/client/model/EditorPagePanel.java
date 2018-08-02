package com.aidado.editor.client.model;

import com.aidado.commoneditorviewer.client.model.BasePagePanel;
import com.aidado.editor.client.event.EventManager;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.ui.Widget;


public class EditorPagePanel extends BasePagePanel {

	private final ActionContainer ac = new ActionContainer();
	
  public EditorPagePanel() {
    setPixelSize(1000, 740);
    Style style = getElement().getStyle();
    style.setPadding(0, Unit.PX);
    style.setBorderWidth(0, Unit.PX);
    style.setOverflow(Overflow.VISIBLE);
    setBackgroundColor("#FFF2AD");
    
    setCssBorderType(CssBorderType.SOLID);
    setBorderSize(3);
    setBorderRadius(5);

    addDomHandler(ac, MouseUpEvent.getType());
  }

  public boolean isPanelIntersecting(Widget w1, Widget w2) {
    int dtLeft = getDistanceLeft(w1);
    int dtTop = getDistanceTop(w1);
    int dtWidth = w1.getOffsetWidth();
    int dtHeight = w1.getOffsetHeight();
    int dsLeft = getDistanceLeft(w2);
    int dsTop = getDistanceTop(w2);
    int dsWidth = w2.getOffsetWidth();
    int dsHeight = w2.getOffsetHeight();

    int xDiff = Math.max(dsLeft + dsWidth, dtLeft + dtWidth) - Math.min(dsLeft, dtLeft);
    int yDiff = Math.max(dsTop + dsHeight, dtTop + dtHeight) - Math.min(dsTop, dtTop);
    return (dsWidth + dtWidth > xDiff && dsHeight + dtHeight > yDiff);
  }
  
  private class ActionContainer implements MouseUpHandler {

		@Override
    public void onMouseUp(MouseUpEvent event) {
			event.stopPropagation();
			event.preventDefault();
			EventManager.get().firePanelUpdatedEvent(EditorPagePanel.this);	    
    }
  }
}