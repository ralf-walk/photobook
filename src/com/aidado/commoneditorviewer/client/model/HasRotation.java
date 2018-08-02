package com.aidado.commoneditorviewer.client.model;

import com.aidado.commoneditorviewer.client.model.Model.Extension;

public interface HasRotation extends Extension {
	
	int getRotation();
	
	void setRotation(int rotation);
}