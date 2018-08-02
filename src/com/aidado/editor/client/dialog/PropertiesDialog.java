package com.aidado.editor.client.dialog;

import com.aidado.common.client.widget.CommonDialog;
import com.aidado.common.client.widget.LayoutBuilder;
import com.aidado.editor.client.dialog.property.BackgroundColorProperty;
import com.aidado.editor.client.dialog.property.BorderRadiusProperty;
import com.aidado.editor.client.dialog.property.RotationAndOpacityProperty;
import com.aidado.editor.client.dialog.property.ShadowProperty;
import com.aidado.editor.client.dialog.property.SlideshowProperty;
import com.aidado.editor.client.dialog.property.BorderProperty.BorderColorAndSizeProperty;
import com.aidado.editor.client.dialog.property.BorderProperty.BorderTypeProperty;
import com.aidado.editor.client.locale.MessageBundle;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;

public class PropertiesDialog extends CommonDialog {

	private static final MessageBundle EM = MessageBundle.INSTANCE;

	public PropertiesDialog() {
		super(EM.properties());
		FlowPanel content = LayoutBuilder.createFlowPanel().put(new BackgroundColorProperty()).put(new BorderTypeProperty()).put(new BorderColorAndSizeProperty()).put(new BorderRadiusProperty()).put(
		    new ShadowProperty()).put(new RotationAndOpacityProperty()).put(new SlideshowProperty()).setMargins(0, 0, 0, 10).build();
		add(content);
		showAlways(new PositionCallback() {

			@Override
			public void setPosition(int offsetWidth, int offsetHeight) {
				setPopupPosition(Window.getClientWidth() - offsetWidth - 100, 200);
			}
		});
	}
}