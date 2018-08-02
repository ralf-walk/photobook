package com.aidado.commoneditorviewer.client.model;

import com.aidado.commoneditorviewer.client.model.Model.Extension;

public interface HasShadow extends Extension {

	int getShadowSize();

	void setShadowSize(int shadowSize);

	boolean isShadowInset();

	void setShadowInset(boolean shadowInset);

	int getShadowOffset();

	void setShadowOffset(int shadowOffset);

	String getShadowColor();

	void setShadowColor(String shadowColor);
}
