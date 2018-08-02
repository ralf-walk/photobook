package com.aidado.editor.client.dialog.property;

import com.aidado.common.client.widget.TitleComposite;
import com.aidado.commoneditorviewer.client.model.Model;
import com.aidado.commoneditorviewer.client.model.Panel;
import com.aidado.editor.client.event.EventManager;
import com.aidado.editor.client.event.PanelUpdatedEvent;
import com.aidado.editor.client.event.PanelUpdatedEvent.PanelUpdatedHandler;
import com.aidado.editor.client.locale.MessageBundle;

public abstract class CompositeProperty<T extends Model> extends TitleComposite {

	private ActionContainer ac = new ActionContainer(this);
	private T lastUpdatedModel;
	private Panel lastUpdatedPanel;

	protected static final MessageBundle EM = MessageBundle.INSTANCE;

	public CompositeProperty() {
		EventManager.get().addHandler(PanelUpdatedEvent.TYPE, ac);
	}

	public void panelUpdated() {
		EventManager.get().firePanelUpdatedEvent(lastUpdatedPanel);
	}

	public T getModel() {
		return lastUpdatedModel;
	}

	abstract protected Model getShowForModel(Panel panel);

	public void showFor(T panel) {
	}

	private class ActionContainer implements PanelUpdatedHandler {

		private final CompositeProperty<T> compositePropertie;

		public ActionContainer(CompositeProperty<T> compositePropertie) {
			this.compositePropertie = compositePropertie;
		}

		@SuppressWarnings("unchecked")
		@Override
		public void panelUpdated(Panel clickedPanel) {
			T model = (T) getShowForModel(clickedPanel);
			if (model != null) {
				lastUpdatedModel = model;
				lastUpdatedPanel = clickedPanel;
				compositePropertie.setVisible(true);
				compositePropertie.showFor(model);
			} else {
				lastUpdatedModel = null;
				lastUpdatedPanel = null;
				compositePropertie.setVisible(false);
			}
		}
	}
}
