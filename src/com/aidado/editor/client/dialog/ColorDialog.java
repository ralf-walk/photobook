package com.aidado.editor.client.dialog;

import com.aidado.common.client.widget.Button;
import com.aidado.common.client.widget.LayoutBuilder;
import com.aidado.common.client.widget.Button.ButtonListener;
import com.aidado.common.client.widget.LayoutBuilder.CellPanelBuilder;
import com.aidado.common.client.widget.LayoutBuilder.CellPanelBuilder.Alignment;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SimplePanel;

public class ColorDialog extends ModelAwareDialog<HasColor> {

	private final ActionContainer ac = new ActionContainer();

	private final String[][] colors = new String[][] { { "#300000", "#600000", "#800000", "#b00000", "#d00000", "#f00000", "#f02020", "#f05050", "#f08080", "#f0a0a0", "#f0c0c0" },

	{ "#302000", "#603000", "#804000", "#b05000", "#d07000", "#f08000", "#f09020", "#f0a050", "#f0b080", "#f0d0a0", "#f0e0c0" },

	{ "#303000", "#506000", "#808000", "#A0B000", "#D0D000", "#F0F000", "#F0F020", "#F0F050", "#F0F080", "#F0F0A0", "#F0F0C0" },

	{ "#203000", "#306000", "#408000", "#50B000", "#70D000", "#80F000", "#90F020", "#A0F050", "#B0F080", "#D0F0A0", "#E0F0C0" },

	{ "#003000", "#006000", "#008000", "#00B000", "#00D000", "#00F000", "#20F020", "#50F050", "#80F080", "#A0F0A0", "#C0F0C0" },

	{ "#003020", "#006030", "#008040", "#00B050", "#00D070", "#00F080", "#20F090", "#50F0A0", "#80F0B0", "#A0F0D0", "#C0F0E0" },

	{ "#003030", "#006050", "#008080", "#00B0A0", "#00D0D0", "#00F0F0", "#20F0F0", "#50F0F0", "#80F0F0", "#A0F0F0", "#C0F0F0" },

	{ "#002030", "#003060", "#004080", "#0050B0", "#0070D0", "#0080F0", "#2090F0", "#50A0F0", "#80B0F0", "#A0D0F0", "#C0E0F0" },

	{ "#000030", "#000060", "#000080", "#0000B0", "#0000D0", "#0000F0", "#2020F0", "#5050F0", "#8080F0", "#A0A0F0", "#C0C0F0" },

	{ "#200030", "#300060", "#400080", "#5000B0", "#7000D0", "#8000F0", "#9020F0", "#A050F0", "#B080F0", "#D0A0F0", "#E0C0F0" },

	{ "#300030", "#600050", "#800080", "#B000A0", "#D000D0", "#F000F0", "#F020F0", "#F050F0", "#F080F0", "#F0A0F0", "#F0C0F0" },

	{ "#300020", "#600030", "#800040", "#B00050", "#D00070", "#F00080", "#F02090", "#F050A0", "#F080B0", "#F0A0D0", "#F0C0E0" },

	{ "#000000", "#101010", "#202020", "#404040", "#505050", "#606060", "#808080", "#a0a0a0", "#b0b0b0", "#d0d0d0", "#ffffff" } };

	public ColorDialog(HasColor isColor) {
		super(M.changeColor());
		setModel(isColor);
		FlowPanel content = new FlowPanel();
		CellPanelBuilder builder = LayoutBuilder.createCellPanel(15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15);
		for (int i = 0; i < colors.length; i++) {
			for (int j = 0; j < colors[0].length; j++) {
				SimplePanel color = new SimplePanel();
				color.addDomHandler(ac, MouseUpEvent.getType());
				Style colorStyle = color.getElement().getStyle();
				colorStyle.setBackgroundColor(colors[i][j]);
				colorStyle.setWidth(15, Unit.PX);
				colorStyle.setHeight(15, Unit.PX);
				colorStyle.setCursor(Cursor.POINTER);
				builder.put(color);
			}
		}
		FlowPanel colorPanel = builder.build();
		colorPanel.getElement().getStyle().setProperty("boxShadow", "0 0 3px 2px rgba(0, 0, 0, 0.8)");
		content.add(colorPanel);

		Button cancelButton = Button.createCancelButton(ac);
		content.add(LayoutBuilder.createCellPanel(100).put(cancelButton).setMargins(0, 10).setAlignment(Alignment.RIGHT).build());
		add(content, 10);
		showCenter();
	}

	private void applyColor(String color) {
		getModel().setColor(color);
		hide();
	}

	private class ActionContainer implements ButtonListener, MouseUpHandler {

		@Override
		public void onMouseUp(MouseUpEvent event) {
			event.stopPropagation();
			event.preventDefault();
			SimplePanel cell = (SimplePanel) event.getSource();
			String color = DOM.getStyleAttribute(cell.getElement(), "backgroundColor");
			applyColor(color);
		}

		@Override
		public void onClick(Button button, boolean pushed) {
			hide();
		}
	}
}