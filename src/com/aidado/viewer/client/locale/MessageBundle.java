package com.aidado.viewer.client.locale;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

public interface MessageBundle extends Messages {
	public static final MessageBundle INSTANCE = GWT.create(MessageBundle.class);

	// Slideshow

	String previousImage();

	String nextImage();

	String hideSlideshow();

	String startSlideshow();

	String stopSlideshow();

	String seconds();

	// Dialog

	String noPages();

	String passwordRequired();

	String passwordRequiredInfo();
}