package com.aidado.viewer.client;

import com.aidado.commoneditorviewer.client.Photobook;
import com.aidado.commoneditorviewer.client.model.BaseRootPanel;
import com.aidado.viewer.client.slideshow.SlideShowPanel;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.RootPanel;

public class ViewerPhotobook extends Photobook {
	
	private SlideShowPanel slideshow;

	public ViewerPhotobook(String photobookContent) {
		super(photobookContent);
		getElement().getStyle().setProperty("margin", "auto");
	}

	@Override
  public void init(String photobookJson) {
		super.init(photobookJson);
		BaseRootPanel rootPanel = getRootPanel();
		slideshow = new SlideShowPanel(rootPanel); 
		History.addValueChangeHandler(slideshow);
		RootPanel.get().add(slideshow);
		showPage(getRootPanel().getPagePanels().get(0));
  }
	
	public SlideShowPanel getSlideShow() {
		return slideshow;
	}
}