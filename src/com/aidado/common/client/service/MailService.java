package com.aidado.common.client.service;

import com.aidado.common.client.CommonAsyncCallback;
import com.aidado.common.client.service.Service.MailServiceAsync;
import com.google.gwt.core.client.GWT;

public class MailService {

	private final static MailServiceAsync SERVICE = GWT.create(Service.MailService.class);

	public static void sendMail(String subject, String body, String to, CommonAsyncCallback<Void> callback) {
		SERVICE.sendMail(subject, body, to, callback);
	}
}