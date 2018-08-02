package com.aidado.editor.client.dialog;

import com.aidado.common.client.widget.Button;
import com.aidado.common.client.widget.CommonDialog;
import com.aidado.common.client.widget.LayoutBuilder;
import com.aidado.common.client.widget.TextBoxFactory;
import com.aidado.common.client.widget.Button.ButtonListener;
import com.aidado.common.client.widget.LayoutBuilder.Mode;
import com.aidado.common.client.widget.LayoutBuilder.CellPanelBuilder.Alignment;
import com.aidado.editor.client.Accessor;
import com.aidado.editor.client.locale.MessageBundle;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class TitleDialog extends CommonDialog {

	private static final MessageBundle EM = MessageBundle.INSTANCE;
	private final ActionContainer ac = new ActionContainer();
	private final TextBox titleTextBox = TextBoxFactory.createTextBox(200, 30, "[^\\w\\s]", null);
	private final Button okButton = Button.createOkButton(ac);
	private final Button cancelButton = Button.createCancelButton(ac);

	public static void showDialog() {
		new TitleDialog().showCenter();
	}

	public TitleDialog() {
		super(EM.title());
		Widget titleInfo = LayoutBuilder.createBox(EM.titleInfo(), Mode.SUCCESS, 250, 10);
		FlowPanel titlePanel = LayoutBuilder.createCellPanel(50, 200).put(EM.title()).put(titleTextBox).build();
		Widget buttons = LayoutBuilder.createCellPanel(110, 100).put(okButton).put(cancelButton).setAlignment(Alignment.RIGHT).setMargins(0, 10).build();
		add(LayoutBuilder.createFlowPanel().put(titleInfo).put(titlePanel).put(buttons).build(), 10);
	}

	private class ActionContainer implements ButtonListener {

		@Override
		public void onClick(Button button, boolean pushed) {
			if (button == okButton) {
				String title = titleTextBox.getText();
				title = (title != null && title.trim().length() > 0) ? title.trim() : null;
				Accessor.getPhotobook().getRootPanel().setTitle(title);
			}
			hide();
		}
	}
}