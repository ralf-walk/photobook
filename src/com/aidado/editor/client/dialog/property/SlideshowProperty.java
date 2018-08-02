package com.aidado.editor.client.dialog.property;

import com.aidado.common.client.widget.LayoutBuilder;
import com.aidado.common.client.widget.TextBoxFactory;
import com.aidado.commoneditorviewer.client.model.BaseImagePanel;
import com.aidado.commoneditorviewer.client.model.Model;
import com.aidado.commoneditorviewer.client.model.Panel;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextBox;


public class SlideshowProperty extends CompositeProperty<BaseImagePanel> {

  private final ActionContainer ac = new ActionContainer();

  private final TextBox title = TextBoxFactory.createTextBox(150, 30, null, null);
  private final CheckBox slideshowEnabled = new CheckBox();

  public SlideshowProperty() {
    slideshowEnabled.addClickHandler(ac);
    title.addKeyUpHandler(ac);
    FlowPanel content = LayoutBuilder.createCellPanel(100, 150).put(EM.title()).put(title).put(EM.slideshow()).put(slideshowEnabled).setRowSpacing(5).setMargins(5, 0).build();
    initWidget(content, EM.slideshow());
  }

  @Override
  public void showFor(BaseImagePanel panel) {
    slideshowEnabled.setValue(panel.isSlideshowEnabled());
    title.setText(panel.getTitle());
  }

  private class ActionContainer implements ClickHandler, KeyUpHandler {

    @Override
    public void onClick(ClickEvent event) {
      getModel().setSlideshowEnabled(slideshowEnabled.getValue());
    }

    @Override
    public void onKeyUp(KeyUpEvent event) {
      String title = ((TextBox) event.getSource()).getText();
      getModel().setTitle((title == null || title.trim().length() == 0) ? null : title.trim());
    }
  }

  protected Model getShowForModel(Panel panel) {
    return panel instanceof BaseImagePanel ? panel : null;
  }
}