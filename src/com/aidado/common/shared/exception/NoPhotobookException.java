package com.aidado.common.shared.exception;

@SuppressWarnings("serial")
public class NoPhotobookException extends BaseException {
	
	private String photobookName;
	
	public NoPhotobookException() {
	  this(null);
	}

	public NoPhotobookException(String photobookName) {
		this.photobookName = photobookName;
	}

	public String getPhotobookName() {
  	return photobookName;
  }
}
