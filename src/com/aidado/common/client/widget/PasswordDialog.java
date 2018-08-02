package com.aidado.common.client.widget;

import com.aidado.common.client.widget.Button.ButtonListener;
import com.aidado.common.client.widget.LayoutBuilder.Mode;
import com.aidado.common.client.widget.LayoutBuilder.CellPanelBuilder.Alignment;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PasswordTextBox;

public abstract class PasswordDialog extends CommonDialog {

	protected final ActionContainer ac = new ActionContainer();
	private final HTML warnMsg = new HTML();
	private final Button okButton = Button.createOkButton(ac);
	private final Button cancelButton = Button.createCancelButton(ac);

	protected final FlowPanel warnPanel = LayoutBuilder.createBox(warnMsg, Mode.WARNING, 350, 10);
	protected final PasswordTextBox passwordBox = TextBoxFactory.createPasswordTextBox(250, 30, null, null);
  protected final FlowPanel buttonPanel = LayoutBuilder.createCellPanel(110, 100).put(cancelButton).put(okButton).setMargins(0, 10, 0, 10).setAlignment(Alignment.RIGHT).build();

	public PasswordDialog(String caption) {
	  super(caption);
	  passwordBox.addKeyPressHandler(ac);
		showWarning(null);
  }

	@Override
	protected void onHide() {
		showWarning(null);
		passwordBox.setText(null);
	}

	@Override
	protected void onShow() {
		passwordBox.setFocus(true);
	}

	protected void showWarning(String warning) {
		warnMsg.setHTML(warning);
		warnPanel.setVisible(warning != null);
	}

	protected abstract void onPasswordEntered(String password);

	protected abstract void onCancelClicked();
	
	private class ActionContainer implements ButtonListener, KeyPressHandler {

		@Override
		public void onClick(Button button, boolean pushed) {
			if (button == okButton) {
				checkPassword();
			} else if (button == cancelButton) {
				onCancelClicked();
			}
		}
		
    @Override
    public void onKeyPress(KeyPressEvent event) {
      if (event.getCharCode() == KeyCodes.KEY_ENTER || event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
      	checkPassword();
      }
    }
    
    private void checkPassword() {
			String password = passwordBox.getText();
			if (password != null && password.length() >= 6 && password.length() <= 20 && !password.contains(" ")) {
				showWarning(null);
				onPasswordEntered(password);
			} else {
				showWarning(M.passwordError());
			}    	
    }
	}
}
