package com.aidado.common.client.widget;

import java.util.Arrays;
import java.util.List;

import com.aidado.common.client.icon.Icons;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;


public class DropDownPanel<T extends ListEntry> extends FlowPanel {

	public interface DropDownPanelListener<T extends ListEntry> {
		void onChange(DropDownPanel<T> source, T dropDownEntry);
	}

	private final DropDownPanelListener<T> listener;
	private final DropDownPanel<T> instance = this;
	private final Image down = new Image(Icons.INSTANCE.dropdownDown());
	private final ActionContainer ac = new ActionContainer();
	private final PopupPanel popup = new PopupPanel(true, false);
	private final ListEntry alwaysDisplayEntry; 
	
	public DropDownPanel(DropDownPanelListener<T> listener, T[] values, int width, ListEntry alwaysDisplayEntry) {
		this(listener, Arrays.asList(values), width, alwaysDisplayEntry);
	}

	public DropDownPanel(DropDownPanelListener<T> listener, List<T> listEntryList, int width, ListEntry alwaysDisplayEntry) {
		setPixelSize(width, 22);
		this.listener = listener;
		this.alwaysDisplayEntry = alwaysDisplayEntry;
		addDomHandler(ac, MouseUpEvent.getType());

		Style dropDownStyle = getElement().getStyle();
		dropDownStyle.setBackgroundColor("#ffffff");
		dropDownStyle.setBorderColor("#000000");
		dropDownStyle.setBorderWidth(1, Unit.PX);
		dropDownStyle.setProperty("borderRadius", "5px");
		dropDownStyle.setBorderStyle(BorderStyle.SOLID);
		dropDownStyle.setCursor(Cursor.POINTER);
		dropDownStyle.setOverflow(Overflow.HIDDEN);

		down.setPixelSize(20, 20);
		down.getElement().getStyle().setFloat(Style.Float.RIGHT);
		add(down);

		FlowPanel popupContent = new FlowPanel();
		for (T dropDownEntry : listEntryList) {
			PopupLine popupLine = new PopupLine(dropDownEntry);
			popupLine.addDomHandler(ac, MouseOverEvent.getType());
			popupLine.addDomHandler(ac, MouseOutEvent.getType());
			popupLine.addDomHandler(ac, MouseUpEvent.getType());
			popupContent.add(popupLine);
		}
		Style popupContentStyle = popupContent.getElement().getStyle();
		popupContentStyle.setCursor(Cursor.POINTER);
		popupContentStyle.setBackgroundColor("#ffffff");
		popupContentStyle.setBorderColor("#000000");
		popupContentStyle.setBorderWidth(1, Unit.PX);
		popupContentStyle.setBorderStyle(BorderStyle.SOLID);
		popupContentStyle.setProperty("borderRadius", "5px");
		popupContentStyle.setOverflow(Overflow.HIDDEN);
		popupContentStyle.setWidth(width, Unit.PX);
		popup.getElement().getStyle().setZIndex(999);
		popup.add(popupContent);

		if (alwaysDisplayEntry != null) {
			selectEntry(alwaysDisplayEntry);
		} else {
			setSelectedIndex(0);
		}
	}

	@SuppressWarnings("unchecked")
  public void setSelectedIndex(int index) {
		FlowPanel popupContent = (FlowPanel) popup.getWidget();
		if (index >= 0 && index < popupContent.getWidgetCount()) {
			selectEntry(((DropDownPanel<T>.PopupLine) popupContent.getWidget(index)).getDropDownEntry());
		} else {
			selectEntry(null);
		}
	}

	public void setSelected(T entry) {
		FlowPanel popupContent = (FlowPanel) popup.getWidget();
		if (entry == null) {
			selectEntry(null);
		} else {
			for (int i = 0; i < popupContent.getWidgetCount(); i++) {
				if (popupContent.getWidget(i) == entry) {
					selectEntry(entry);
				}
			}
		}
	}
	
	public boolean isSelecting() {
	  return popup.isShowing();
	}

