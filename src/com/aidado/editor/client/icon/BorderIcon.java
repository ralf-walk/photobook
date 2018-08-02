package com.aidado.editor.client.icon;

import com.google.gwt.resources.client.ImageResource;

public enum BorderIcon {
  DASHED {

    @Override
    public ImageResource getImageResource() {
      return BorderIconBundle.INSTANCE.borderDashed();
    }
  },
  DOTTED {

    @Override
    public ImageResource getImageResource() {
      return BorderIconBundle.INSTANCE.borderDotted();
    }
  },
  DOUBLE {

    @Override
    public ImageResource getImageResource() {
      return BorderIconBundle.INSTANCE.borderDouble();
    }
  },
  GROOVE {

    @Override
    public ImageResource getImageResource() {
      return BorderIconBundle.INSTANCE.borderGroove();
    }
  },
  INSET {

    @Override
    public ImageResource getImageResource() {
      return BorderIconBundle.INSTANCE.borderInset();
    }
  },
  OUTSET {

    @Override
    public ImageResource getImageResource() {
      return BorderIconBundle.INSTANCE.borderOutset();
    }
  },
  RIDGE {

    @Override
    public ImageResource getImageResource() {
      return BorderIconBundle.INSTANCE.borderRidge();
    }
  },
  SOLID {

    @Override
    public ImageResource getImageResource() {
      return BorderIconBundle.INSTANCE.borderSolid();
    }
  },
  METAL {

    @Override
    public ImageResource getImageResource() {
      return BorderIconBundle.INSTANCE.borderMetal();
    }
  },
  HEARTS {

    @Override
    public ImageResource getImageResource() {
      return BorderIconBundle.INSTANCE.borderHearts();
    }
  };

  public abstract ImageResource getImageResource();
}
