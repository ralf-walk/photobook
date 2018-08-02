package com.aidado.common.client.widget;

import com.aidado.common.client.CookieAndUrlManagerClient;
import com.aidado.common.shared.AidadoPage;

public abstract class PhotobookLoadDialog extends PasswordDialog {

	public interface PhotobookLoadListener {
		void onPhotobookLoad(String content);
	}

	protected final PhotobookLoadListener listener;

	public PhotobookLoadDialog(String caption, PhotobookLoadListener listener) {
		super(caption);
		this.listener = listener;
	}

	@Override
	protected void onCancelClicked() {
		CookieAndUrlManagerClient manager = CookieAndUrlManagerClient.get();
		manager.setSession(null);
		manager.redirectPage(AidadoPage.START);
	}
}
