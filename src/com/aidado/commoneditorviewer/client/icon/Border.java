package com.aidado.commoneditorviewer.client.icon;

import com.google.gwt.core.client.GWT;

public enum Border {
  HEARTS(12, 12) {

    @Override
    public BorderBundle getBundle() {
      return GWT.create(Hearts.class);
    }
  },
  METAL(12, 0) {

    @Override
    public BorderBundle getBundle() {
      return GWT.create(Metal.class);
    }
  };

  private int overlap;
  private int underlap;

  private Border(int overlap, int underlap) {
    this.overlap = overlap;
    this.underlap = underlap;
  }

  public static Border get(String bundleBase) {
    return Border.valueOf(bundleBase);
  }

  public BorderBundle getBundle() {
    return null;
  }

  public int getOverlap() {
    return overlap;
  }

  public int getUnderlap() {
    return underlap;
  }
}
