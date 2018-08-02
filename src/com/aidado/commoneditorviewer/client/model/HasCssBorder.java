package com.aidado.commoneditorviewer.client.model;

import com.aidado.commoneditorviewer.client.model.Model.Extension;

public interface HasCssBorder extends Extension {

	public enum CssBorderType {
		DOTTED, DASHED, SOLID, DOUBLE, GROOVE, RIDGE, INSET, OUTSET;
	}

	CssBorderType getCssBorderType();

	void setCssBorderType(CssBorderType cssBorderType);

	int getBorderSize();

	void setBorderSize(int borderSize);

	String getBorderColor();

	void setBorderColor(String borderColor);
}