package com.aidado.common.client.widget;

import com.aidado.common.client.widget.Button.ButtonListener;
import com.aidado.common.client.widget.LayoutBuilder.FlowPanelBuilder;
import com.aidado.common.client.widget.LayoutBuilder.Mode;
import com.aidado.common.client.widget.LayoutBuilder.CellPanelBuilder.Alignment;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public abstract class PasswordChangeDialog extends CommonDialog {

  private final ActionContainer ac = new ActionContainer();
  private final Label warnMsg = new Label();
  private final FlowPanel warnPanel = LayoutBuilder.createBox(warnMsg, Mode.WARNING, 320, 10);

  private final HTML currentPassword = new HTML();
  private final TitleComposite passwordComposite = new TitleComposite(LayoutBuilder.createCellPanel(50).put(currentPassword).setAlignment(Alignment.CENTER).setMargins(0, 5).build(), M.password());

  private final TextBox passwordBox = TextBoxFactory.createTextBox(200, 10, null, null);
  private final TextBox confirmPasswordBox = TextBoxFactory.createTextBox(200, 10, null, null);

  private final Button okButton = Button.createOkButton(ac);
  private final Button cancelButton = Button.createCancelButton(ac);

  protected PasswordChangeDialog(String title) {
    super(title);
  }

  protected void init(String password, String info, String passwordHeader, String passwordLabel, String passwordConfirmLabel, Widget additionalContent) {
    FlowPanelBuilder contentBuiler = LayoutBuilder.createFlowPanel();
    if (password != null && password.trim().length() > 0) {
      passwordComposite.setVisible(true);
      currentPassword.setHTML(new SafeHtmlBuilder().appendHtmlConstant("<b>").appendEscaped(password).appendHtmlConstant("</b>").toSafeHtml());
    } else {
      passwordComposite.setVisible(false);
    }
    contentBuiler.put(passwordComposite);

    FlowPanel infoBox = LayoutBuilder.createBox(info, Mode.SUCCESS, 320, 10);
    infoBox.getElement().getStyle().setMargin(10, Unit.PX);
    contentBuiler.put(infoBox);

    warnPanel.getElement().getStyle().setMargin(10, Unit.PX);
    warnPanel.setVisible(false);
    contentBuiler.put(warnPanel);

    TitleComposite newPasswordComposite = new TitleComposite(LayoutBuilder.createCellPanel(120, 200).put(passwordLabel).put(passwordBox).put(passwordConfirmLabel).put(confirmPasswordBox)
        .setRowSpacing(10).setMargins(10, 0).build(), passwordHeader);
    contentBuiler.put(newPasswordComposite);

    if (additionalContent != null) {
      contentBuiler.put(additionalContent);
    }

    Widget buttons = LayoutBuilder.createCellPanel(110, 100).put(okButton).put(cancelButton).setAlignment(Alignment.RIGHT).setMargins(10, 10, 10, 10).build();
    contentBuiler.put(buttons);
    add(contentBuiler.build());

  }

  protected abstract void passwordUpdated(String password);

  @Override
  protected void onHide() {
    showWarning(null);
    passwordBox.setText(null);
    confirmPasswordBox.setText(null);
  }

  protected void showWarning(String warning) {
    warnMsg.setText(warning);
    warnPanel.setVisible(warning != null);
  }

  protected boolean validate() {
    String password = passwordBox.getText();
    if (password != null && password.length() >= 6 && password.length() <= 20 && !password.contains(" ")) {
      if (password.equals(confirmPasswordBox.getText())) {
        return true;
      } else {
        showWarning(M.passwordConfirmationError());
      }
    } else {
      showWarning(M.passwordError());
    }
    return false;
  }

  private class ActionContainer implements ButtonListener {

    @Override
    public void onClick(Button button, boolean pushed) {
      if (button == okButton) {
        if (validate()) {
          hide();
          passwordUpdated(passwordBox.getText());
        }
      } else if (button == cancelButton) {
        hide();
      }
    }
  }
}