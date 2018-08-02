package com.aidado.homepage.client.dialog;

import com.aidado.common.client.CommonAsyncCallback;
import com.aidado.common.client.CookieAndUrlManagerClient;
import com.aidado.common.client.service.PhotobookService;
import com.aidado.common.client.widget.LayoutBuilder;
import com.aidado.common.client.widget.PasswordChangeDialog;
import com.aidado.common.client.widget.TitleComposite;
import com.aidado.common.shared.AidadoPage;
import com.aidado.homepage.client.locale.MessageBundle;
import com.google.gwt.user.client.ui.CheckBox;

public class CreatePhotobookDialog extends PasswordChangeDialog {

  private static final MessageBundle HM = MessageBundle.INSTANCE;
  private final CheckBox termsCheckBox = new CheckBox(CookieAndUrlManagerClient.get().isProdMode() ? HM.terms() : HM.termsDev());

  public CreatePhotobookDialog() {
    super(HM.createPhotobookHeader());
    TitleComposite terms = new TitleComposite(LayoutBuilder.createCellPanel(300).put(termsCheckBox).setMargins(10, 0).build(), HM.termsHeader());
    init(null, HM.createPhotobookInfo(), M.password(), M.password(), M.confirmPassword(), terms);
    showCenter();
  }

  @Override
  protected void passwordUpdated(String password) {
    // PhotobookService.createAndInitPhotobook(password, new CommonAsyncCallback<Void>() {

    //  @Override
    //  public void onSuccess(Void result) {
        CookieAndUrlManagerClient manager = CookieAndUrlManagerClient.get();
        manager.redirectPage(AidadoPage.EDITOR);
    //  }
    // });
  }

  @Override
  protected boolean validate() {
    if (super.validate()) {
      if (!termsCheckBox.getValue()) {
        showWarning(HM.acceptTerms());
      } else {
        return true;
      }
    }
    return false;
  }
}