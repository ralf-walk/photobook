package com.aidado.editor.client.model.dnd;

import com.aidado.common.client.widget.Button;
import com.aidado.common.client.widget.LayoutBuilder;
import com.aidado.common.client.widget.Button.ButtonListener;
import com.aidado.editor.client.Accessor;
import com.aidado.editor.client.event.EventManager;
import com.aidado.editor.client.icon.Icons;
import com.aidado.editor.client.locale.MessageBundle;
import com.aidado.editor.client.model.EditorPagePanel;
import com.aidado.editor.client.model.dnd.ResizeCorner.CornerType;
import com.google.gwt.user.client.ui.FlowPanel;

public class CarrierActivePanel extends CarrierDragPanel {

	private static final Icons EI = Icons.INSTANCE;
	private static final MessageBundle EM = MessageBundle.INSTANCE;

	private final ActionContainer ac = new ActionContainer();
	private final Button removeButton = Button.createPushButton(ac, EM.removePanel(), 22, 22, EI.panelDelete());
	private final Button bringToFrontButton = Button.createPushButton(ac, EM.bringToFront(), 22, 22, EI.panelBringToFront());
	private final Button sendToBackButton = Button.createPushButton(ac, EM.sendToBack(), 22, 22, EI.panelSendToBack());
	private final FlowPanel controlsPanel = LayoutBuilder.createCellPanel(22, 22, 22).put(bringToFrontButton).put(sendToBackButton).put(removeButton).build();
	private final ResizeCorner[] resizeCorners;

	public CarrierActivePanel(CarrierLayer carrierLayer) {
		super(carrierLayer);
		getElement().getStyle().setProperty("border", "1px dashed #000000");
		resizeCorners = new ResizeCorner[] { new ResizeCorner(this, CornerType.TOP_LEFT), new ResizeCorner(this, CornerType.TOP_RIGHT), new ResizeCorner(this, CornerType.BOTTOM_LEFT),
		    new ResizeCorner(this, CornerType.BOTTOM_RIGHT) };
		for (ResizeCorner resizeCorner : resizeCorners) {
			add(resizeCorner);
		}
		add(controlsPanel);
	}

	@Override
	protected void update() {
		super.update();
		int resizeCornerHalfSize = EI.dragCorner().getWidth() / 2;
		setWidgetPosition(resizeCorners[0], -resizeCornerHalfSize, -resizeCornerHalfSize);
		setWidgetPosition(resizeCorners[1], getOffsetWidth() - resizeCornerHalfSize, -resizeCornerHalfSize);
		setWidgetPosition(resizeCorners[2], -6, getOffsetHeight() - resizeCornerHalfSize);
		setWidgetPosition(resizeCorners[3], getOffsetWidth() - resizeCornerHalfSize, getOffsetHeight() - resizeCornerHalfSize);
		int left = getOffsetWidth() - 33;
		setWidgetPosition(controlsPanel, left, 7);
	}

	@Override
	protected void onDragEnd(boolean clicked) {
		if (clicked) {
			((CarrierAwarePanel) panel).onClick();
		}
	}

	private class ActionContainer implements ButtonListener {

		@Override
		public void onClick(Button button, boolean pushed) {
			if (button == removeButton) {
				getPanel().removeFromParent();
				remove();
				EventManager.get().firePanelUpdatedEvent(Accessor.getPhotobook().getCurrentPage());
			} else {
				EditorPagePanel page = (EditorPagePanel)Accessor.getPhotobook().getCurrentPage();
				int currentIndex = page.getWidgetIndex(getPanel());
				if (button == bringToFrontButton) {
					for (int i = currentIndex + 1; i < page.getWidgetCount(); i++) {
						if (page.isPanelIntersecting(getPanel(), page.getWidget(i))) {
							page.insert(page.getWidget(i), currentIndex);
							break;
						}
					}
				} else if (button == sendToBackButton) {
					for (int i = currentIndex - 1; i >= 0; i--) {
						if (page.isPanelIntersecting(getPanel(), page.getWidget(i))) {
							page.insert(getPanel(), i);
							break;
						}
					}
				}
			}
		}
	}
}