package com.aidado.viewer.client.model;

import com.aidado.commoneditorviewer.client.model.BaseImagePanel;
import com.aidado.viewer.client.Viewer;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;


public class ViewerImagePanel extends BaseImagePanel {

	@Override
  public void setSlideshowEnabled(boolean slideshowEnabled) {
	  super.setSlideshowEnabled(slideshowEnabled);
	  if (slideshowEnabled) {
	  	getElement().getStyle().setCursor(Cursor.POINTER);
	    addDomHandler(new MouseUpHandler() {

				@Override
        public void onMouseUp(MouseUpEvent event) {
	        Viewer.get().getPhotobook().getSlideShow().triggerShow(ViewerImagePanel.this);
        }}, MouseUpEvent.getType());
	  }
  }
}