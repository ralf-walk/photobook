package com.aidado.common.client.service;

import java.util.List;

import com.aidado.common.client.CommonAsyncCallback;
import com.aidado.common.client.service.Service.ImageBlobInfoServiceAsync;
import com.google.gwt.core.client.GWT;


public class ImageBlobInfoService {
	private final static ImageBlobInfoServiceAsync SERVICE = GWT.create(Service.ImageBlobInfoService.class);

	public static void getImageBlobInfos(CommonAsyncCallback<String> callback) {
		SERVICE.getImageBlobInfos(callback);
	}

	public static void syncImageBlobs(List<String> newBlobKeys, CommonAsyncCallback<Void> callback) {
		SERVICE.syncImageBlobs(newBlobKeys, callback);
	}

	public static void getUploadUrls(int amount, CommonAsyncCallback<List<String>> callback) {
		SERVICE.getUploadUrls(amount, callback);
	}
}
