package com.aidado.editor.client.dialog.property;

import com.aidado.common.client.widget.Button;
import com.aidado.common.client.widget.LayoutBuilder;
import com.aidado.common.client.widget.Button.ButtonListener;
import com.aidado.commoneditorviewer.client.model.HasBackground;
import com.aidado.commoneditorviewer.client.model.Model;
import com.aidado.commoneditorviewer.client.model.Panel;
import com.aidado.editor.client.dialog.ColorDialog;
import com.aidado.editor.client.dialog.HasColor;
import com.google.gwt.user.client.ui.FlowPanel;

public class BackgroundColorProperty extends CompositeProperty<HasBackground> {

	private final ActionContainer ac = new ActionContainer();

	private final Button colorButton = Button.createChooseColorButton(ac);

	public BackgroundColorProperty() {
		FlowPanel content = LayoutBuilder.createCellPanel(100, 150).put(EM.color()).put(colorButton).setRowSpacing(5).setMargins(5, 0, 20, 0).build();
		initWidget(content, EM.background());
	}

	private class ActionContainer implements ButtonListener {

		@Override
		public void onClick(Button button, boolean pushed) {
			if (button == colorButton) {
				new ColorDialog(new HasColor() {

					@Override
					public void setColor(String color) {
						getModel().setBackgroundColor(color);
					}
				});
			}
		}
	}

	protected Model getShowForModel(Panel panel) {
		return panel instanceof HasBackground ? panel : null;
	}
}