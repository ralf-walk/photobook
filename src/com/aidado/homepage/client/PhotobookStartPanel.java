package com.aidado.homepage.client;

import com.aidado.common.client.CookieAndUrlManagerClient;
import com.aidado.common.client.widget.Button;
import com.aidado.common.client.widget.LayoutBuilder;
import com.aidado.common.client.widget.TitleComposite;
import com.aidado.common.client.widget.Button.ButtonListener;
import com.aidado.common.client.widget.LayoutBuilder.Mode;
import com.aidado.common.shared.AidadoPage;
import com.aidado.homepage.client.dialog.CreatePhotobookDialog;
import com.aidado.homepage.client.locale.MessageBundle;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

public class PhotobookStartPanel extends Composite {

	private static final MessageBundle HM = MessageBundle.INSTANCE;

	private final ActionContainer ac = new ActionContainer();

	public PhotobookStartPanel() {
		Button createButton = Button.createPushButton(ac, null, 200, 44, com.aidado.common.client.icon.Icons.INSTANCE.add(), HM.createNewPhotobook());
		FlowPanel createPanel = LayoutBuilder.createCellPanel(200).put(createButton).setMargins(5, 10, 0, 15).build();
		FlowPanel content = LayoutBuilder.createFlowPanel().put(new TitleComposite(createPanel, HM.createNewPhotobook())).build();
		initWidget(LayoutBuilder.createBox(content, Mode.NORMAL, 300, 0));
	}

	private class ActionContainer implements ButtonListener {

		@Override
		public void onClick(Button button, boolean pushed) {
			// new CreatePhotobookDialog();
			CookieAndUrlManagerClient manager = CookieAndUrlManagerClient.get();
			manager.clearPhotobook();
			manager.redirectPage(AidadoPage.EDITOR);
		}
	}
}