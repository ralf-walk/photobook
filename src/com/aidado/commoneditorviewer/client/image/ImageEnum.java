package com.aidado.commoneditorviewer.client.image;

import com.google.gwt.resources.client.ImageResource;

public enum ImageEnum {
    I1(ImageBundle.INSTANCE.i1()),
    I2(ImageBundle.INSTANCE.i2()),
    I3(ImageBundle.INSTANCE.i3()),
    I4(ImageBundle.INSTANCE.i4()),
    I5(ImageBundle.INSTANCE.i5());

    private final ImageResource imageResource;

    private ImageEnum(ImageResource imageResource) {
        this.imageResource = imageResource;
    }

    public ImageResource getImageResource() {
        return imageResource;
    }
}
