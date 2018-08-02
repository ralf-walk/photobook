package com.aidado.viewer.client;

import com.aidado.common.client.CommonAsyncCallback;
import com.aidado.common.client.CookieAndUrlManagerClient;
import com.aidado.common.client.InfoDialog;
import com.aidado.common.client.InfoDialog.InfoDialogListener;
import com.aidado.common.client.service.PhotobookService;
import com.aidado.common.client.widget.LayoutBuilder;
import com.aidado.common.client.widget.LayoutBuilder.Mode;
import com.aidado.common.client.widget.PhotobookLoadDialog.PhotobookLoadListener;
import com.aidado.common.shared.AidadoPage;
import com.aidado.commoneditorviewer.client.PanelFactoryManager;
import com.aidado.viewer.client.dialog.PhotobookLoadViewDialog;
import com.aidado.viewer.client.locale.MessageBundle;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

public class ViewerEntryPoint implements EntryPoint, PhotobookLoadListener {

	@Override
	public void onModuleLoad() {
		String photobook = CookieAndUrlManagerClient.get().getPhotobook();
		PanelFactoryManager.init(new PanelFactoryViewer());
		onPhotobookLoad(photobook);
/*		String photobookId = CookieAndUrlManagerClient.get().getPhotobookIdUrl();
		if (photobookId != null) {
			PanelFactoryManager.init(new PanelFactoryViewer());
			PhotobookService.getPhotobookForView(photobookId, null, new CommonAsyncCallback<String>() {

				@Override
				public void onSuccess(String photobook) {
					onPhotobookLoad(photobook);
				}

				@Override
				public void onFailure(Throwable caught) {
					new PhotobookLoadViewDialog(ViewerEntryPoint.this);
				}
			});
		}*/
	}

	@Override
	public void onPhotobookLoad(String photobook) {
		if (photobook == null) {
			new InfoDialog(LayoutBuilder.createBox(MessageBundle.INSTANCE.noPages(), Mode.WARNING, 260, 10), false, new InfoDialogListener() {

				@Override
				public void onOkClicked() {
					CookieAndUrlManagerClient.get().redirectPage(AidadoPage.EDITOR);
				}

				@Override
				public void onCancelClicked() {
				}
			});
		} else {
			new Viewer(photobook);
		}
	}
}