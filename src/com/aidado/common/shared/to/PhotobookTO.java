package com.aidado.common.shared.to;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PhotobookTO implements Serializable {

	private String photobookId;

	private String sessionId;

	private boolean password;

	private String passwordEdit;
	
	private String content;

	@SuppressWarnings("unused")
	private PhotobookTO() {
		this(null);
	}

	public PhotobookTO(String photobookId) {
		this.photobookId = photobookId;
	}

	public boolean isPassword() {
		return password;
	}

	public void setPassword(boolean password) {
		this.password = password;
	}

	public String getPhotobookId() {
		return photobookId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getPasswordEdit() {
  	return passwordEdit;
  }

	public void setPasswordEdit(String passwordEdit) {
  	this.passwordEdit = passwordEdit;
  }

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}