package com.aidado.common.client.widget;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class LayoutBuilder<T extends LayoutBuilder<T>> {

	public static CellPanelBuilder createCellPanel(int... columnWidthsPx) {
		return new CellPanelBuilder(columnWidthsPx);
	}

	public static FlowPanelBuilder createFlowPanel() {
		return new FlowPanelBuilder();
	}

	public static FlowPanel createBox(String info, Mode mode, int maxWidth, int marginBottom) {
		return createBox(new HTML(info), mode, maxWidth, marginBottom);
	}

	public static FlowPanel createBox(Widget w, Mode mode, int maxWidth, int marginBottom) {
		return LayoutBuilder.createFlowPanel().put(w).setStyleMode(mode).setMaxWidth(maxWidth).setMargins(0, 0, 0, marginBottom).build();
	}

	protected FlowPanel flowPanel = new FlowPanel();

	public enum Mode {
		NORMAL(null), SUCCESS("success"), WARNING("warning"), ERROR("error");

		private final String styleName;

		private Mode(String styleName) {
			this.styleName = styleName;
		}

		public String getStyleName() {
			return styleName;
		}
	}

	@SuppressWarnings("unchecked")
	public T setStyleMode(Mode mode) {
		flowPanel.addStyleName("box");
		if (mode != Mode.NORMAL) {
			flowPanel.addStyleName(mode.getStyleName());
		}
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T setStyleProperty(String name, String value) {
		Style style = flowPanel.getElement().getStyle();
		style.setProperty(name, value);
		return (T) this;
	}

	public T setMargins(int left, int top) {
		return setMargins(left, top, 0, 0);
	}

	@SuppressWarnings("unchecked")
	public T setMargins(int left, int top, int right, int bottom) {
		Style style = flowPanel.getElement().getStyle();
		if (left > 0) {
			style.setMarginLeft(left, Unit.PX);
		}
		if (top > 0) {
			style.setMarginTop(top, Unit.PX);
		}
		if (right > 0) {
			style.setMarginRight(right, Unit.PX);
		}
		if (bottom > 0) {
			style.setMarginBottom(bottom, Unit.PX);
		}
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T setMaxWidth(int maxWidth) {
		if (maxWidth > 0) {
			flowPanel.getElement().getStyle().setProperty("maxWidth", maxWidth + "px");
		}
		return (T) this;
	}

	public abstract T put(Widget w);

	public abstract T put(String html);

	public abstract FlowPanel build();

	public static class FlowPanelBuilder extends LayoutBuilder<FlowPanelBuilder> {

		private FlowPanelBuilder() {
		}

		@Override
		public FlowPanelBuilder put(Widget w) {
			if (w != null) {
				flowPanel.add(w);
			}
			return this;
		}

		@Override
		public FlowPanelBuilder put(String html) {
			if (html != null) {
				flowPanel.add(new HTML(html));
			}
			return this;
		}

		@Override
		public FlowPanel build() {
			return flowPanel;
		}
	}

	public static class CellPanelBuilder extends LayoutBuilder<CellPanelBuilder> {

		public interface RowAttachListener {
			void rowAttached(FlowPanel cellPanel, FlowPanel row);
		}

		public enum Alignment {
			LEFT, RIGHT, CENTER;
		}

		private final List<Widget> widgets = new ArrayList<Widget>();
		private final int[] columnWidthsPx;

		private int rowSpacing = 0;
		private Alignment alignment = Alignment.LEFT;
		private RowAttachListener rowAttachListener = null;

		private CellPanelBuilder(int... columnWidthsPx) {
			this.columnWidthsPx = columnWidthsPx;
		}

		@Override
		public CellPanelBuilder put(Widget w) {
			widgets.add(w);
			return this;
		}

		@Override
		public CellPanelBuilder put(String html) {
			return put(new HTML(html));
		}

		public CellPanelBuilder putEmpty() {
			return put(new HTML("&nbsp;"));
		}

		public CellPanelBuilder setRowSpacing(int rowSpacing) {
			this.rowSpacing = rowSpacing;
			return this;
		}

		public CellPanelBuilder setAlignment(Alignment alignment) {
			this.alignment = alignment;
			return this;
		}

		public CellPanelBuilder setRowAttachListener(RowAttachListener rowAttachListener) {
			this.rowAttachListener = rowAttachListener;
			return this;
		}

		@Override
		public FlowPanel build() {
			FlowPanel row = new FlowPanel();
			for (Widget w : widgets) {

				FlowPanel cell = new FlowPanel();
				Style cellStyle = cell.getElement().getStyle();
				cellStyle.setWidth(columnWidthsPx[widgets.indexOf(w) % columnWidthsPx.length], Unit.PX);
				cell.add(w);
				row.add(cell);

				cellStyle.setFloat(Style.Float.LEFT);
				if (row.getWidgetCount() == columnWidthsPx.length || widgets.indexOf(w) == widgets.size() - 1) {
					SimplePanel clearPanel = new SimplePanel();
					clearPanel.getElement().getStyle().setProperty("clear", "left");
					row.add(clearPanel);
					flowPanel.add(row);
					if (rowAttachListener != null) {
						rowAttachListener.rowAttached(flowPanel, row);
					}
					row = new FlowPanel();
					row.getElement().getStyle().setMarginTop(rowSpacing, Unit.PX);
				}
			}

			Style gridPanelStyle = flowPanel.getElement().getStyle();
			int gridPanelWidth = 0;
			for (int width : columnWidthsPx) {
				gridPanelWidth += width;
			}
			gridPanelStyle.setWidth(gridPanelWidth, Unit.PX);

			if (alignment == Alignment.CENTER) {
				gridPanelStyle.setProperty("marginLeft", "auto");
				gridPanelStyle.setProperty("marginRight", "auto");
			} else if (alignment == Alignment.RIGHT) {
				gridPanelStyle.setProperty("marginLeft", "auto");
			}
			return flowPanel;
		}
	}
}