package com.aidado.homepage.client;

import com.aidado.common.client.CookieAndUrlManagerClient;
import com.aidado.common.client.contentframe.ContentBox;
import com.aidado.common.client.contentframe.ContentFrame;
import com.aidado.common.shared.AidadoPage;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.user.client.ui.RootPanel;

public class HomepageEntryPoint implements EntryPoint {

	@Override
	public void onModuleLoad() {
		RootPanel.get().getElement().getStyle().setOverflowY(Overflow.SCROLL);
		AidadoPage page = CookieAndUrlManagerClient.get().getCurrentPage();
		PhotobookStartPanel photobookStartPanel = new PhotobookStartPanel();
		//RootPanel.get().add(new ContentFrame(new ContentBox(new Start(), photobookStartPanel), false));
		if (page == AidadoPage.START) {
			RootPanel.get().add(new ContentFrame(new ContentBox(new Start(), photobookStartPanel), false));
		} else if (page == AidadoPage.FEATURES) {
			RootPanel.get().add(new ContentFrame(new ContentBox(new Features(), photobookStartPanel), false));
		} else if (page == AidadoPage.IMPRESS) {
			RootPanel.get().add(new ContentFrame(new ContentBox(new Impress(), photobookStartPanel), false));
		} else if (page == AidadoPage.AGB) {
			RootPanel.get().add(new ContentFrame(new ContentBox(new AGB(), photobookStartPanel), false));
		}
	}
}