package com.aidado.editor.client.dialog.property;

import com.aidado.common.client.widget.LayoutBuilder;
import com.aidado.common.client.widget.SliderPanel;
import com.aidado.common.client.widget.SliderPanel.SliderPanelListener;
import com.aidado.commoneditorviewer.client.model.HasOpacity;
import com.aidado.commoneditorviewer.client.model.HasRotation;
import com.aidado.commoneditorviewer.client.model.Model;
import com.aidado.commoneditorviewer.client.model.Panel;
import com.aidado.commoneditorviewer.client.model.Model.Extension;

public class RotationAndOpacityProperty extends CompositeProperty<Extension> {

	private final ActionContainer ac = new ActionContainer();
	private final SliderPanel rotationSlider = new SliderPanel(ac, -5, 5, 150);
	private final SliderPanel opacitySlider = new SliderPanel(ac, 1, 10, 150);

	public RotationAndOpacityProperty() {
		initWidget(LayoutBuilder.createCellPanel(100, 150).put(EM.rotation()).put(rotationSlider).put(EM.opacity()).put(opacitySlider).setRowSpacing(5).setMargins(5, 0, 20, 0).build(), EM.rotationAndOpacity());
	}

	@Override
	public void showFor(Extension extension) {
		int rotationSliderValue = ((HasRotation) extension).getRotation() / 10;
		rotationSlider.setValue(rotationSliderValue);

		int opacitySliderValue = ((HasOpacity) extension).getOpacity();
		opacitySlider.setValue(opacitySliderValue);
	}

	@Override
	protected Model getShowForModel(Panel panel) {
		return (panel instanceof HasRotation && panel instanceof HasOpacity) ? panel : null;
	}

	private final class ActionContainer implements SliderPanelListener {

		@Override
		public void onSlide(SliderPanel source, int value) {
			if (source == rotationSlider) {
				((HasRotation) getModel()).setRotation(value * 10);
			} else if (source == opacitySlider) {
				((HasOpacity) getModel()).setOpacity(value);
			}
		}
	}
}