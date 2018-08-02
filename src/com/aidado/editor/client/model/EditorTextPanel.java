package com.aidado.editor.client.model;

import com.aidado.commoneditorviewer.client.model.BaseTextPanel;
import com.aidado.editor.client.dialog.TextEditorDialog;
import com.aidado.editor.client.locale.MessageBundle;
import com.aidado.editor.client.model.dnd.CarrierPanel.CarrierAwarePanel;
import com.google.gwt.user.client.ui.Widget;

public class EditorTextPanel extends BaseTextPanel implements CarrierAwarePanel {

	public EditorTextPanel() {
		getElement().getStyle().setBackgroundColor("#CCCCCC");
		setBackgroundColor("#FFFFFF");
		setHtmlText(MessageBundle.INSTANCE.textPanelDefaultText());
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
		new TextEditorDialog(this);
	}
}