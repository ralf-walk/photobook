package com.aidado.common.client.contentframe;

import com.aidado.common.client.CookieAndUrlManagerClient;
import com.aidado.common.client.InfoDialog;
import com.aidado.common.client.InfoDialog.InfoDialogListener;
import com.aidado.common.client.icon.Icons;
import com.aidado.common.client.locale.MessageBundle;
import com.aidado.common.client.widget.ContextAwareAnchor;
import com.aidado.common.client.widget.LayoutBuilder;
import com.aidado.common.client.widget.LayoutBuilder.Mode;
import com.aidado.common.shared.AidadoPage;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ContentFrame extends Composite implements ValueChangeHandler<String> {

    private static final MessageBundle M = MessageBundle.INSTANCE;

    @UiField(provided = true)
    final Widget content;

    @UiField
    AbsolutePanel root;

    @UiField
    Element header;

    @UiField
    Element footer;

/*
    @UiField(provided = true)
    ContextAwareAnchor impressAnchor = new ContextAwareAnchor(M.navigationImpress(), AidadoPage.IMPRESS);

    @UiField(provided = true)
    ContextAwareAnchor termsAnchor = new ContextAwareAnchor(M.navigationTerms(), AidadoPage.AGB);
*/

    @UiField(provided = true)
    ContextAwareAnchor startAnchor = new ContextAwareAnchor(M.navigationStart(), AidadoPage.START);

    @UiField(provided = true)
    ContextAwareAnchor featuresAnchor = new ContextAwareAnchor(M.navigationFeatures(), AidadoPage.FEATURES);

    /*@UiField(provided = true)
    ContextAwareAnchor editorAnchor = new EditorAnchor();
*/
    @UiField(provided = true)
    ContextAwareAnchor logoutAnchor = new LogoutAnchor();

    private static ContentFrameUiBinder uiBinder = GWT.create(ContentFrameUiBinder.class);

    interface ContentFrameUiBinder extends UiBinder<Widget, ContentFrame> {
    }

    public Widget getContent() {
        return content;
    }

    public AbsolutePanel getRoot() {
        return root;
    }

    public ContentFrame(Widget content, boolean secureNavigation) {
        this.content = content;
        initWidget(uiBinder.createAndBindUi(this));

        header.getStyle().setBackgroundImage("url(" + Icons.INSTANCE.contentHead().getSafeUri().asString() + ")");
        header.getStyle().setProperty("backgroundPosition", "20px 0px");
        header.getStyle().setProperty("backgroundRepeat", "no-repeat");

        footer.getStyle().setBackgroundImage("url(" + Icons.INSTANCE.contentFoot().getSafeUri().asString() + ")");
        footer.getStyle().setProperty("backgroundPosition", "700px 0px");
        footer.getStyle().setProperty("backgroundRepeat", "no-repeat");

        CookieAndUrlManagerClient manager = CookieAndUrlManagerClient.get();
        AidadoPage page = manager.getCurrentPage();
        if (page == AidadoPage.START) {
            startAnchor.setStyleName("active", true);
        } else if (page == AidadoPage.FEATURES) {
            featuresAnchor.setStyleName("active", true);
        } else if (page == AidadoPage.EDITOR) {
            // editorAnchor.setStyleName("active", true);
        }
        logoutAnchor.setVisible(manager.getSession().getSessionId() != null);

        if (secureNavigation) {
/*            impressAnchor.setNavigateWarning(MessageBundle.INSTANCE.navigateOut());
            termsAnchor.setNavigateWarning(MessageBundle.INSTANCE.navigateOut());*/
            startAnchor.setNavigateWarning(MessageBundle.INSTANCE.navigateOut());
            featuresAnchor.setNavigateWarning(MessageBundle.INSTANCE.navigateOut());
            logoutAnchor.setNavigateWarning(MessageBundle.INSTANCE.navigateOut());
            History.addValueChangeHandler(this);
            History.newItem("sec", false);
        }
    }

    @Override
    public void onValueChange(ValueChangeEvent<String> event) {
        new InfoDialog(LayoutBuilder.createFlowPanel().put(MessageBundle.INSTANCE.navigateOut()).setStyleProperty("maxWidth", "300px").setMargins(0, 0, 0, 5).setStyleMode(Mode.SUCCESS).build(), true,
                new InfoDialogListener() {

                    @Override
                    public void onOkClicked() {
                        History.back();
                    }

                    @Override
                    public void onCancelClicked() {
                        History.newItem("sec", false);
                    }
                });
    }

    private final class EditorAnchor extends ContextAwareAnchor {

        public EditorAnchor() {
            super(M.navigationEditor(), AidadoPage.EDITOR);
        }

        @Override
        public void onClick(ClickEvent event) {
            CookieAndUrlManagerClient manager = CookieAndUrlManagerClient.get();
            if (manager.getSession().getPhotobookId() != null) {
                super.onClick(event);
            } else {
                event.preventDefault();
                event.stopPropagation();
                new LoginDialog();
            }
        }
    }

    private final class LogoutAnchor extends ContextAwareAnchor {

        public LogoutAnchor() {
            super(M.navigationLogout(), AidadoPage.START);
        }

        @Override
        protected boolean isClickable() {
            return true;
        }

        @Override
        public void onNavigation() {
            CookieAndUrlManagerClient manager = CookieAndUrlManagerClient.get();
            manager.setSession(null);
            super.onNavigation();
        }
    }
}