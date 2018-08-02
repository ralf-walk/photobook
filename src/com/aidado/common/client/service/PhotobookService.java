package com.aidado.common.client.service;

import com.aidado.common.client.CommonAsyncCallback;
import com.aidado.common.client.service.Service.PhotobookServiceAsync;
import com.google.gwt.core.client.GWT;

public class PhotobookService {

	private final static PhotobookServiceAsync SERVICE = GWT.create(Service.PhotobookService.class);

	public static void createAndInitPhotobook(String passwordForEdit, CommonAsyncCallback<Void> callback) {
		SERVICE.createAndInitPhotobook(passwordForEdit, callback);
	}

	public static void initPhotobook(String photobookId, String passwordForEdit, CommonAsyncCallback<Void> callback) {
		SERVICE.initPhotobook(photobookId, passwordForEdit, callback);
	}

	public static void getPhotobookForEdit(CommonAsyncCallback<String> callback) {
		SERVICE.getPhotobookForEdit(callback);
	}

	public static void getPhotobookForView(String photobookId, String passwordForView, CommonAsyncCallback<String> callback) {
		SERVICE.getPhotobookForView(photobookId, passwordForView, callback);
	}

	public static void savePhotobookContent(String content, CommonAsyncCallback<Void> callback) {
		SERVICE.savePhotobookContent(content, callback);
	}

	public static void  getPasswordForEdit(CommonAsyncCallback<String> callback) {		
		SERVICE.getPasswordForEdit(callback);
	}

	public static void savePhotobookPasswordForEdit(String passwordForEdit, CommonAsyncCallback<Void> callback) {
		SERVICE.savePhotobookPasswordForEdit(passwordForEdit, callback);
	}

	public static void  getPasswordForView(CommonAsyncCallback<String> callback) {		
		SERVICE.getPasswordForView(callback);
	}

	public static void savePhotobookPasswordForView(String passwordForView, CommonAsyncCallback<Void> callback) {
		SERVICE.savePhotobookPasswordForView(passwordForView, callback);
	}

	public static void deletePhotobook(CommonAsyncCallback<Void> callback) {
		SERVICE.deletePhotobook(callback);
	}
}