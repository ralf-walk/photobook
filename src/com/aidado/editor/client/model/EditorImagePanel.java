package com.aidado.editor.client.model;

import java.util.ArrayList;
import java.util.List;

import com.aidado.commoneditorviewer.client.model.BaseImagePanel;
import com.aidado.editor.client.model.dnd.CarrierPanel.CarrierAwarePanel;
import com.google.gwt.user.client.ui.Widget;


public class EditorImagePanel extends BaseImagePanel implements CarrierAwarePanel {

  private static final List<EditorImagePanel> IMAGE_LIST = new ArrayList<EditorImagePanel>();

  public static List<EditorImagePanel> getAllImages() {
    return IMAGE_LIST;
  }

  public EditorImagePanel() {
    getElement().getStyle().setBackgroundColor("#CCCCCC");
  }

  @Override
  protected void onLoad() {
    super.onLoad();
    IMAGE_LIST.add(this);
  }

  @Override
  protected void onUnload() {
    super.onUnload();
    IMAGE_LIST.remove(this);
  }

  @Override
  public boolean isDropPossible(Widget w, boolean dragging) {
    return w instanceof EditorPagePanel;
  }

  @Override
  public void onDrop(Widget w) {
  }

  @Override
  public void onClick() {
  }
}