package com.aidado.editor.client.model.dnd;

import com.aidado.commoneditorviewer.client.model.BaseRootPanel;
import com.aidado.commoneditorviewer.client.model.Panel;
import com.aidado.editor.client.Accessor;
import com.aidado.editor.client.EditorPhotobook;
import com.aidado.editor.client.icon.Icons;
import com.aidado.editor.client.model.EditorPagePanel;
import com.aidado.editor.client.model.EditorRootPanel;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class CarrierDragPanel extends CarrierPanel {

	private static final Icons EI = Icons.INSTANCE;
	private final ActionContainer ac = new ActionContainer();
	private SimplePanel arrowPanel = new SimplePanel();

	public CarrierDragPanel(CarrierLayer carrierLayer) {
		super(carrierLayer);
		addDomHandler(ac, MouseDownEvent.getType());
		addDomHandler(ac, MouseUpEvent.getType());
		addDomHandler(ac, MouseMoveEvent.getType());

		// adding arrow panel
		Style arrowPanelStyle = arrowPanel.getElement().getStyle();
		arrowPanelStyle.setOpacity(0.8);
		arrowPanelStyle.setBackgroundImage("url(" + EI.dragArrowLeft().getSafeUri().asString() + "),url(" + EI.dragArrowRight().getSafeUri().asString() + "),url("
		    + EI.dragArrowUp().getSafeUri().asString() + "),url(" + EI.dragArrowDown().getSafeUri().asString() + ")");
		arrowPanelStyle.setProperty("backgroundPosition", "left center, right center, center top, center bottom");
		arrowPanelStyle.setProperty("backgroundRepeat", "no-repeat");
		add(arrowPanel);
	}

	@Override
	protected void update() {
		super.update();
		arrowPanel.setPixelSize(getWidth() + 30, getHeight() + 30);
		setWidgetPosition(arrowPanel, -15, -15);
	}

	public void onDrop(Widget w) {
		((CarrierAwarePanel) panel).onDrop(w);
	}

	protected void onDragEnd(boolean clicked) {
		if (clicked) {
			carrierLayer.initActivateCarrier((CarrierAwarePanel) getPanel());
		}
		remove();
	}

	public boolean isDropPossible(Widget w, boolean draggingStarted) {
		return ((CarrierAwarePanel) panel).isDropPossible(w, draggingStarted);
	}

	private class ActionContainer extends MouseHandler {

		private final CarrierDragPanel carrierPanel = CarrierDragPanel.this;
		private final HighlightPanel highlightPanel = new HighlightPanel();

		private boolean dragPossible = false;
		private boolean dragging = false;
		private boolean inPagePanel = false;
		private Widget dropTarget;

		private int offsetX;
		private int offsetY;

		@Override
		public void onMouseDown(MouseDownEvent event) {
			super.onMouseDown(event);
			offsetX = event.getRelativeX(getElement());
			offsetY = event.getRelativeY(getElement());
			DOM.setCapture(getElement());
			dragPossible = true;
			carrierPanel.setZIndex(970);
			RootPanel.get().getElement().getStyle().setCursor(Cursor.MOVE);
		}

		@Override
		public void onMouseMove(MouseMoveEvent event) {
			super.onMouseMove(event);
			if (dragPossible) {
				dragging = true;

				EditorPhotobook photobook = Accessor.getPhotobook();
				EditorPagePanel pagePanel = (EditorPagePanel)photobook.getCurrentPage();
				Element pageElement = pagePanel.getElement();

				int newX = event.getRelativeX(pageElement) - offsetX;
				int newY = event.getRelativeY(pageElement) - offsetY;

				// keep panel in page
				if (!carrierPanel.isDropPossible(photobook.getRootPanel(), dragging)) {
					if (!inPagePanel) {
						inPagePanel = newX >= 0 && newY >= 0 && newX + carrierPanel.getOffsetWidth() <= pagePanel.getOffsetWidth() && newY + carrierPanel.getOffsetHeight() <= pagePanel.getOffsetHeight();
					}
					if (inPagePanel) {
						newX = Math.max(0, Math.min(newX, pagePanel.getOffsetWidth() - carrierPanel.getOffsetWidth()));
						newY = Math.max(0, Math.min(newY, pagePanel.getOffsetHeight() - carrierPanel.getOffsetHeight()));
					}
				}
				carrierPanel.setLeft(newX);
				carrierPanel.setTop(newY);
				RootPanel.get().getElement().getStyle().setCursor(Cursor.MOVE);

				// Highlight dropTarget
				Panel newDropTarget = getHighestPossibleDropTarget();
				if (newDropTarget != null && newDropTarget != carrierPanel) {
					if (newDropTarget != dropTarget) {
						if (newDropTarget instanceof EditorRootPanel || newDropTarget instanceof EditorPagePanel) {
							newDropTarget.insert(highlightPanel, 0, 0, 0);
							highlightPanel.setPixelSize(newDropTarget.getOffsetWidth(), newDropTarget.getOffsetHeight());
						} else {
							newDropTarget.add(highlightPanel, 0, 0);
							highlightPanel.setPixelSize(newDropTarget.getOffsetWidth(), newDropTarget.getOffsetHeight());
						}
					}
				} else {
					highlightPanel.removeFromParent();
				}
				dropTarget = newDropTarget;
			}
		}

		@Override
		public void onMouseUp(MouseUpEvent event) {
			super.onMouseUp(event);
			DOM.releaseCapture(carrierPanel.getElement());
			RootPanel.get().getElement().getStyle().clearCursor();
			dragPossible = false;
			highlightPanel.removeFromParent();
			carrierPanel.clearZIndex();
			inPagePanel = false;
			offsetX = 0;
			offsetY = 0;

			dropTarget = getHighestPossibleDropTarget();
			if (dropTarget != null) {
				if (dragging) {
					carrierPanel.onDrop(dropTarget);
					carrierPanel.onDragEnd(false);
				} else {
					carrierPanel.onDragEnd(true);
				}
			} else {
				carrierPanel.getPanel().removeFromParent();
				carrierPanel.onDragEnd(false);
			}
			dropTarget = null;
			dragging = false;
		}

		public Panel getHighestPossibleDropTarget() {
			BaseRootPanel rootPanel = Accessor.getPhotobook().getRootPanel();
			Panel dropTargetResult = carrierPanel.isDropPossible(rootPanel, dragging) ? rootPanel : null;

			EditorPagePanel page = (EditorPagePanel)Accessor.getPhotobook().getCurrentPage();
			if (carrierPanel.isDropPossible(page, dragging) && page.isPanelIntersecting(page, (Widget) carrierPanel)) {
				dropTargetResult = page;
			}
			for (Panel child : ((EditorPagePanel) page).getChildPanels()) {
				if (carrierPanel.isDropPossible(child, dragging)) {
					if (page.isPanelIntersecting(child, (Widget) carrierPanel)) {
						if (dropTargetResult == null || dropTargetResult == page || page.getWidgetIndex(dropTargetResult) < page.getWidgetIndex(child)) {
							dropTargetResult = child;
						}
					}
				}
			}
			return dropTargetResult;
		}

		private class HighlightPanel extends Panel {

			public HighlightPanel() {
				Style style = getElement().getStyle();
				style.setPosition(Position.ABSOLUTE);
				style.setOpacity(0.5);
				style.setProperty("filter", "alpha(opacity=50)");
				style.setBackgroundColor("#ffffff");
			}
		}
	}
}