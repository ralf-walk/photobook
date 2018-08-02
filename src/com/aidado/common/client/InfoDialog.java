package com.aidado.common.client;

import com.aidado.common.client.widget.Button;
import com.aidado.common.client.widget.CommonDialog;
import com.aidado.common.client.widget.LayoutBuilder;
import com.aidado.common.client.widget.Button.ButtonListener;
import com.aidado.common.client.widget.LayoutBuilder.CellPanelBuilder.Alignment;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class InfoDialog extends CommonDialog {

	public static abstract class InfoDialogListener {
		public abstract void onOkClicked();

		public void onCancelClicked() {
		}
	}

	private final ActionContainer ac = new ActionContainer();

	private final FlowPanel content = new FlowPanel();
	private final Button okButton = Button.createOkButton(ac);
	private final Button cancelButton = Button.createCancelButton(ac);

	private InfoDialogListener listener;

	public InfoDialog(Widget w) {
		this(w, false, null);
	}

	public InfoDialog(Widget w, boolean cancelButtonEnabled, InfoDialogListener listener) {
		super(null);
		this.listener = listener;
		content.clear();
		content.add(w);

		Widget controls = null;
		if (cancelButtonEnabled) {
			controls = LayoutBuilder.createCellPanel(110, 100).put(cancelButton).put(okButton).setAlignment(Alignment.RIGHT).build();
		} else {
			controls = LayoutBuilder.createCellPanel(100).put(okButton).setAlignment(Alignment.RIGHT).build();
		}
		content.add(controls);
		add(content, 10);
		showCenter();
	}

	private class ActionContainer implements ButtonListener {

		@Override
		public void onClick(Button button, boolean pushed) {
			if (listener != null) {
				if (button == okButton) {
					listener.onOkClicked();
				} else {
					listener.onCancelClicked();
				}
			}
			hide();
		}
	}
}