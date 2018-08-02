package com.aidado.commoneditorviewer.client.model;

import com.aidado.commoneditorviewer.client.model.Model.Item;

public class ImageBlobInfo implements Item {

  private String key;
  private String fileName;
  private String url;
  private int size;

  public ImageBlobInfo(String key, String fileName, String url, int size) {
    this.key = key;
    this.fileName = fileName;
    this.url = url;
    this.size = size;
  }

  public String getKey() {
    return key;
  }

  public String getFileName() {
    return fileName;
  }

  public String getUrl() {
    return url;
  }

  public int getSize() {
    return size;
  }
}