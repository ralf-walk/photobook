package com.aidado.common.client.widget;

import com.aidado.common.client.StyleSetter;
import com.aidado.common.client.icon.Icons;
import com.aidado.common.client.locale.MessageBundle;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.SimplePanel;


public abstract class Button extends SimplePanel {

	public interface ButtonListener {
		void onClick(Button button, boolean pushed);
	}

	public static Button createOkButton(ButtonListener listener) {
		return new PushButton(listener, MessageBundle.INSTANCE.ok(), 100, 22, Icons.INSTANCE.ok(), MessageBundle.INSTANCE.ok());
	}

	public static Button createCancelButton(ButtonListener listener) {
		return new PushButton(listener, MessageBundle.INSTANCE.cancel(), 100, 22, Icons.INSTANCE.cancel(), MessageBundle.INSTANCE.cancel());
	}

	public static Button createChooseColorButton(ButtonListener listener) {
		return new PushButton(listener, MessageBundle.INSTANCE.cancel(), 150, 22, Icons.INSTANCE.color(), MessageBundle.INSTANCE.changeColor());
	}

	public static Button createPushButton(ButtonListener listener, String title, int width, int height, String text) {
		return new PushButton(listener, title, width, height, null, text);
	}

	public static Button createPushButton(ButtonListener listener, String title, int width, int height, ImageResource image) {
		return new PushButton(listener, title, width, height, image, null);
	}

	public static Button createPushButton(ButtonListener listener, String title, int width, int height, ImageResource image, String text) {
		return new PushButton(listener, title, width, height, image, text);
	}

	public static Button createToggleButton(ButtonListener listener, String title, int width, int height, String text) {
		return new ToggleButton(listener, title, width, height, null, text);
	}

	public static Button createToggleButton(ButtonListener listener, String title, int width, int height, ImageResource image) {
		return new ToggleButton(listener, title, width, height, image, null);
	}

	public static Button createToggleButton(ButtonListener listener, String title, int width, int height, ImageResource image, String text) {
		return new ToggleButton(listener, title, width, height, image, text);
	}

	private final DivElement imageContainer = Document.get().createDivElement();
	private final ImageElement imageElement = Document.get().createImageElement();
	private final DivElement textContainer = Document.get().createDivElement();
	private final SpanElement textElement = Document.get().createSpanElement();
	private final FocusPanel frontButton = new FocusPanel();

	private ActionContainer ac = new ActionContainer();
	protected ButtonListener listener;
	protected boolean pushed = false;
	protected boolean enabled = true;

	protected Button(ButtonListener listener, String title, int width, int height, ImageResource imageResource, String text) {
		if (listener == null && this instanceof ButtonListener) {
			this.listener = (ButtonListener) this;
		} else {
			this.listener = listener;
		}

		// set button back
		Style backButtonStyle = getElement().getStyle();
		backButtonStyle.setWidth(width, Unit.PX);
		backButtonStyle.setHeight(height, Unit.PX);
		backButtonStyle.setPosition(Position.RELATIVE);
		backButtonStyle.setOverflow(Overflow.HIDDEN);
		backButtonStyle.setColor("#222222");
		backButtonStyle.setBackgroundColor("#FFFFFF");
		backButtonStyle.setProperty("borderRadius", "5px");

		// image
		if (imageResource != null) {
			imageElement.setSrc(imageResource.getSafeUri().asString());
			Style imageContainerStyle = imageContainer.getStyle();
			imageContainerStyle.setWidth(height, Unit.PX);
			imageContainerStyle.setHeight(height, Unit.PX);
			imageContainerStyle.setFloat(Style.Float.LEFT);
			imageContainerStyle.setPadding(2, Unit.PX);
			imageContainerStyle.setProperty("background", "none repeat scroll 0 0 rgba(0, 0, 0, 0.1)");
			imageContainerStyle.setProperty("boxShadow", "1px 0 0 rgba(0, 0, 0, 0.5), 2px 0 0 rgba(255, 255, 255, 0.5)");
			imageContainerStyle.setProperty("borderBottomLeftRadius", "5px");
			imageContainerStyle.setProperty("borderTopLeftRadius", "5px");

			imageElement.getStyle().setWidth(height - 4, Unit.PX);
			imageElement.getStyle().setHeight(height - 4, Unit.PX);
		} else {
			imageContainer.getStyle().setDisplay(Display.NONE);
		}

		// text
		if (text != null) {
			textElement.setInnerText(text);
			Style textContainerStyle = textContainer.getStyle();
			textContainerStyle.setWidth(width - height, Unit.PX);
			textContainerStyle.setHeight(height, Unit.PX);
			textContainerStyle.setFloat(Style.Float.RIGHT);
			textContainerStyle.setPadding(3, Unit.PX);
		} else {
			textContainer.getStyle().setDisplay(Display.NONE);
		}

		// button front
		frontButton.addMouseUpHandler(ac);
		frontButton.addMouseDownHandler(ac);
		frontButton.addMouseOverHandler(ac);
		frontButton.addMouseOutHandler(ac);
		frontButton.setTitle(title);
		Style frontButtonStyle = frontButton.getElement().getStyle();
		frontButtonStyle.setWidth(width, Unit.PX);
		frontButtonStyle.setHeight(height, Unit.PX);
		frontButtonStyle.setPosition(Position.ABSOLUTE);
		frontButtonStyle.setLeft(0, Unit.PX);
		frontButtonStyle.setTop(0, Unit.PX);
		frontButtonStyle.setProperty("border", "1px solid black");
		frontButtonStyle.setProperty("borderRadius", "5px");
		frontButtonStyle.setProperty("boxShadow", "0 0 1px 1px rgba(255, 255, 255, 0.8) inset, 0 1px 0 rgba(0, 0, 0, 0.15)");

		// putting all together
		imageContainer.appendChild(imageElement);
		textContainer.appendChild(textElement);
		getElement().appendChild(imageContainer);
		getElement().appendChild(textContainer);
		add(frontButton);
		setEnabled(true);
	}

