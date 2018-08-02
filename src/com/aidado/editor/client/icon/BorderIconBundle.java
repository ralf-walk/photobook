package com.aidado.editor.client.icon;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface BorderIconBundle extends ClientBundle {
  
  public static final BorderIconBundle INSTANCE = (BorderIconBundle) GWT.create(BorderIconBundle.class);
  
  @Source("border_no.png")
  ImageResource borderNo();

  @Source("border_dashed.png")
  ImageResource borderDashed();

  @Source("border_dotted.png")
  ImageResource borderDotted();

  @Source("border_double.png")
  ImageResource borderDouble();

  @Source("border_groove.png")
  ImageResource borderGroove();

  @Source("border_inset.png")
  ImageResource borderInset();

  @Source("border_outset.png")
  ImageResource borderOutset();

  @Source("border_ridge.png")
  ImageResource borderRidge();

  @Source("border_solid.png")
  ImageResource borderSolid();

  @Source("border_metal.png")
  ImageResource borderMetal();

  @Source("border_hearts.png")
  ImageResource borderHearts();
}
