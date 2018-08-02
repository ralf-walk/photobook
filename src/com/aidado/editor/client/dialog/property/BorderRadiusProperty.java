package com.aidado.editor.client.dialog.property;

import com.aidado.common.client.widget.LayoutBuilder;
import com.aidado.common.client.widget.SliderPanel;
import com.aidado.common.client.widget.SliderPanel.SliderPanelListener;
import com.aidado.commoneditorviewer.client.model.HasBorderRadius;
import com.aidado.commoneditorviewer.client.model.HasImageBorder;
import com.aidado.commoneditorviewer.client.model.Model;
import com.aidado.commoneditorviewer.client.model.Panel;

public class BorderRadiusProperty extends CompositeProperty<HasBorderRadius> {

	private final ActionContainer ac = new ActionContainer();
	private final SliderPanel sizeSlider = new SliderPanel(ac, 0, 9, 150);

	public BorderRadiusProperty() {
		initWidget(LayoutBuilder.createCellPanel(100, 150).put(EM.size()).put(sizeSlider).setRowSpacing(5).setMargins(5, 0, 20, 0).build(), EM.roundedCorner());
	}

	@Override
	protected Model getShowForModel(Panel panel) {
		if (panel instanceof HasBorderRadius) {
			if (!(panel instanceof HasImageBorder) || panel instanceof HasImageBorder && ((HasImageBorder) panel).getImageBorderType() == null) {
				return panel;
			}
		}
		return null;
	}

	@Override
	public void showFor(HasBorderRadius hasBorderRadius) {
		sizeSlider.setValue(hasBorderRadius.getBorderRadius() / 3);
	}

	private class ActionContainer implements SliderPanelListener {

		@Override
		public void onSlide(SliderPanel source, int value) {
			getModel().setBorderRadius(value * 3);
		}
	}
}