	public void setText(String text) {
		textElement.setInnerText(text);
	}

	public void setId(String id) {
		frontButton.getElement().setId(id);
	}

	public void setVisible(Visibility visibility) {
		this.getElement().getStyle().setVisibility(visibility);
		frontButton.getElement().getStyle().setVisibility(visibility);
	}

	public void setEnabled(boolean enabled) {
		Style style = frontButton.getElement().getStyle();
		if (enabled) {
			StyleSetter.set(frontButton.getElement(), "background-image", "linear-gradient(top, rgba(180, 180, 180, 0.1),	rgba(20, 20, 20, 0.2))", false, true);
			style.setCursor(Cursor.POINTER);
		} else {
			StyleSetter.set(frontButton.getElement(), "background-image", "linear-gradient(top, rgba(180, 180, 180, 0.4),	rgba(20, 20, 20, 0.5))", false, true);
			style.setCursor(Cursor.DEFAULT);
		}
		this.enabled = enabled;
	}

	private void setHover(boolean hover) {
		if (enabled) {
			Style style = frontButton.getElement().getStyle();
			if (hover) {
				StyleSetter.set(frontButton.getElement(), "background-image", "linear-gradient(top, rgba(180, 180, 180, 0.06),	rgba(20, 20, 20, 0.16))", false, true);
				style.setProperty("filter", "progid:DXImageTransform.Microsoft.gradient(startColorStr='rgba(180,180,180,0.0)', EndColorStr='rgba(20, 20, 20, 0.1)')");
			} else {
				setPushed(pushed);
			}
		}
	}

	public void setPushed(boolean pushed) {
		if (enabled) {
			if (pushed) {
				Style style = frontButton.getElement().getStyle();
				StyleSetter.set(frontButton.getElement(), "background-image", "linear-gradient(top, rgba(180, 180, 180, 0.1),	rgba(20, 20, 20, 0))", false, true);
				style.setProperty("filter", "progid:DXImageTransform.Microsoft.gradient(startColorStr='rgba(180,180,180,0.0)', EndColorStr='rgba(20, 20, 20, 0.1)')");
			} else {
				setEnabled(true);
			}
		}
		this.pushed = pushed;
	}

	protected abstract void onPushEvent(boolean mouseUp);

	private class ActionContainer implements MouseUpHandler, MouseDownHandler, MouseOutHandler, MouseOverHandler {

		@Override
		public void onMouseOut(MouseOutEvent event) {
			setHover(false);
		}

		@Override
		public void onMouseOver(MouseOverEvent event) {
			setHover(true);
		}

		@Override
		public void onMouseUp(MouseUpEvent event) {
			event.stopPropagation();
			event.preventDefault();
			if (enabled) {
				onPushEvent(true);
			}
		}

		@Override
		public void onMouseDown(MouseDownEvent event) {
			event.stopPropagation();
			event.preventDefault();
			if (enabled) {
				onPushEvent(false);
			}
		}
	}

	public static class PushButton extends Button {

		protected PushButton(ButtonListener listener, String title, int width, int height, ImageResource image, String text) {
			super(listener, title, width, height, image, text);
		}

		@Override
		protected void onPushEvent(boolean mouseUp) {
			setPushed(!mouseUp);
			if (mouseUp) {
				if (listener != null) {
					listener.onClick(this, true);
				}
			}
		}
	}

	public static class ToggleButton extends Button {

		protected ToggleButton(ButtonListener listener, String title, int width, int height, ImageResource image, String text) {
			super(listener, title, width, height, image, text);
		}

		@Override
		protected void onPushEvent(boolean mouseUp) {
			if (mouseUp) {
				boolean newPushed = !pushed;
				setPushed(newPushed);
				if (listener != null) {
					listener.onClick(this, newPushed);
				}
			}
		}
	}
}