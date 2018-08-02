package com.aidado.homepage.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class Start extends Composite {

  interface StartUiBinder extends UiBinder<Widget, Start> {
  }

  private static StartUiBinder uiBinder = GWT.create(StartUiBinder.class);

  public Start() {
    initWidget(uiBinder.createAndBindUi(this));
  }
}