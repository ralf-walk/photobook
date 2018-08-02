package com.aidado.editor.client.dialog.property;

import java.util.ArrayList;
import java.util.List;

import com.aidado.common.client.widget.Button;
import com.aidado.common.client.widget.DropDownPanel;
import com.aidado.common.client.widget.LayoutBuilder;
import com.aidado.common.client.widget.ListEntry;
import com.aidado.common.client.widget.SliderPanel;
import com.aidado.common.client.widget.Button.ButtonListener;
import com.aidado.common.client.widget.DropDownPanel.DropDownPanelListener;
import com.aidado.common.client.widget.SliderPanel.SliderPanelListener;
import com.aidado.commoneditorviewer.client.model.HasCssBorder;
import com.aidado.commoneditorviewer.client.model.HasImageBorder;
import com.aidado.commoneditorviewer.client.model.Model;
import com.aidado.commoneditorviewer.client.model.Panel;
import com.aidado.commoneditorviewer.client.model.HasCssBorder.CssBorderType;
import com.aidado.commoneditorviewer.client.model.HasImageBorder.ImageBorderType;
import com.aidado.editor.client.dialog.ColorDialog;
import com.aidado.editor.client.dialog.HasColor;
import com.aidado.editor.client.icon.BorderIcon;
import com.aidado.editor.client.icon.BorderIconBundle;
import com.google.gwt.resources.client.ImageResource;


public class BorderProperty {

  public static class BorderTypeProperty extends CompositeProperty<Panel> {

    private final ActionContainer ac = new ActionContainer();
    private final DropDownPanel<BorderDropDownEntry<?>> styleDropDown;

    public BorderTypeProperty() {
      List<BorderDropDownEntry<?>> dropDownList = new ArrayList<BorderDropDownEntry<?>>();
      dropDownList.add(new BorderDropDownEntry<Object>(BorderIconBundle.INSTANCE.borderNo(), EM.noBorder(), false, null));
      for (CssBorderType type : CssBorderType.values()) {
        dropDownList.add(new BorderDropDownEntry<CssBorderType>(BorderIcon.valueOf(type.name()).getImageResource(), type.name().toLowerCase(), true, type));
      }
      for (ImageBorderType type : ImageBorderType.values()) {
        dropDownList.add(new BorderDropDownEntry<ImageBorderType>(BorderIcon.valueOf(type.name()).getImageResource(), type.name().toLowerCase(), false, type));
      }
      styleDropDown = new DropDownPanel<BorderDropDownEntry<?>>(ac, dropDownList, 150, null);
      initWidget(LayoutBuilder.createCellPanel(100, 150).put(EM.style()).put(styleDropDown).setRowSpacing(5).setMargins(5, 0, 20, 0).build(), EM.borderType());
    }

    @Override
    public void showFor(Panel hasBorderPanel) {
      CssBorderType cssBorderType = ((HasCssBorder) hasBorderPanel).getCssBorderType();
      ImageBorderType imageBorderType = ((HasImageBorder) hasBorderPanel).getImageBorderType();
      if (cssBorderType != null) {
        styleDropDown.setSelectedIndex(cssBorderType.ordinal() + 1);
      } else if (imageBorderType != null) {
        styleDropDown.setSelectedIndex(imageBorderType.ordinal() + CssBorderType.values().length + 1);
      } else {
        styleDropDown.setSelectedIndex(0);
      }
    }

    @Override
    protected Model getShowForModel(Panel panel) {
      return (panel instanceof HasCssBorder && panel instanceof HasImageBorder) ? panel : null;
    }

    private class ActionContainer implements DropDownPanelListener<BorderDropDownEntry<?>> {

      @SuppressWarnings("unchecked")
      @Override
      public void onChange(DropDownPanel<BorderDropDownEntry<?>> source, BorderDropDownEntry<?> borderEntry) {
        HasImageBorder hasImageBorder = (HasImageBorder) getModel();
        HasCssBorder hasCssBorder = (HasCssBorder) getModel();
        hasCssBorder.setCssBorderType((borderEntry.getBorderType() == null || !borderEntry.isCssBorder()) ? null : ((BorderDropDownEntry<CssBorderType>) borderEntry).getBorderType());
        hasImageBorder.setImageBorderType((borderEntry.getBorderType() == null || borderEntry.isCssBorder()) ? null : ((BorderDropDownEntry<ImageBorderType>) borderEntry).getBorderType());
        panelUpdated();
      }
    }

    private class BorderDropDownEntry<T> implements ListEntry {

      private ImageResource previewImage;
      private String text;
      private boolean cssBorder;
      private T type;

      public BorderDropDownEntry(ImageResource previewImage, String text, boolean cssBorder, T type) {
        this.previewImage = previewImage;
        this.cssBorder = cssBorder;
        this.text = text;
        this.type = type;
      }

      @Override
      public ImageResource getImage() {
        return previewImage;
      }

      @Override
      public String getText() {
        return text;
      }

      public boolean isCssBorder() {
        return cssBorder;
      }

      public T getBorderType() {
        return type;
      }
    }
  }

  public static class BorderColorAndSizeProperty extends CompositeProperty<HasCssBorder> {

    private final ActionContainer ac = new ActionContainer();

    private final Button colorButton = Button.createChooseColorButton(ac);
    private final SliderPanel sizeSlider = new SliderPanel(ac, 1, 9, 150);

    public BorderColorAndSizeProperty() {
      initWidget(LayoutBuilder.createCellPanel(100, 150).put(EM.color()).put(colorButton).put(EM.size()).put(sizeSlider).setRowSpacing(5).setMargins(5, 0, 20, 0).build(), EM.borderColorAndSize());
    }

    @Override
    protected Model getShowForModel(Panel panel) {
      if (panel instanceof HasCssBorder) {
        HasCssBorder hasCssBorder = (HasCssBorder) panel;
        if (hasCssBorder.getCssBorderType() != null) {
          return panel;
        }
      }
      return null;
    }

    @Override
    public void showFor(HasCssBorder decoration) {
      sizeSlider.setValue(decoration.getBorderSize());
    }

    private class ActionContainer implements ButtonListener, SliderPanelListener {

      @Override
      public void onSlide(SliderPanel source, int value) {
        getModel().setBorderSize(value);
        panelUpdated();
      }

      @Override
      public void onClick(Button button, boolean pushed) {
        new ColorDialog(new HasColor() {

          @Override
          public void setColor(String color) {
            getModel().setBorderColor(color);
          }
        });
      }
    }
  }
}