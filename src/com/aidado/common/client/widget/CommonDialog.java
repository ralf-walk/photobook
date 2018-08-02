package com.aidado.common.client.widget;

import java.util.ArrayList;
import java.util.List;

import com.aidado.common.client.StyleSetter;
import com.aidado.common.client.icon.Icons;
import com.aidado.common.client.locale.MessageBundle;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class CommonDialog extends PopupPanel {

	protected static final Icons I = Icons.INSTANCE;
	protected static final MessageBundle M = MessageBundle.INSTANCE;

	private final static List<CommonDialog> dialogList = new ArrayList<CommonDialog>();

	private final List<CommonDialog> hiddenDialogList = new ArrayList<CommonDialog>();
	private final CommonDialog instance = this;
	private final ActionContainer ac = new ActionContainer();

	private final HeadingElement headElement = Document.get().createHElement(1);
	private final AbsolutePanel dragPanel = new AbsolutePanel();
	private final AbsolutePanel content = new AbsolutePanel();
	private boolean centered = false;

	public CommonDialog(String caption) {
		setAnimationEnabled(true);
		setModal(true);
		addDomHandler(new MouseUpHandler() {

			@Override
			public void onMouseUp(MouseUpEvent event) {
				event.preventDefault();
				event.stopPropagation();
			}
		}, MouseUpEvent.getType());
		DOM.setStyleAttribute(getElement(), "border", null);
		Style style = getElement().getStyle();
		style.setBackgroundColor("#f4f4f4");
		style.setProperty("border", "1px solid grey");
		style.setProperty("borderRadius", "10px");
		style.setProperty("boxShadow", "inset 0 0 2px 2px #AAAAAA");
		style.setOverflow(Overflow.HIDDEN);

		if (caption != null) {
			Style headElementStyle = headElement.getStyle();
			headElement.setInnerText(caption);
			headElementStyle.setColor("grey");
			headElementStyle.setMarginBottom(0, Unit.PX);
			headElementStyle.setMarginTop(0, Unit.PX);
			headElementStyle.setPadding(3, Unit.PX);
			headElementStyle.setHeight(30, Unit.PX);
			headElementStyle.setFontSize(1.2, Unit.EM);
			headElementStyle.setProperty("textAlign", "center");
			headElementStyle.setBackgroundColor("#C3D9FF");
			headElementStyle.setProperty("border", "2px solid grey");
			headElementStyle.setProperty("borderRadius", "10px 10px 0px 0px");
			content.getElement().getStyle().setOverflow(Overflow.VISIBLE);
			content.getElement().appendChild(headElement);

			Style dragPanelStyle = dragPanel.getElement().getStyle();
			dragPanelStyle.setWidth(100, Unit.PCT);
			dragPanelStyle.setHeight(30, Unit.PX);
			dragPanelStyle.setPadding(3, Unit.PX);
			dragPanelStyle.setCursor(Cursor.POINTER);
			dragPanelStyle.setProperty("filter", "progid:DXImageTransform.Microsoft.gradient(startColorStr='rgba(180,180,180,0.1)', EndColorStr='rgba(20, 20, 20, 0.4)' )");
			StyleSetter.set(dragPanel.getElement(), "background-image", "linear-gradient(top, rgba(180, 180, 180, 0.1), rgba(20, 20, 20, 0.2) )", false, true);
			content.add(dragPanel, 0, 0);

			dragPanel.addDomHandler(ac, MouseDownEvent.getType());
			dragPanel.addDomHandler(ac, MouseUpEvent.getType());
			dragPanel.addDomHandler(ac, MouseMoveEvent.getType());
		}
	}

	public void setCaption(String caption) {
		headElement.setInnerText(caption);
	}

	@Override
	public void add(Widget w) {
		add(w, 0);
	}

	public void add(Widget w, int padding) {
		FlowPanel decoration = new FlowPanel();
		Style style = decoration.getElement().getStyle();
		style.setPadding(Math.max(2, padding), Unit.PX);
		decoration.add(w);
		content.add(decoration);
		super.add(content);
	}

	protected void showPopup(Widget relativeWidget) {
		applyDialogProperties(true, false, 950);
		showRelativeTo(relativeWidget);
	}

	protected void showAlways(PositionCallback callback) {
		applyDialogProperties(false, false, 900);
		setVisible(false);
		DOM.setStyleAttribute(getElement(), "position", "absolute");
		RootPanel.get().add(this);
		callback.setPosition(getOffsetWidth(), getOffsetHeight());
		setVisible(true);
		for (CommonDialog dialog : dialogList) {
			if (dialog.centered && dialog.isShowing()) {
				setVisible(false);
				break;
			}
		}
		dialogList.add(this);
	}

	protected void showCenter() {
		applyDialogProperties(false, true, 990);
		centered = true;
		for (CommonDialog dialog : dialogList) {
			if (dialog.isAttached() && dialog.isVisible()) {
				dialog.setVisible(false);
				hiddenDialogList.add(dialog);
			}
		}
		center();
		dialogList.add(this);
	}

	@Override
	public void hide(boolean autoClosed) {
		hideThisShowOther();
	}

	@Override
	public void hide() {
		hideThisShowOther();
	}

	@Override
	public void setVisible(boolean visible) {
		if (visible) {
			onShow();
		}
		super.setVisible(visible);
	}

	private void hideThisShowOther() {
		if (centered) {
			for (CommonDialog dialog : hiddenDialogList) {
				if (dialogList.contains(dialog) && dialog.isAttached() && !dialog.isVisible()) {
					dialog.onShowAgain();
				}
			}
			hiddenDialogList.clear();
		}
		super.hide(false);
		dialogList.remove(this);
		onHide();
	}

	protected void onHide() {
	}

	protected void onShow() {
	}

	protected void onShowAgain() {
		setVisible(true);
	}

	private void applyDialogProperties(boolean autoHide, boolean glass, int zIndex) {
		setAutoHideEnabled(autoHide);
		setGlassEnabled(glass);
		getElement().getStyle().setZIndex(zIndex);
	}

	protected void dragStopped() {
	}

	private class ActionContainer implements MouseDownHandler, MouseMoveHandler, MouseUpHandler {

		private boolean dragging = false;

		private int offsetX;
		private int offsetY;

		@Override
		public void onMouseDown(MouseDownEvent event) {
			event.stopPropagation();
			event.preventDefault();
			DOM.setCapture(dragPanel.getElement());
			offsetX = event.getRelativeX(dragPanel.getElement());
			offsetY = event.getRelativeY(dragPanel.getElement());
			dragging = true;
		}

		@Override
		public void onMouseUp(MouseUpEvent event) {
			event.stopPropagation();
			event.preventDefault();
			DOM.releaseCapture(dragPanel.getElement());
			dragging = false;
			dragStopped();
		}

		@Override
		public void onMouseMove(MouseMoveEvent event) {
			if (dragging) {
				event.stopPropagation();
				event.preventDefault();
				Element parentElem = getParent().getElement();
				int newX = event.getRelativeX(parentElem) - offsetX;
				int newY = event.getRelativeY(parentElem) - offsetY;
				RootPanel.get().setWidgetPosition(instance, newX, newY);
			}
		}
	}
}