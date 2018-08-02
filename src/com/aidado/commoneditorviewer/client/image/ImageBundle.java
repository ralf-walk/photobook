package com.aidado.commoneditorviewer.client.image;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface ImageBundle extends ClientBundle {

    public static final ImageBundle INSTANCE = (ImageBundle) GWT.create(ImageBundle.class);

    @ClientBundle.Source("i1.jpg")
    public ImageResource i1();

    @ClientBundle.Source("i2.jpg")
    public ImageResource i2();

    @ClientBundle.Source("i3.jpg")
    public ImageResource i3();

    @ClientBundle.Source("i4.jpg")
    public ImageResource i4();

    @ClientBundle.Source("i5.jpg")
    public ImageResource i5();
}