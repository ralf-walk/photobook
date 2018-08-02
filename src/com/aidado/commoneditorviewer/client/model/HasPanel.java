package com.aidado.commoneditorviewer.client.model;

import com.aidado.commoneditorviewer.client.model.Model.Extension;

public interface HasPanel extends Extension {

  int getLeft();

  void setLeft(int left);

  int getTop();

  void setTop(int top);

  int getWidth();

  void setWidth(int width);

  int getHeight();

  void setHeight(int height);
}