	private void selectEntry(ListEntry entry) {
		clear();
		if (entry != null) {
			if (entry.getImage() != null) {
				SimplePanel imageContainer = new SimplePanel();
				Style imageContainerStyle = imageContainer.getElement().getStyle();
				imageContainerStyle.setWidth(22, Unit.PX);
				imageContainerStyle.setPadding(1, Unit.PX);
				imageContainerStyle.setFloat(Float.LEFT);
				imageContainerStyle.setProperty("background", "none repeat scroll 0 0 rgba(0, 0, 0, 0.1)");
				imageContainerStyle.setProperty("boxShadow", "1px 0 0 rgba(0, 0, 0, 0.5), 2px 0 0 rgba(255, 255, 255, 0.5)");
				imageContainerStyle.setProperty("borderBottomLeftRadius", "5px");
				imageContainerStyle.setProperty("borderTopLeftRadius", "5px");				
				Image entryImage = new Image();
				entryImage.setUrl(entry.getImage().getSafeUri().asString());
				entryImage.setPixelSize(18, 18);
				imageContainer.setWidget(entryImage);
				add(imageContainer);
			}
			HTML textHtml = new HTML(entry.getText());
			textHtml.getElement().getStyle().setPadding(3, Unit.PX);
			textHtml.getElement().getStyle().setFloat(Style.Float.LEFT);
			add(textHtml);
		}
		add(down);
	}

	private class ActionContainer implements MouseDownHandler, MouseOverHandler, MouseOutHandler, MouseUpHandler {

		@Override
		public void onMouseDown(MouseDownEvent event) {
			event.stopPropagation();
			event.preventDefault();
		}

		@Override
		public void onMouseOver(MouseOverEvent event) {
			event.stopPropagation();
			event.preventDefault();
			((FlowPanel) event.getSource()).getElement().getStyle().setBackgroundColor("#e3e8f3");
		}

		@Override
		public void onMouseOut(MouseOutEvent event) {
			event.stopPropagation();
			event.preventDefault();
			((FlowPanel) event.getSource()).getElement().getStyle().clearBackgroundColor();
		}

		@SuppressWarnings("unchecked")
    @Override
		public void onMouseUp(MouseUpEvent event) {
			event.stopPropagation();
			event.preventDefault();
			if (event.getSource() == instance) {
				int left = DOM.getAbsoluteLeft(instance.getElement());
				int top = DOM.getAbsoluteTop(instance.getElement()) + instance.getOffsetHeight();
				popup.setPopupPosition(left, top);
				popup.show();
			} else {
				DropDownPanel<T>.PopupLine popupLine = (DropDownPanel<T>.PopupLine) event.getSource();
				popupLine.getElement().getStyle().clearBackgroundColor();
				if (alwaysDisplayEntry == null) {
					T entry = popupLine.getDropDownEntry();
					selectEntry(entry);
				}
				listener.onChange(instance, popupLine.getDropDownEntry());
				popup.hide();
			}
		}
	}

	private class PopupLine extends FlowPanel {

		private final T entry;

		public PopupLine(T entry) {
			this.entry = entry;
			Style popupLineStyle = getElement().getStyle();
			popupLineStyle.setPadding(5, Unit.PX);
			popupLineStyle.setFloat(Style.Float.NONE);
			if (entry.getImage() != null) {
				Image entryImage = new Image();
				entryImage.setUrl(entry.getImage().getSafeUri().asString());
				entryImage.setPixelSize(20, 20);
				Style entryImageStyle = entryImage.getElement().getStyle();
				entryImageStyle.setFloat(Float.LEFT);
				entryImageStyle.setMarginRight(5, Unit.PX);
				entryImageStyle.setProperty("boxShadow", "0 0 2px 2px #CCCCCC");
				add(entryImage);
			}
			HTML textHtml = new HTML(entry.getText());
			textHtml.getElement().getStyle().setPadding(3, Unit.PX);
			add(textHtml);
		}

		public T getDropDownEntry() {
			return entry;
		}
	}
}