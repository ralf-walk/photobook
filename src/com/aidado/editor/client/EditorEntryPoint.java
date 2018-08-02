package com.aidado.editor.client;

import com.aidado.common.client.CommonAsyncCallback;
import com.aidado.common.client.CookieAndUrlManagerClient;
import com.aidado.common.client.contentframe.ContentFrame;
import com.aidado.common.client.service.PhotobookService;
import com.aidado.common.client.widget.PhotobookLoadDialog.PhotobookLoadListener;
import com.aidado.common.shared.AidadoPage;
import com.aidado.common.shared.CookieAndUrlManager.Session;
import com.aidado.common.shared.exception.SessionInvalidException;
import com.aidado.commoneditorviewer.client.PanelFactoryManager;
import com.aidado.editor.client.dialog.ImageDialog;
import com.aidado.editor.client.dialog.PhotobookLoadEditDialog;
import com.aidado.editor.client.dialog.PropertiesDialog;
import com.aidado.editor.client.dialog.WelcomeDialog;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.user.client.ui.RootPanel;

public class EditorEntryPoint implements EntryPoint, PhotobookLoadListener {

	private static ContentFrame contentFrame = null;

	@Override
	public void onModuleLoad() {
		CookieAndUrlManagerClient manager = CookieAndUrlManagerClient.get();

		onPhotobookLoad(manager.getPhotobook());

		/* Session session = manager.getSession();
		String photobookId = session.getPhotobookId();
		if (photobookId == null) {
			manager.redirectPage(AidadoPage.START);
		} else {
			PhotobookService.getPhotobookForEdit(new CommonAsyncCallback<String>() {

				@Override
				public void onSuccess(String photobook) {
					onPhotobookLoad(photobook);
				}

				@Override
        public void onFailure(Throwable caught) {
					if (caught instanceof SessionInvalidException) {
						new PhotobookLoadEditDialog(EditorEntryPoint.this);
					} else {
						super.onFailure(caught);
					}
				}
			});
		}*/
	}

	@Override
  public void onPhotobookLoad(String photobook) {
		RootPanel.get().getElement().getStyle().setOverflowY(Overflow.SCROLL);
		PanelFactoryManager.init(new PanelFactoryEditor());

		Editor editor = new Editor(photobook);
		EditorEntryPoint.contentFrame = new ContentFrame(editor, true);

		new PropertiesDialog();
		new ImageDialog();

		RootPanel.get().add(contentFrame);

		if (photobook == null) {
			new WelcomeDialog().showDialog();
		}
  }

	public static ContentFrame getContentFrame() {
		return contentFrame;
	}
}