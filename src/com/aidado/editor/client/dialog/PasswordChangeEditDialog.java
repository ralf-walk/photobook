package com.aidado.editor.client.dialog;

import com.aidado.common.client.CommonAsyncCallback;
import com.aidado.common.client.InfoDialog;
import com.aidado.common.client.service.PhotobookService;
import com.aidado.common.client.widget.LayoutBuilder;
import com.aidado.common.client.widget.PasswordChangeDialog;
import com.aidado.common.client.widget.LayoutBuilder.Mode;
import com.aidado.editor.client.locale.MessageBundle;

public class PasswordChangeEditDialog extends PasswordChangeDialog {

  private static final MessageBundle EM = MessageBundle.INSTANCE;

  public PasswordChangeEditDialog(String password) {
    super(EM.passwordForEdit());
    init(password, EM.infoChangePasswordForEdit(), EM.changePassword(), M.newPassword(), M.confirmNewPassword(), null);
    showCenter();
  }

  @Override
  protected void passwordUpdated(String password) {
    PhotobookService.savePhotobookPasswordForEdit(password, new CommonAsyncCallback<Void>() {

      @Override
      public void onSuccess(Void result) {
        new InfoDialog(LayoutBuilder.createBox(EM.passwordChanged(), Mode.SUCCESS, 150, 5));
      }
    });
  }
}