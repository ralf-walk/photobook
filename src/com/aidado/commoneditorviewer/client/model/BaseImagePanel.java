package com.aidado.commoneditorviewer.client.model;

import com.aidado.common.client.widget.RatioKeepingImage;
import com.aidado.common.client.widget.RatioKeepingImage.Mode;
import com.aidado.commoneditorviewer.client.model.Model.Item;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class BaseImagePanel extends ExtensionPanel implements Item, HasCssBorder, HasImageBorder, HasShadow, HasBorderRadius, HasRotation, HasOpacity, HasTitle {

	protected final RatioKeepingImage image = new RatioKeepingImage(Mode.ENLARGE_TO_PARENT);

	private final SimplePanel staticInnerContainer = new SimplePanel();
	private final Panel borderPanel = new Panel();
	private final Panel containerPanel = new Panel();

	private boolean slideshowEnabled;

	public BaseImagePanel() {
		image.addLoadHandler(new LoadHandler() {

			@Override
			public void onLoad(LoadEvent event) {
				requestUpdate();
			}
		});
		Style containerPanelStyle = containerPanel.getElement().getStyle();
		containerPanelStyle.setOverflow(Overflow.VISIBLE);
		containerPanelStyle.setWidth(100, Unit.PCT);
		containerPanelStyle.setHeight(100, Unit.PCT);

		Style staticInnerStyle = staticInnerContainer.getElement().getStyle();
		staticInnerStyle.setPosition(Position.STATIC);
		staticInnerStyle.setOverflow(Overflow.HIDDEN);
		staticInnerStyle.setWidth(100, Unit.PCT);
		staticInnerStyle.setHeight(100, Unit.PCT);

		Style borderPanelStyle = borderPanel.getElement().getStyle();
		borderPanelStyle.setOverflow(Overflow.VISIBLE);
		borderPanelStyle.setWidth(100, Unit.PCT);
		borderPanelStyle.setHeight(100, Unit.PCT);

		Style imageStyle = image.getElement().getStyle();
		imageStyle.setPosition(Position.STATIC);
		staticInnerContainer.add(image);

		containerPanel.add(staticInnerContainer);
		containerPanel.add(borderPanel);
		add(containerPanel);
	}

	@Override
	protected void update() {
		super.update();
		image.update();
	}

	public String getImageUrl() {
		return image.getUrl();
	}

	public void setImageUrl(String url) {
		image.setUrl(url);
		requestUpdate();
	}

	public boolean isSlideshowEnabled() {
		return slideshowEnabled;
	}

	public void setSlideshowEnabled(boolean slideshowEnabled) {
		this.slideshowEnabled = slideshowEnabled;
	}

	@Override
	public void setBorderRadius(int borderRadius) {
		super.setBorderRadius(borderRadius);
		String borderRadiusValue = new StringBuilder().append(borderRadius).append("px").toString();
		staticInnerContainer.getElement().getStyle().setProperty("borderRadius", borderRadiusValue);
		image.getElement().getStyle().setProperty("borderRadius", borderRadiusValue);
		borderPanel.getElement().getStyle().setProperty("borderRadius", borderRadiusValue);
	}

	@Override
	public Widget getHasRotationWidget() {
		return containerPanel;
	}

	@Override
	public Widget getHasCssBorderWidget() {
		return borderPanel;
	}

	@Override
	public Widget getHasShadowWidget() {
		return borderPanel;
	}

	@Override
	public Widget getHasImageBorderWidget() {
		return borderPanel;
	}
}