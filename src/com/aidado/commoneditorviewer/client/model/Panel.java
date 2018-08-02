package com.aidado.commoneditorviewer.client.model;

import com.aidado.commoneditorviewer.client.model.Model.Item;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Widget;


public class Panel extends AbsolutePanel implements HasPanel, Item {

	private int left;
	private int top;
	private int width;
	private int height;

	private final int ABSOLUTE_MIN_SIZE = 40;

	public Panel() {
		Style style = getElement().getStyle();
		style.setOverflow(Overflow.VISIBLE);
		style.setDisplay(Display.BLOCK);
	}

	protected void update() {
		setLeft(left);
		setTop(top);
	}

	private int getParentBorderSize() {
		if (getParent() != null) {
			return DOM.getIntStyleAttribute(getParent().getElement(), "borderWidth");
		}
		return 0;
	}

	private int getBorderSize() {
		return DOM.getIntStyleAttribute(getElement(), "borderWidth");
	}

	public void requestUpdate() {
		if (this.isAttached()) {
			update();
		}
	}

	@Override
	protected void onAttach() {
		// because child attaching is before onLoad(), restore positioning
		getElement().getStyle().setPosition(Position.RELATIVE);
		left = Math.max(Math.abs(left), Math.abs(DOM.getIntStyleAttribute(getElement(), "left")));
		top = Math.max(Math.abs(top), Math.abs(DOM.getIntStyleAttribute(getElement(), "top")));
		if (getParent() instanceof AbsolutePanel) {
			((AbsolutePanel) getParent()).setWidgetPosition(this, left, top);
		}
		super.onAttach();
	}

	@Override
	protected void onLoad() {
		super.onLoad();
		update();
	}

	public int getDistanceLeft(Widget w) {
		return DOM.getAbsoluteLeft(w.getElement()) - DOM.getAbsoluteLeft(getElement());
	}

	public int getDistanceTop(Widget w) {
		return DOM.getAbsoluteTop(w.getElement()) - DOM.getAbsoluteTop(getElement());
	}

	@Override
	public void setWidgetPosition(Widget w, int left, int top) {
		super.setWidgetPosition(w, left - getBorderSize(), top - getBorderSize());
	}

	@Override
	public void setPixelSize(int width, int height) {
		this.width = Math.max(ABSOLUTE_MIN_SIZE, width);
		this.height = Math.max(ABSOLUTE_MIN_SIZE, height);
		super.setPixelSize(this.width, this.height);
		requestUpdate();
	}

	public int getOffsetLeft() {
		return DOM.getElementPropertyInt(getElement(), "offsetLeft");
	}

	public int getOffsetTop() {
		return DOM.getElementPropertyInt(getElement(), "offsetTop");
	}

	public void setLeft(int left) {
		this.left = left;
		getElement().getStyle().setLeft(left - getParentBorderSize(), Unit.PX);
	}

	public int getLeft() {
		return left;
	}

	public void setTop(int top) {
		this.top = top;
		getElement().getStyle().setTop(top - getParentBorderSize(), Unit.PX);
	}

	public int getTop() {
		return top;
	}

	public void setWidth(int width) {
		this.width = width;
		getElement().getStyle().setWidth(width, Unit.PX);
		requestUpdate();
	}

	public int getWidth() {
		return width;
	}

	public void setHeight(int height) {
		this.height = height;
		getElement().getStyle().setHeight(height, Unit.PX);
		requestUpdate();
	}

	public int getHeight() {
		return height;
	}

	public void setZIndex(int zIndex) {
		getElement().getStyle().setZIndex(zIndex);
	}

	public int getZIndex() {
		return DOM.getIntStyleAttribute(getElement(), "zIndex");
	}

	public void clearZIndex() {
		getElement().getStyle().clearZIndex();
	}

	public int getMinWidth() {
		return ABSOLUTE_MIN_SIZE;
	}

	public int getMinHeight() {
		return ABSOLUTE_MIN_SIZE;
	}
}