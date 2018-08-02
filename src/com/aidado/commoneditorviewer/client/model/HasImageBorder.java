package com.aidado.commoneditorviewer.client.model;

import com.aidado.commoneditorviewer.client.model.Model.Extension;

public interface HasImageBorder extends Extension {

	public enum ImageBorderType {
		HEARTS, METAL;
	}
	
	ImageBorderType getImageBorderType();

	void setImageBorderType(ImageBorderType imageBorderType);
}