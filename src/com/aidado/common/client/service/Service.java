package com.aidado.common.client.service;

import java.util.List;

import com.aidado.common.shared.exception.BaseException;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

public interface Service {

	@RemoteServiceRelativePath("../service/MailService")
	public interface MailService extends Service, RemoteService {

		void sendMail(String subject, String body, String to) throws BaseException;
	}

	public interface MailServiceAsync {

		void sendMail(String subject, String body, String to, AsyncCallback<Void> callback);
	}

	@RemoteServiceRelativePath("../service/ImageBlobInfoService")
	public interface ImageBlobInfoService extends Service, RemoteService {

		void syncImageBlobs(List<String> newBlobKeys) throws BaseException;

		String getImageBlobInfos() throws BaseException;

		List<String> getUploadUrls(int amount) throws BaseException;
	}

	public interface ImageBlobInfoServiceAsync {

		void getImageBlobInfos(AsyncCallback<String> callback);

		void syncImageBlobs(List<String> newBlobKeys, AsyncCallback<Void> callback);

		void getUploadUrls(int amount, AsyncCallback<List<String>> callback);
	}

	@RemoteServiceRelativePath("../service/PhotobookService")
	public interface PhotobookService extends Service, RemoteService {

		void createAndInitPhotobook(String password) throws BaseException;

		void initPhotobook(String password, String photobookId) throws BaseException;

		void savePhotobookContent(String content) throws BaseException;

		String getPasswordForEdit() throws BaseException;

		void savePhotobookPasswordForEdit(String passwordForEdit) throws BaseException;

		String getPasswordForView() throws BaseException;

		void savePhotobookPasswordForView(String passwordForView) throws BaseException;

		String getPhotobookForEdit() throws BaseException;

		String getPhotobookForView(String photobookId, String password) throws BaseException;

		void deletePhotobook() throws BaseException;
	}

	public interface PhotobookServiceAsync {

		void getPhotobookForView(String photobookId, String password, AsyncCallback<String> callback);

		void getPhotobookForEdit(AsyncCallback<String> callback);

		void deletePhotobook(AsyncCallback<Void> callback);

		void createAndInitPhotobook(String password, AsyncCallback<Void> callback);

		void initPhotobook(String password, String photobookId, AsyncCallback<Void> callback);

		void savePhotobookContent(String content, AsyncCallback<Void> callback);

		void getPasswordForEdit(AsyncCallback<String> callback);

		void savePhotobookPasswordForEdit(String passwordForEdit, AsyncCallback<Void> callback);

		void getPasswordForView(AsyncCallback<String> callback);

		void savePhotobookPasswordForView(String passwordForView, AsyncCallback<Void> callback);
	}
}