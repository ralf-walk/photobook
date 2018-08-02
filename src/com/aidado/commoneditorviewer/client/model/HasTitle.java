package com.aidado.commoneditorviewer.client.model;

import com.aidado.commoneditorviewer.client.model.Model.Extension;

public interface HasTitle extends Extension {

	String getTitle();

	void setTitle(String title);
}