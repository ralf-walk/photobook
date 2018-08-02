package com.aidado.common.client.widget;

import com.aidado.common.client.CookieAndUrlManagerClient;
import com.aidado.common.client.InfoDialog;
import com.aidado.common.client.InfoDialog.InfoDialogListener;
import com.aidado.common.client.widget.LayoutBuilder.Mode;
import com.aidado.common.shared.AidadoPage;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;

public class ContextAwareAnchor extends Anchor implements ClickHandler {

	private final AidadoPage page;
	private String navigateWarning = null;

	public ContextAwareAnchor(String text, AidadoPage page) {
		super(text, page.getDisplayName());
		this.page = page;
		addClickHandler(this);
	}

	protected void onNavigation() {
		CookieAndUrlManagerClient.get().redirectPage(page);
	}
	
	protected boolean isClickable() {
		return page != CookieAndUrlManagerClient.get().getCurrentPage();
	}

	@Override
	public void onClick(ClickEvent event) {
		if (!isClickable()) {
			event.preventDefault();
			event.stopPropagation();
		} else {
			if (navigateWarning == null && !GWT.isProdMode()) {
				event.preventDefault();
				event.stopPropagation();
				onNavigation();
			} else if (navigateWarning != null) {
				event.preventDefault();
				event.stopPropagation();
				new InfoDialog(LayoutBuilder.createFlowPanel().put(navigateWarning).setStyleProperty("maxWidth", "300px").setStyleProperty("marginBottom", "5px").setStyleMode(Mode.SUCCESS).build(), true,
				    new InfoDialogListener() {

					    @Override
					    public void onOkClicked() {
					    	CookieAndUrlManagerClient.get().clearPhotobook();
						    onNavigation();
					    }

					    @Override
					    public void onCancelClicked() {
					    }
				    });
			}
		}
	}

	public void setNavigateWarning(String navigateWarning) {
		this.navigateWarning = navigateWarning;
	}
}