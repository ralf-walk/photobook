package com.aidado.editor.client.dialog;

import com.aidado.common.client.CommonAsyncCallback;
import com.aidado.common.client.CookieAndUrlManagerClient;
import com.aidado.common.client.InfoDialog;
import com.aidado.common.client.InfoDialog.InfoDialogListener;
import com.aidado.common.client.service.PhotobookService;
import com.aidado.common.client.widget.Button;
import com.aidado.common.client.widget.CommonDialog;
import com.aidado.common.client.widget.LayoutBuilder;
import com.aidado.common.client.widget.TitleComposite;
import com.aidado.common.client.widget.Button.ButtonListener;
import com.aidado.common.client.widget.LayoutBuilder.FlowPanelBuilder;
import com.aidado.common.client.widget.LayoutBuilder.Mode;
import com.aidado.common.client.widget.LayoutBuilder.CellPanelBuilder.Alignment;
import com.aidado.common.shared.AidadoPage;
import com.aidado.editor.client.Accessor;
import com.aidado.editor.client.icon.Icons;
import com.aidado.editor.client.locale.MessageBundle;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.HTML;

public class AdministrationDialog extends CommonDialog {

	private static final Icons EI = Icons.INSTANCE;
	private static final MessageBundle EM = MessageBundle.INSTANCE;

	private final ActionContainer ac = new ActionContainer();
	private final HTML titleBox = new HTML();
	private final Button titleButton = Button.createPushButton(ac, EM.changeTitle(), 180, 22, EI.title(), EM.changeTitle());
	private final Button passwordforViewButton = Button.createPushButton(ac, EM.showPassword(), 180, 22, EI.password(), EM.showPassword());
	private final Button passwordforEditButton = Button.createPushButton(ac, EM.showPassword(), 180, 22, EI.password(), EM.showPassword());
	private final Button deletePhotobookButton = Button.createPushButton(ac, EM.deletePhotobook(), 180, 22, EI.photobookDelete(), EM.deletePhotobook());
	private final Button okButton = Button.createOkButton(ac);

	public AdministrationDialog() {
		super(EM.infoAdministration());

		FlowPanelBuilder content = LayoutBuilder.createFlowPanel();

		// photobook info
		String photobookId = CookieAndUrlManagerClient.get().getSession().getPhotobookId();
		content.put(new TitleComposite(LayoutBuilder.createCellPanel(50, 200).put(EM.url()).put("<b>http://aidado.com/" + photobookId + "</b>").setRowSpacing(5).setMargins(10, 0, 0, 15).build(), EM
		    .photobookInfo()));

		// title
		content.put(new TitleComposite(LayoutBuilder.createCellPanel(50, 200).put(EM.title()).put(titleBox).putEmpty().put(titleButton).setRowSpacing(5).setMargins(10, 0, 0, 15).build(), EM
		    .photobookTitle()));

		// passwords
		content.put(new TitleComposite(LayoutBuilder.createCellPanel(50, 200).putEmpty().put(passwordforEditButton).setMargins(10, 0, 0, 15).build(), EM.passwordForEdit()));
		content.put(new TitleComposite(LayoutBuilder.createCellPanel(50, 200).putEmpty().put(passwordforViewButton).setMargins(10, 0, 0, 15).build(), EM.passwordForView()));

		// delete photobook
		content.put(new TitleComposite(LayoutBuilder.createCellPanel(50, 200).putEmpty().put(deletePhotobookButton).setMargins(10, 0, 0, 15).build(), EM.deletePhotobook()));

		// ok button
		content.put(LayoutBuilder.createCellPanel(110).put(okButton).setAlignment(Alignment.RIGHT).setMargins(10, 15, 10, 10).build());
		add(content.build());
		showCenter();
	}

	@Override
	public void onShow() {
		String title = Accessor.getPhotobook().getRootPanel().getTitle();
		title = (title != null && title.trim().length() > 0) ? title : EM.noTitle();
		titleBox.setHTML(new SafeHtmlBuilder().appendHtmlConstant("<b>").appendEscaped(title).appendHtmlConstant("</b>").toSafeHtml());
	}

	private class ActionContainer implements ButtonListener {

		@Override
		public void onClick(Button button, boolean pushed) {
			if (button == titleButton) {
				TitleDialog.showDialog();
			} else if (button == passwordforEditButton) {
				PhotobookService.getPasswordForEdit(new CommonAsyncCallback<String>() {

					@Override
					public void onSuccess(String password) {
						new PasswordChangeEditDialog(password);
					}
				});
			} else if (button == passwordforViewButton) {
				PhotobookService.getPasswordForView(new CommonAsyncCallback<String>() {

					@Override
					public void onSuccess(String password) {
						new PasswordChangeViewDialog(password);
					}
				});
			} else if (button == deletePhotobookButton) {
				new InfoDialog(LayoutBuilder.createBox(EM.deletePhotobookWarn(), Mode.WARNING, 300, 10), true, new InfoDialogListener() {

					@Override
					public void onOkClicked() {
						PhotobookService.deletePhotobook(new CommonAsyncCallback<Void>() {

							@Override
							public void onSuccess(Void result) {
								CookieAndUrlManagerClient manager = CookieAndUrlManagerClient.get();
								manager.setSession(null);
								manager.redirectPage(AidadoPage.START);
							}
						});
					}
				});
			} else {
				hide();
			}
		}
	}
}