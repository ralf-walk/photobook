package com.aidado.common.client.widget;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;

public class TextBoxFactory {

	public static TextBox createTextBox(int width, int maxLength, String invalidCharsPattern, String title) {
		TextBox textBox = new TextBox();
		applyInputStyle(width, maxLength, invalidCharsPattern, title, textBox);
		return textBox;
	}

	public static PasswordTextBox createPasswordTextBox(int width, int maxLength, String pattern, String title) {
		PasswordTextBox passwordTextBox = new PasswordTextBox();
		applyInputStyle(width, maxLength, pattern, title, passwordTextBox);
		return passwordTextBox;
	}

	private static void applyInputStyle(int width, int maxLength, String invalidCharsPattern, String title, TextBox textBox) {
		Style style = textBox.getElement().getStyle();
		style.setWidth(width, Unit.PX);
		style.setHeight(22, Unit.PX);
		style.setBackgroundColor("#FFFFFF");
		style.setProperty("border", "1px solid black");
		style.setProperty("borderRadius", "5px");
    style.setProperty("boxShadow", "inset 0 0 1px 1px #AAAAAA");
		textBox.setMaxLength(maxLength);
		textBox.setTitle(title);
		ActionContainer ac  = new ActionContainer(textBox, invalidCharsPattern);
		textBox.addMouseOverHandler(ac);
		textBox.addMouseOutHandler(ac);
		if (invalidCharsPattern != null) {
			textBox.addKeyUpHandler(ac);
		}
	}

	private static final class ActionContainer implements KeyUpHandler, MouseOverHandler, MouseOutHandler {

		private final TextBox textBox;
		private final String invalidCharsPattern;

		public ActionContainer(TextBox textBox, String invalidCharsPattern) {
			this.textBox = textBox;
			this.invalidCharsPattern = invalidCharsPattern;
		}

		@Override
		public void onKeyUp(KeyUpEvent event) {
			textBox.setText(textBox.getText().replaceAll(invalidCharsPattern, ""));
		}

		@Override
		public void onMouseOver(MouseOverEvent event) {
			textBox.getElement().getStyle().setProperty("border", "1px solid blue");
		}

		@Override
		public void onMouseOut(MouseOutEvent event) {
			textBox.getElement().getStyle().setProperty("border", "1px solid black");
		}
	}
}