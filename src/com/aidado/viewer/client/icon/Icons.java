package com.aidado.viewer.client.icon;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface Icons extends ClientBundle {
  public static final Icons INSTANCE = GWT.create(Icons.class);

  @Source("slideshow_stop.png")
  ImageResource slideshowStop();

  @Source("slideshow_hide.png")
  ImageResource slideshowHide();

  @Source("slideshow_next.png")
  ImageResource slideshowNext();

  @Source("slideshow_previous.png")
  ImageResource slideshowPrevious();

  @Source("slideshow_start.png")
  ImageResource slideshowStart();

  @Source("photobook_password_enabled.png")
  ImageResource photobookPasswordEnabled();
}