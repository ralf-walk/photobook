package com.aidado.common.client;

import com.aidado.common.client.InfoDialog.InfoDialogListener;
import com.aidado.common.client.locale.MessageBundle;
import com.aidado.common.client.widget.LayoutBuilder;
import com.aidado.common.client.widget.LayoutBuilder.Mode;
import com.aidado.common.shared.AidadoPage;
import com.aidado.common.shared.exception.NoPhotobookException;
import com.aidado.common.shared.exception.SessionInvalidException;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class CommonAsyncCallback<T> implements AsyncCallback<T> {

  private static final MessageBundle M = MessageBundle.INSTANCE;

  public void onFailure(Throwable caught) {
    InfoDialogListener listener = new InfoDialogListener() {

      @Override
      public void onOkClicked() {
        CookieAndUrlManagerClient.get().redirectPage(AidadoPage.START);
      }
    };

    try {
      throw caught;
    } catch (SessionInvalidException e) {
      new InfoDialog(LayoutBuilder.createBox(M.sessionInvalidText(), Mode.ERROR, 400, 10), false, listener);
    } catch (NoPhotobookException e) {
      new InfoDialog(LayoutBuilder.createBox(M.noPhotobookText(), Mode.ERROR, 400, 10), false, listener);
    } catch (Throwable e) {
      new InfoDialog(LayoutBuilder.createBox(M.unknownError(), Mode.ERROR, 400, 10), false, listener);
    }
  }

  @Override
  public void onSuccess(T result) {
  }
}
