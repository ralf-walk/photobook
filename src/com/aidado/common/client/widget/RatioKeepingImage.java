package com.aidado.common.client.widget;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class RatioKeepingImage extends Image {

	public enum Mode {
		SHRINK_IN_PARENT, ENLARGE_TO_PARENT;
	}

	private Mode mode;

	public RatioKeepingImage(String url, Mode mode) {
		super(url);
		addLoadHandler(new LoadHandler() {
      
      @Override
      public void onLoad(LoadEvent event) {
        update();
      }
    });
		this.mode = mode;
	}

	public RatioKeepingImage(Mode mode) {
		this.mode = mode;
	}

	@Override
	protected void onLoad() {
		super.onLoad();
	}

	@Override
	public void setUrl(SafeUri url) {
		super.setUrl(url);
		update();
	}

	@Override
	public void setUrl(String url) {
		setUrlAndVisibleRect(url, 0, 0, getWidth(), getHeight());
		super.setUrl(url);
		update();
	}

	public void update() {
		if (isAttached() && getWidth() > 0 && getHeight() > 0) {
			Style imageStyle = getElement().getStyle();
			imageStyle.clearMarginLeft();
			imageStyle.clearMarginTop();

			Widget parent = getParent();
			int borderSize = DOM.getIntStyleAttribute(parent.getElement(), "borderWidth");
			int parentWidth = parent.getOffsetWidth() - 2 * borderSize;
			int parentHeigth = parent.getOffsetHeight() - 2 * borderSize;

			float panelWidthToHeightRatio = (float) parentWidth / (float) parentHeigth;
			float imageWidthToHeightRatio = (float) getWidth() / getHeight();

			if ((imageWidthToHeightRatio >= panelWidthToHeightRatio && mode == Mode.ENLARGE_TO_PARENT) || (imageWidthToHeightRatio < panelWidthToHeightRatio && mode == Mode.SHRINK_IN_PARENT)) {
				// set height and update width
				int newWidth = Math.round((parentHeigth * imageWidthToHeightRatio));
				setPixelSize(newWidth, parentHeigth);
				imageStyle.setMarginLeft(-((newWidth - parentWidth) / 2) - borderSize, Unit.PX);
				imageStyle.setMarginTop(-borderSize, Unit.PX);
			} else {
				int newHeight = Math.round((parentWidth * (1 / imageWidthToHeightRatio)));
				setPixelSize(parentWidth, newHeight);
				imageStyle.setMarginTop(-((newHeight - parentHeigth) / 2) - borderSize, Unit.PX);
				imageStyle.setMarginLeft(-borderSize, Unit.PX);
			}
		}
	}
}