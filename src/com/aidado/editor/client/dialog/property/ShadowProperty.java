package com.aidado.editor.client.dialog.property;

import com.aidado.common.client.widget.Button;
import com.aidado.common.client.widget.LayoutBuilder;
import com.aidado.common.client.widget.SliderPanel;
import com.aidado.common.client.widget.Button.ButtonListener;
import com.aidado.common.client.widget.SliderPanel.SliderPanelListener;
import com.aidado.commoneditorviewer.client.model.HasImageBorder;
import com.aidado.commoneditorviewer.client.model.HasShadow;
import com.aidado.commoneditorviewer.client.model.Model;
import com.aidado.commoneditorviewer.client.model.Panel;
import com.aidado.editor.client.dialog.ColorDialog;
import com.aidado.editor.client.dialog.HasColor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CheckBox;

public class ShadowProperty extends CompositeProperty<HasShadow> {

	private final ActionContainer ac = new ActionContainer();

	private final SliderPanel sizeSlider = new SliderPanel(ac, 0, 9, 150);
	private final SliderPanel offsetSlider = new SliderPanel(ac, -3, 3, 150);
	private final Button colorButton = Button.createChooseColorButton(ac);
	private final CheckBox insetCheckBox = new CheckBox();

	public ShadowProperty() {
		insetCheckBox.addClickHandler(ac);
		initWidget(LayoutBuilder.createCellPanel(100, 150).put(EM.size()).put(sizeSlider).put(EM.offset()).put(offsetSlider).put(EM.color()).put(colorButton).put(EM.inset()).put(insetCheckBox)
		    .setRowSpacing(5).setMargins(5, 0, 20, 0).build(), EM.shadow());
	}

	@Override
	protected Model getShowForModel(Panel panel) {
		if (panel instanceof HasShadow) {
			if (!(panel instanceof HasImageBorder) || panel instanceof HasImageBorder && ((HasImageBorder) panel).getImageBorderType() == null) {
				return panel;
			}
		}
		return null;
	}

	@Override
	public void showFor(HasShadow shadow) {
		int shadowSize = shadow.getShadowSize();
		sizeSlider.setValue(shadowSize / 2);
		int shadowOffset = shadow.getShadowOffset() / 2;
		offsetSlider.setValue(shadowOffset);
		insetCheckBox.setValue(shadow.isShadowInset(), false);
	}

	private class ActionContainer implements ButtonListener, SliderPanelListener, ClickHandler {

		@Override
		public void onSlide(SliderPanel source, int value) {
			if (source == sizeSlider) {
				getModel().setShadowSize(value * 2);
			} else {
				getModel().setShadowOffset(value * 2);
			}
		}

		@Override
		public void onClick(com.aidado.common.client.widget.Button button, boolean pushed) {
			new ColorDialog(new HasColor() {

				@Override
				public void setColor(String color) {
					getModel().setShadowColor(color);
				}
			});
		}

		@Override
		public void onClick(ClickEvent event) {
			getModel().setShadowInset(insetCheckBox.getValue());
		}
	}
}