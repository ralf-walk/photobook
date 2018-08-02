package com.aidado.editor.client.dialog;

import com.aidado.common.client.CommonAsyncCallback;
import com.aidado.common.client.InfoDialog;
import com.aidado.common.client.service.PhotobookService;
import com.aidado.common.client.widget.LayoutBuilder;
import com.aidado.common.client.widget.PasswordChangeDialog;
import com.aidado.common.client.widget.LayoutBuilder.Mode;
import com.aidado.editor.client.locale.MessageBundle;

public class PasswordChangeViewDialog extends PasswordChangeDialog {

  private static final MessageBundle EM = MessageBundle.INSTANCE;

  protected PasswordChangeViewDialog(String password) {
    super(EM.passwordForView());
    init(password, EM.infoChangePasswordForView(), EM.changePassword(), M.newPassword(), M.confirmNewPassword(), null);
    showCenter();
  }

  @Override
  protected void passwordUpdated(String password) {
    PhotobookService.savePhotobookPasswordForView(password, new CommonAsyncCallback<Void>() {

      @Override
      public void onSuccess(Void result) {
        hide();
        new InfoDialog(LayoutBuilder.createBox(EM.passwordChanged(), Mode.SUCCESS, 150, 5));
      }
    });
  }
}