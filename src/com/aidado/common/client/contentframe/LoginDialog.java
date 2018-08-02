package com.aidado.common.client.contentframe;

import com.aidado.common.client.CommonAsyncCallback;
import com.aidado.common.client.CookieAndUrlManagerClient;
import com.aidado.common.client.service.PhotobookService;
import com.aidado.common.client.widget.LayoutBuilder;
import com.aidado.common.client.widget.PasswordDialog;
import com.aidado.common.client.widget.TextBoxFactory;
import com.aidado.common.client.widget.LayoutBuilder.Mode;
import com.aidado.common.shared.AidadoPage;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextBox;

public class LoginDialog extends PasswordDialog {

	private final TextBox addressBox = TextBoxFactory.createTextBox(250, 27, null, null);

	public LoginDialog() {
		super(M.login());
		addressBox.addKeyPressHandler(ac);
		FlowPanel infoBox = LayoutBuilder.createBox(M.loginInfo(), Mode.SUCCESS, 350, 10);
		warnPanel.setVisible(false);
		FlowPanel inputPanel = LayoutBuilder.createCellPanel(100, 250).put(M.address()).put(addressBox).put(M.password()).put(passwordBox).setRowSpacing(10).build();
		FlowPanel content = LayoutBuilder.createFlowPanel().put(infoBox).put(warnPanel).put(inputPanel).put(buttonPanel).build();
		add(content, 10);
		showCenter();
	}

	@Override
	protected void onCancelClicked() {
		hide();
	}

	@Override
	protected void onPasswordEntered(String password) {
		String address = addressBox.getText() != null ? addressBox.getText() : "";
		String photobookId = address.substring(address.lastIndexOf("/") + 1, address.length()).trim().toUpperCase();
		if (photobookId.matches("[" + CookieAndUrlManagerClient.get().getPossiblePhotobookCharacters() + "]{5}")) {
			PhotobookService.initPhotobook(photobookId, password, new CommonAsyncCallback<Void>() {

				@Override
				public void onFailure(Throwable caught) {
					showWarning(M.loginAddressPasswordWarn());
				}

				@Override
				public void onSuccess(Void result) {
					CookieAndUrlManagerClient.get().redirectPage(AidadoPage.EDITOR);
				}
			});
		} else {
			showWarning(M.loginAddressWarn());
		}
	}

	@Override
	protected void onHide() {
		super.onHide();
		addressBox.setText(null);
	}

	@Override
	protected void onShow() {
		addressBox.setFocus(true);
	}
}