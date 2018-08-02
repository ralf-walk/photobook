package com.aidado.viewer.client;

import com.aidado.common.client.CookieAndUrlManagerClient;
import com.aidado.common.client.locale.MessageBundle;
import com.aidado.common.client.widget.ContextAwareAnchor;
import com.aidado.common.client.widget.LayoutBuilder;
import com.aidado.common.client.widget.LayoutBuilder.Mode;
import com.aidado.common.shared.AidadoPage;
import com.aidado.common.shared.CookieAndUrlManager.Session;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineHTML;

public class AnchorBox extends Composite {

	private static final MessageBundle M = MessageBundle.INSTANCE;

	public AnchorBox() {
		ContextAwareAnchor editAnchor = new EditAnchor();
		ContextAwareAnchor startAnchor = new ContextAwareAnchor(M.navigationStart(), AidadoPage.START);
		ContextAwareAnchor impressAnchor = new ContextAwareAnchor(M.navigationImpress(), AidadoPage.IMPRESS);
		// FlowPanel anchorPanel = LayoutBuilder.createFlowPanel().put(editAnchor).put(new InlineHTML(" &ndash; ")).put(startAnchor).put(new InlineHTML(" &ndash; ")).put(impressAnchor).build();
		FlowPanel anchorPanel = LayoutBuilder.createFlowPanel().put(editAnchor).put(new InlineHTML(" &ndash; ")).put(startAnchor).build();
		anchorPanel.getElement().getStyle().setMargin(5, Unit.PX);
		anchorPanel.getElement().getStyle().setProperty("textAlign", "right");
		initWidget(LayoutBuilder.createBox(anchorPanel, Mode.NORMAL, 0, 0));
	}

	private static class EditAnchor extends ContextAwareAnchor {

		public EditAnchor() {
			super(M.navigationEditor(), AidadoPage.EDITOR);
		}

		@Override
		public void onClick(ClickEvent event) {
			CookieAndUrlManagerClient manager = CookieAndUrlManagerClient.get();
			if (manager.getSession().getPhotobookId() == null) {
				String photobookId = manager.getPhotobookIdUrl();
				manager.setSession(new Session(photobookId, null));
			}
			super.onClick(event);
		}
	}
}