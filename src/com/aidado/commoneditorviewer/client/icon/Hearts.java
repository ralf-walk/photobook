package com.aidado.commoneditorviewer.client.icon;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;


public interface Hearts extends ClientBundle, BorderBundle {
	
  public static final Hearts INSTANCE = (Hearts) GWT.create(Hearts.class);

  @Source("border_hearts_TL.png")
	public ImageResource topLeft();

  @Source("border_hearts_T.png")
	public ImageResource top();

  @Source("border_hearts_TR.png")
	public ImageResource topRight();

  @Source("border_hearts_LR.png")
	public ImageResource right();

  @Source("border_hearts_BR.png")
	public ImageResource bottomRight();

  @Source("border_hearts_B.png")
	public ImageResource bottom();

  @Source("border_hearts_BL.png")
	public ImageResource bottomLeft();

  @Source("border_hearts_LR.png")
	public ImageResource left();
}