package com.aidado.viewer.client.dialog;

import com.aidado.common.client.CommonAsyncCallback;
import com.aidado.common.client.CookieAndUrlManagerClient;
import com.aidado.common.client.service.PhotobookService;
import com.aidado.common.client.widget.LayoutBuilder;
import com.aidado.common.client.widget.PhotobookLoadDialog;
import com.aidado.common.client.widget.LayoutBuilder.Mode;
import com.aidado.viewer.client.AnchorBox;
import com.aidado.viewer.client.locale.MessageBundle;
import com.google.gwt.user.client.ui.FlowPanel;

public class PhotobookLoadViewDialog extends PhotobookLoadDialog {

	private static final MessageBundle VM = MessageBundle.INSTANCE;

	public PhotobookLoadViewDialog(PhotobookLoadListener listener) {
		super(VM.passwordRequired(), listener);
		FlowPanel infoBox = LayoutBuilder.createBox(VM.passwordRequiredInfo(), Mode.SUCCESS, 350, 10);
		FlowPanel inputPanel = LayoutBuilder.createCellPanel(100, 250).put(M.password()).put(passwordBox).setRowSpacing(10).build();
		FlowPanel content = LayoutBuilder.createFlowPanel().put(warnPanel).put(infoBox).put(inputPanel).put(buttonPanel).setMargins(0, 0, 0, 10).put(new AnchorBox()).build();
		add(content, 10);
		showCenter();
	}

	@Override
	protected void onPasswordEntered(String password) {
		String photobookId = CookieAndUrlManagerClient.get().getPhotobookIdUrl();
		PhotobookService.getPhotobookForView(photobookId, password, new CommonAsyncCallback<String>() {

			@Override
			public void onSuccess(String photobook) {
				hide();
				listener.onPhotobookLoad(photobook);
			}

			@Override
			public void onFailure(Throwable caught) {
				showWarning(M.passwordInvalid());
			}
		});
	}
}