package com.aidado.editor.client.dialog;

import com.aidado.common.client.CommonAsyncCallback;
import com.aidado.common.client.CookieAndUrlManagerClient;
import com.aidado.common.client.InfoDialog;
import com.aidado.common.client.service.MailService;
import com.aidado.common.client.widget.Button;
import com.aidado.common.client.widget.Button.ButtonListener;
import com.aidado.common.client.widget.CommonDialog;
import com.aidado.common.client.widget.LayoutBuilder;
import com.aidado.common.client.widget.LayoutBuilder.CellPanelBuilder.Alignment;
import com.aidado.common.client.widget.LayoutBuilder.FlowPanelBuilder;
import com.aidado.common.client.widget.LayoutBuilder.Mode;
import com.aidado.common.client.widget.TextBoxFactory;
import com.aidado.editor.client.locale.MessageBundle;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextBox;

public class WelcomeDialog extends CommonDialog {

    private static final MessageBundle EM = MessageBundle.INSTANCE;

    private final ActionContainer ac = new ActionContainer();
    private final Button okButton = Button.createOkButton(ac);

    private final CheckBox sendMailCheckBox = new CheckBox(EM.sendMail());
    private final FlowPanel mailWarnBox = LayoutBuilder.createBox(EM.mailError(), Mode.ERROR, 300, 10);
    private final TextBox mailTextBox = TextBoxFactory.createTextBox(200, 50, null, null);
    private final FlowPanel mailPanel = LayoutBuilder.createFlowPanel().put(mailWarnBox).put(LayoutBuilder.createCellPanel(100, 200).put(EM.mail()).put(mailTextBox).build()).build();

    public WelcomeDialog() {
        super(EM.welcome());
        String photobookId = CookieAndUrlManagerClient.get().getSession().getPhotobookId();
        FlowPanelBuilder welcomeBuilder = LayoutBuilder.createFlowPanel();

        // welcome
        HTML welcomeHtml = new HTML(EM.welcomeText());
        welcomeHtml.getElement().getStyle().setProperty("textAlign", "center");
        welcomeBuilder.put(LayoutBuilder.createBox(welcomeHtml, Mode.SUCCESS, 300, 10));

        // email
        sendMailCheckBox.addClickHandler(ac);
        sendMailCheckBox.getElement().getStyle().setMargin(10, Unit.PX);
        sendMailCheckBox.getElement().getStyle().setDisplay(Display.BLOCK);
        onSendMailClicked(false);
        welcomeBuilder.put(sendMailCheckBox).put(mailPanel);

        // button
        welcomeBuilder.put(LayoutBuilder.createCellPanel(100).put(okButton).setMargins(0, 10).setAlignment(Alignment.RIGHT).build());
        add(welcomeBuilder.build(), 10);
    }

    private void onSendMailClicked(boolean sendMail) {
        if (!sendMail) {
            mailTextBox.setValue(null);
            mailWarnBox.setVisible(false);
        }
        mailPanel.setVisible(sendMail);
    }

    public void showDialog() {
        showCenter();
    }

    @Override
    public void onShowAgain() {
        // dont show dialog again
        hide();
    }

    private class ActionContainer implements ButtonListener, ClickHandler {

        @Override
        public void onClick(Button button, boolean pushed) {
            if (sendMailCheckBox.getValue()) {
                String mail = mailTextBox.getText() != null ? mailTextBox.getText().trim() : "";
                if (mail.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
                    String body = EM.mailBody(CookieAndUrlManagerClient.get().getSession().getPhotobookId());
                    String subject = EM.mailSubject();
                    MailService.sendMail(subject, body, mail, new CommonAsyncCallback<Void>() {

                        @Override
                        public void onFailure(Throwable caught) {
                            mailWarnBox.setVisible(true);
                        }

                        @Override
                        public void onSuccess(Void result) {
                            new InfoDialog(LayoutBuilder.createBox(EM.mailOk(), Mode.SUCCESS, 200, 10));
                        }
                    });
                } else {
                    mailWarnBox.setVisible(true);
                }
            } else {
                hide();
            }
        }

        @Override
        public void onClick(ClickEvent event) {
            onSendMailClicked(((CheckBox) event.getSource()).getValue());
        }
    }
}