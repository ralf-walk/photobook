package com.aidado.editor.client.dialog;

import com.aidado.common.client.CommonAsyncCallback;
import com.aidado.common.client.CookieAndUrlManagerClient;
import com.aidado.common.client.service.PhotobookService;
import com.aidado.common.client.widget.LayoutBuilder;
import com.aidado.common.client.widget.PhotobookLoadDialog;
import com.aidado.common.client.widget.LayoutBuilder.Mode;
import com.aidado.editor.client.locale.MessageBundle;
import com.google.gwt.user.client.ui.FlowPanel;

public class PhotobookLoadEditDialog extends PhotobookLoadDialog {

	private static final MessageBundle EM = MessageBundle.INSTANCE;
	
	public PhotobookLoadEditDialog(PhotobookLoadListener listener) {
		super(M.login(), listener);
		FlowPanel infoBox = LayoutBuilder.createBox(EM.loginInfo(), Mode.SUCCESS, 350, 10);
		FlowPanel inputPanel = LayoutBuilder.createCellPanel(100, 250).put(M.password()).put(passwordBox).setRowSpacing(10).build();
		FlowPanel content = LayoutBuilder.createFlowPanel().put(warnPanel).put(infoBox).put(inputPanel).put(buttonPanel).build();
		add(content, 10);
		showCenter();
	}

	@Override
  protected void onPasswordEntered(String password) {
		String photobookId = CookieAndUrlManagerClient.get().getSession().getPhotobookId();
		PhotobookService.initPhotobook(photobookId, password, new CommonAsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
				PhotobookService.getPhotobookForEdit(new CommonAsyncCallback<String>() {

					@Override
					public void onSuccess(String photobook) {
						hide();
						listener.onPhotobookLoad(photobook);
					}
				});
			}

			@Override
			public void onFailure(Throwable caught) {
				showWarning(M.passwordInvalid());
			}
		});
  }
}