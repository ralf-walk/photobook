package com.aidado.editor.client;

import com.aidado.common.client.CookieAndUrlManagerClient;
import com.aidado.common.client.InfoDialog;
import com.aidado.common.client.widget.Button;
import com.aidado.common.client.widget.Button.ButtonListener;
import com.aidado.common.client.widget.LayoutBuilder;
import com.aidado.common.client.widget.LayoutBuilder.Mode;
import com.aidado.common.shared.AidadoPage;
import com.aidado.commoneditorviewer.client.PanelFactoryManager;
import com.aidado.commoneditorviewer.client.PanelFactoryManager.PanelFactory;
import com.aidado.commoneditorviewer.client.model.BasePagePanel;
import com.aidado.commoneditorviewer.client.model.Panel;
import com.aidado.editor.client.dialog.AdministrationDialog;
import com.aidado.editor.client.icon.Icons;
import com.aidado.editor.client.locale.MessageBundle;
import com.aidado.editor.client.model.EditorImagePanel;
import com.aidado.editor.client.model.dnd.CarrierPanel.CarrierAwarePanel;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;

public class PhotobookControls extends AbsolutePanel {

    private static final Icons EI = Icons.INSTANCE;
    private static final MessageBundle EM = MessageBundle.INSTANCE;

    private final ActionContainer ac = new ActionContainer();

    private final Button administrationButton = Button.createPushButton(ac, EM.infoAdministration(), 40, 40, EI.menuAdministration());
    private final Button saveButton = Button.createPushButton(ac, EM.save(), 40, 40, EI.menuSave());
    private final Button previewButton = Button.createPushButton(ac, EM.savePreview(), 40, 40, EI.menuSaveAndPreview());

    private final Image textBoxImage;
    private final Image imageBoxImage;


    public PhotobookControls() {
        Style style = getElement().getStyle();
        style.setProperty("marginTop", "10px");
        style.setProperty("borderTopLeftRadius", "10px");
        style.setProperty("borderTopRightRadius", "10px");
        style.setBackgroundColor("#222");
        style.setPadding(5, Unit.PX);

        textBoxImage = new Image(EI.panelText());
        textBoxImage.setTitle(MessageBundle.INSTANCE.textBox());
        textBoxImage.addMouseDownHandler(ac);
        add(textBoxImage);

        imageBoxImage = new Image(EI.panelImage());
        imageBoxImage.setTitle(MessageBundle.INSTANCE.imageBox());
        imageBoxImage.addMouseDownHandler(ac);
        add(imageBoxImage);

        // FlowPanel menuPanel = LayoutBuilder.createCellPanel(50, 50, 50, 50, 50, 50, 50).put(saveButton).put(previewButton).put(administrationButton).putEmpty().putEmpty().put(textBoxImage).put(imageBoxImage).build();
        FlowPanel menuPanel = LayoutBuilder.createCellPanel(50, 50, 50, 50, 50, 50, 50).put(previewButton).putEmpty().putEmpty().put(textBoxImage).put(imageBoxImage).build();
        add(menuPanel);
    }

    private class ActionContainer implements MouseDownHandler, ButtonListener {

        @Override
        public void onMouseDown(MouseDownEvent event) {
            event.stopPropagation();
            event.preventDefault();
            Panel panel = null;
            Image image = (Image) event.getSource();
            PanelFactory factory = PanelFactoryManager.getFactory();

            if (image == textBoxImage) {
                panel = factory.createTextPanel();
            } else if (image == imageBoxImage) {
                panel = factory.createImagePanel();
                ((EditorImagePanel) panel).setBackgroundColor("#555555");
            }
            panel.setPixelSize(40, 40);
            BasePagePanel pagePanel = Accessor.getPhotobook().getCurrentPage();
            pagePanel.addChildPanel(panel);
            panel.setLeft(DOM.getAbsoluteLeft(image.getElement()) - DOM.getAbsoluteLeft(pagePanel.getElement()));
            panel.setTop(DOM.getAbsoluteTop(image.getElement()) - DOM.getAbsoluteTop(pagePanel.getElement()));
            Accessor.getPhotobook().getCarrierLayer().initDragCarrier((CarrierAwarePanel) panel, event.getNativeEvent());
        }

        @Override
        public void onClick(Button button, boolean pushed) {
            if (button == saveButton) {
                String photobookJson = Accessor.getPhotobook().getPhotobookJson();
                CookieAndUrlManagerClient.get().setPhotobook(photobookJson);
/*				PhotobookService.savePhotobookContent(photobookJson, new CommonAsyncCallback<Void>() {

					@Override
					public void onSuccess(Void result) {*/
                new InfoDialog(LayoutBuilder.createBox(EM.saveOkText(), Mode.SUCCESS, 400, 10));
/*					}
				});*/
            } else if (button == previewButton) {
                String photobookJson = Accessor.getPhotobook().getPhotobookJson();
/*				PhotobookService.savePhotobookContent(photobookJson, new CommonAsyncCallback<Void>() {

					@Override
					public void onSuccess(Void result) {
						new InfoDialog(LayoutBuilder.createBox(EM.saveOkText(), Mode.SUCCESS, 400, 10), false, new InfoDialogListener() {

							@Override
							public void onOkClicked() {
								String photobookId = CookieAndUrlManagerClient.get().getSession().getPhotobookId();*/
                CookieAndUrlManagerClient.get().setPhotobook(photobookJson);
                CookieAndUrlManagerClient.get().redirectPage(AidadoPage.VIEWER);
/*							}

							@Override
							public void onCancelClicked() {
							}
						});
					}
				});*/
            } else if (button == administrationButton) {
                new AdministrationDialog();
            }
        }

    }
}