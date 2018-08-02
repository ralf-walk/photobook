package com.aidado.common.client.widget;

import com.aidado.common.client.icon.Icons;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Image;


public class SliderPanel extends AbsolutePanel {

	private final ActionContainer ac;
	private final SliderPanelListener listener;

	private final int height = 20;
	private final AbsolutePanel sliderLinePanel = new AbsolutePanel();

	private final Image slider = new Image(Icons.INSTANCE.slider());
	private final Image sliderLeft = new Image(Icons.INSTANCE.sliderLeft());
	private final Image sliderRight = new Image(Icons.INSTANCE.sliderRight());

	private final int sliderLineWidth;

	private float sliderRangePart;
	private final int minValue;
	private final int maxValue;
	private int valueCount;
	private int value;

	public interface SliderPanelListener {
		void onSlide(SliderPanel source, int value);
	}

	public SliderPanel(SliderPanelListener listener, int minValue, int maxValue, int width) {
		this.listener = listener;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.valueCount = (maxValue - minValue) + 1;
		setPixelSize(width, height);

		sliderLineWidth = width - 2 * height;
		sliderRangePart = (sliderLineWidth - slider.getWidth()) / (float) (this.valueCount - 1);

		ac = new ActionContainer();
		sliderLeft.addDomHandler(ac, MouseUpEvent.getType());
		sliderLeft.getElement().getStyle().setCursor(Cursor.POINTER);
		add(sliderLeft);
		setWidgetPosition(sliderLeft, 0, 0);

		sliderRight.addDomHandler(ac, MouseUpEvent.getType());
		sliderRight.getElement().getStyle().setCursor(Cursor.POINTER);
		add(sliderRight);
		setWidgetPosition(sliderRight, width - sliderRight.getWidth(), 0);

		sliderLinePanel.setPixelSize(sliderLineWidth, height);
		Image sliderLine = new Image(Icons.INSTANCE.sliderLine());
		sliderLinePanel.getElement().getStyle().setBackgroundImage("url(" + sliderLine.getUrl() + ")");
		sliderLinePanel.addDomHandler(ac, MouseUpEvent.getType());

		slider.addDomHandler(ac, MouseDownEvent.getType());
		slider.addDomHandler(ac, MouseUpEvent.getType());
		slider.addDomHandler(ac, MouseMoveEvent.getType());
		slider.getElement().getStyle().setCursor(Cursor.POINTER);
		sliderLinePanel.add(slider);
		sliderLinePanel.setWidgetPosition(slider, 0, 0);

		add(sliderLinePanel);
		setWidgetPosition(sliderLinePanel, height, 0);
	}

	public void setValue(int value) {
		if (minValue <= value && value <= maxValue) {
			this.value = value;
			int sliderPosition = value - minValue;
			sliderLinePanel.setWidgetPosition(slider, Math.round(sliderRangePart * sliderPosition), 0);
		}
	}

	private void notifyListener(int value) {
		setValue(value);
		listener.onSlide(this, value);
	}

	public int getValue() {
		return value;
	}

	private class ActionContainer implements MouseDownHandler, MouseUpHandler, MouseMoveHandler {

		private final float offset = (sliderRangePart + slider.getWidth()) / (float) 2 - slider.getWidth();
		private boolean sliding;

		@Override
		public void onMouseDown(MouseDownEvent event) {
			event.stopPropagation();
			event.preventDefault();
			DOM.setCapture(slider.getElement());
			sliding = true;
		}

		@Override
		public void onMouseMove(MouseMoveEvent event) {
			event.stopPropagation();
			event.preventDefault();
			if (sliding) {
				int sliderPosition = event.getRelativeX(sliderLinePanel.getElement());
				setValueByCursor(sliderPosition);
			}
		}

		@Override
		public void onMouseUp(MouseUpEvent event) {
			event.stopPropagation();
			event.preventDefault();

			Image source = (Image) event.getSource();
			int value = getValue();
			if (source == sliderLeft) {
				if (value > minValue) {
					notifyListener(value - 1);
				}
			} else if (source == sliderRight) {
				if (value < maxValue) {
					notifyListener(value + 1);
				}
			} else if (source == slider) {
				if (sliding) {
					DOM.releaseCapture(slider.getElement());
					sliding = false;
				} else {
					int cursorPosition = event.getRelativeX(sliderLinePanel.getElement());
					setValueByCursor(cursorPosition);
				}
			}
		}

		private void setValueByCursor(int cursorPosition) {
			cursorPosition = Math.max(0, Math.min(cursorPosition, sliderLineWidth));
			float offsetPosition = (float) cursorPosition + offset;
			int sliderValue = (int) (offsetPosition / sliderRangePart);
			sliderValue += minValue;
			if (value != sliderValue) {
				notifyListener(sliderValue);
			}
		}
	}
}