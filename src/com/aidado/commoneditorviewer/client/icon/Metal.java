package com.aidado.commoneditorviewer.client.icon;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;


public interface Metal extends ClientBundle, BorderBundle {
	
  public static final Metal INSTANCE = (Metal) GWT.create(Metal.class);

  @Source("border_metal_TL.png")
	public ImageResource topLeft();

  @Source("border_metal_T.png")
	public ImageResource top();

  @Source("border_metal_TR.png")
	public ImageResource topRight();

  @Source("border_metal_R.png")
	public ImageResource right();

  @Source("border_metal_BR.png")
	public ImageResource bottomRight();

  @Source("border_metal_B.png")
	public ImageResource bottom();

  @Source("border_metal_BL.png")
	public ImageResource bottomLeft();

  @Source("border_metal_L.png")
	public ImageResource left();
}