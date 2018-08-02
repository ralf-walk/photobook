package com.aidado.common.client.locale;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

public interface MessageBundle extends Messages {
	public static final MessageBundle INSTANCE = GWT.create(MessageBundle.class);

	// Common

	String ok();

	String cancel();

	String name();

	String delete();

	String changeColor();

	String password();

	// Content Frame

	String navigationEditor();
	
	String navigationStart();

	String navigationFeatures();

	String navigationImpress();

	String navigationTerms();

	String navigationLogout();

	String navigateOut();

	// Photobook

	String page();

	String previousPage();

	String nextPage();
	
	// CommonAsyncCallback

	String unknownError();

	String sessionInvalidText();

	String noPhotobookText();
	
	// LoginDialog
	
	String login();
	
	String loginInfo();
	
	String loginAddressWarn();
	
	String loginAddressPasswordWarn();
	
	String address();
	
	// Management

	String newPassword();

	String confirmPassword();

	String confirmNewPassword();
	
	String passwordInvalid();
	
	String passwordError();
	
	String passwordConfirmationError();
}