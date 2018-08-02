package com.aidado.editor.client;

import java.util.List;

import com.aidado.common.client.InfoDialog;
import com.aidado.common.client.InfoDialog.InfoDialogListener;
import com.aidado.common.client.widget.Button;
import com.aidado.common.client.widget.LayoutBuilder;
import com.aidado.common.client.widget.Button.ButtonListener;
import com.aidado.common.client.widget.LayoutBuilder.Mode;
import com.aidado.commoneditorviewer.client.PanelFactoryManager;
import com.aidado.commoneditorviewer.client.Photobook;
import com.aidado.commoneditorviewer.client.Photobook.PageSwapAnimation.SwapDirection;
import com.aidado.commoneditorviewer.client.model.BasePagePanel;
import com.aidado.editor.client.event.EventManager;
import com.aidado.editor.client.icon.Icons;
import com.aidado.editor.client.locale.MessageBundle;
import com.aidado.editor.client.model.dnd.CarrierLayer;
import com.google.gwt.user.client.ui.FlowPanel;

public class EditorPhotobook extends Photobook {

	private static final Icons EI = Icons.INSTANCE;
	private static final MessageBundle EM = MessageBundle.INSTANCE;

	private final int maxPages = 99;
	private final ActionContainer ac = new ActionContainer();
	private final CarrierLayer carrierLayer = new CarrierLayer();

	private final Button addButton = Button.createPushButton(ac, EM.addPage(), 32, 32, EI.pageAdd());
	private final Button deleteButton = Button.createPushButton(ac, EM.deletePage(), 32, 32, EI.pageDelete());

	public EditorPhotobook(String photobookJson) {
		super(photobookJson);		
		FlowPanel menuPanel = LayoutBuilder.createCellPanel(40,32).put(addButton).put(deleteButton).build();
		menuPanel.getElement().getStyle().setOpacity(0.7);
		add(menuPanel);
		setWidgetPosition(menuPanel, 928, 0);
		carrierLayer.setPixelSize(1000, 740);
		insert(carrierLayer, 0);
		setWidgetPosition(carrierLayer, 0, 0);
	}
	
	@Override
  public void init(String photobookJson) {
		super.init(photobookJson);
		if (rootPanel == null) {
			rootPanel = PanelFactoryManager.getFactory().createRootPanel();
			rootPanel.getPagePanels().add(PanelFactoryManager.getFactory().createPagePanel());
		}
		showPage(getRootPanel().getPagePanels().get(0));
		EventManager.get().firePanelUpdatedEvent(getCurrentPage());
  }

	public String getPhotobookJson() {
		return PhotobookSaver.save(rootPanel);
	}

	public CarrierLayer getCarrierLayer() {
		return carrierLayer;
	}

	private void createNewPage() {
		List<BasePagePanel> pagePanels = rootPanel.getPagePanels();
		if (pagePanels.size() >= maxPages) {
			new InfoDialog(LayoutBuilder.createBox(MessageBundle.INSTANCE.toManyPages(), Mode.WARNING, 400, 10));
		} else {
			int index = pagePanels.indexOf(getCurrentPage());
			BasePagePanel newPage = PanelFactoryManager.getFactory().createPagePanel();
			pagePanels.add(index + 1, newPage);
			swapPage(getCurrentPage(), SwapDirection.LEFT);
			showPage(newPage);
		}
	}

	@Override
	protected void showPage(BasePagePanel page) {
		super.showPage(page);
		EventManager.get().firePanelUpdatedEvent(getCurrentPage());
	}

	private void deletePage() {
		List<BasePagePanel> pagePanels = rootPanel.getPagePanels();
		int index = pagePanels.indexOf(getCurrentPage());
		int pageCount = rootPanel.getPagePanels().size();

		// swap and remove old page
		SwapDirection swapDirection = index < pageCount - 1 ? SwapDirection.LEFT : SwapDirection.RIGHT;
		swapPage(getCurrentPage(), swapDirection);
		pagePanels.remove(getCurrentPage());
		pageCount--;

		// show new page
		if (pageCount == 0) {
			BasePagePanel newPage = PanelFactoryManager.getFactory().createPagePanel();
			pagePanels.add(newPage);
			showPage(newPage);			
		} else if (swapDirection == SwapDirection.LEFT) {
			showPage(pagePanels.get(index));			
		} else {
			showPage(pagePanels.get(--index));						
		}
	}

	private class ActionContainer implements ButtonListener {

		@Override
		public void onClick(Button button, boolean pushed) {
			if (button == addButton) {
				createNewPage();
			} else if (button == deleteButton) {
				new InfoDialog(LayoutBuilder.createBox(EM.pageDeletePrompt(), Mode.WARNING, 400, 10), true, new InfoDialogListener() {

					@Override
					public void onOkClicked() {
						deletePage();
					}

					@Override
					public void onCancelClicked() {
					}
				});
			}
		}
	}
}