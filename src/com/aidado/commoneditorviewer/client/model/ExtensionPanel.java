package com.aidado.commoneditorviewer.client.model;

import java.util.HashMap;
import java.util.Map;

import com.aidado.common.client.StyleSetter;
import com.aidado.commoneditorviewer.client.icon.Border;
import com.aidado.commoneditorviewer.client.icon.BorderBundle;
import com.aidado.commoneditorviewer.client.icon.pattern.Pattern;
import com.aidado.commoneditorviewer.client.model.HasCssBorder.CssBorderType;
import com.aidado.commoneditorviewer.client.model.HasImageBorder.ImageBorderType;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Widget;


public class ExtensionPanel extends Panel {

	@Override
	protected void update() {
		updateImageDecoration();
		super.update();
	}

	// ### HasBackground ###

	protected Widget getHasBackgroundWidget() {
		return this;
	}

	private Pattern pattern;

	public Pattern getBackgroundPattern() {
		return pattern;
	}

	public void setBackgroundPattern(Pattern pattern) {
		this.pattern = pattern;
		Style backgroundStyle = getHasBackgroundWidget().getElement().getStyle();
		if (pattern != null) {
			backgroundStyle.setBackgroundImage("url(" + pattern.getImageResource().getSafeUri().asString() + ")");
			backgroundStyle.setProperty("backgroundRepeat", "repeat");
			if (!pattern.hasTransparency()) {
				setBackgroundColor(null);
			}
		} else {
			backgroundStyle.clearBackgroundImage();
			backgroundStyle.clearProperty("backgroundRepeat");
		}
	}

	public String getBackgroundColor() {
		return getHasBackgroundWidget().getElement().getStyle().getBackgroundColor();
	}

	public void setBackgroundColor(String backgroundColor) {
		if (backgroundColor != null) {
			getHasBackgroundWidget().getElement().getStyle().setBackgroundColor(backgroundColor);
			if (pattern != null && !pattern.hasTransparency()) {
				setBackgroundPattern(null);
			}
		} else {
			getHasBackgroundWidget().getElement().getStyle().clearBackgroundColor();
		}
	}

	// ### HasImageBorder ###

	protected Widget getHasImageBorderWidget() {
		return this;
	}

	private ImageBorderType imageBorderType;

	public ImageBorderType getImageBorderType() {
		return imageBorderType;
	}

	public void setImageBorderType(ImageBorderType imageBorderType) {
		if (imageBorderType == null) {
			setDecorationBundle(null);
		} else {
			setDecorationBundle(Border.valueOf(imageBorderType.name().toUpperCase()));
		}
		this.imageBorderType = imageBorderType;
	}

	private Map<DecorationImagePosition, BorderImagePanel> borderImageMap = new HashMap<DecorationImagePosition, BorderImagePanel>();

	private Border decorationBundle;

	private enum DecorationImagePosition {
		TOP_LEFT, TOP, TOP_RIGHT, LEFT, RIGHT, BOTTOM_LEFT, BOTTOM, BOTTOM_RIGHT;
	}

	protected void setDecorationBundle(Border decorationBundle) {
		clearImageDecoration();
		this.decorationBundle = decorationBundle;
		if (decorationBundle != null) {
			applyDecoration();
		} else {
			clearImageDecoration();
		}
	}

	private void applyDecoration() {
		clearCssBorder();
		clearShadow();
		setBorderRadius(0);
		BorderBundle imageBundle = decorationBundle.getBundle();
		addDecoratorImage(new BorderImagePanel(imageBundle.topLeft()), DecorationImagePosition.TOP_LEFT);
		addDecoratorImage(new BorderImagePanel(imageBundle.top()), DecorationImagePosition.TOP);
		addDecoratorImage(new BorderImagePanel(imageBundle.topRight()), DecorationImagePosition.TOP_RIGHT);
		addDecoratorImage(new BorderImagePanel(imageBundle.left()), DecorationImagePosition.LEFT);
		addDecoratorImage(new BorderImagePanel(imageBundle.right()), DecorationImagePosition.RIGHT);
		addDecoratorImage(new BorderImagePanel(imageBundle.bottomLeft()), DecorationImagePosition.BOTTOM_LEFT);
		addDecoratorImage(new BorderImagePanel(imageBundle.bottom()), DecorationImagePosition.BOTTOM);
		addDecoratorImage(new BorderImagePanel(imageBundle.bottomRight()), DecorationImagePosition.BOTTOM_RIGHT);
		requestUpdate();
	}

	private void clearImageDecoration() {
		for (BorderImagePanel borderImagePanel : borderImageMap.values()) {
			borderImagePanel.removeFromParent();
		}
		borderImageMap.clear();
		decorationBundle = null;
	}

	private void addDecoratorImage(BorderImagePanel imagePanel, DecorationImagePosition position) {
		borderImageMap.put(position, imagePanel);
		((AbsolutePanel) getHasImageBorderWidget()).insert(imagePanel, 0);
	}

	private class BorderImagePanel extends AbsolutePanel {
		public BorderImagePanel(ImageResource imageResource) {
			getElement().getStyle().setBackgroundImage("url(" + imageResource.getSafeUri().asString() + ")");
			getElement().getStyle().setProperty("backgroundRepeat", "repeat");
			setPixelSize(imageResource.getWidth(), imageResource.getHeight());
		}
	}

	private void updateImageDecoration() {
		if (decorationBundle != null) {
			int overlap = decorationBundle.getOverlap();
			int underlap = decorationBundle.getUnderlap();
			AbsolutePanel borderImagePanel = (AbsolutePanel) getHasImageBorderWidget();
			int width = borderImagePanel.getOffsetWidth();
			int height = borderImagePanel.getOffsetHeight();
			borderImagePanel.setWidgetPosition(borderImageMap.get(DecorationImagePosition.TOP_LEFT), -overlap, -overlap);
			borderImagePanel.setWidgetPosition(borderImageMap.get(DecorationImagePosition.TOP), underlap, -overlap);
			borderImagePanel.setWidgetPosition(borderImageMap.get(DecorationImagePosition.TOP_RIGHT), width - underlap, -overlap);
			borderImagePanel.setWidgetPosition(borderImageMap.get(DecorationImagePosition.LEFT), -overlap, underlap);
			borderImagePanel.setWidgetPosition(borderImageMap.get(DecorationImagePosition.RIGHT), width - underlap, underlap);
			borderImagePanel.setWidgetPosition(borderImageMap.get(DecorationImagePosition.BOTTOM_LEFT), -overlap, height - underlap);
			borderImagePanel.setWidgetPosition(borderImageMap.get(DecorationImagePosition.BOTTOM), underlap, height - underlap);
			borderImagePanel.setWidgetPosition(borderImageMap.get(DecorationImagePosition.BOTTOM_RIGHT), width - underlap, height - underlap);

			int schrink = 2 * underlap;
			borderImageMap.get(DecorationImagePosition.TOP).setWidth((width - schrink) + "px");
			borderImageMap.get(DecorationImagePosition.BOTTOM).setWidth((width - schrink) + "px");
			borderImageMap.get(DecorationImagePosition.LEFT).setHeight((height - schrink) + "px");
			borderImageMap.get(DecorationImagePosition.RIGHT).setHeight((height - schrink) + "px");
		}
	}

	// ### HasCssBorder ###

	protected Widget getHasCssBorderWidget() {
		return this;
	}

	private CssBorderType cssBorderType;
	private String borderColor = "#000000";
	private int borderSize = 1;

	public CssBorderType getCssBorderType() {
		return cssBorderType;
	}

	public void setCssBorderType(CssBorderType cssBorderType) {
		this.cssBorderType = cssBorderType;
		applyCssDecoration();
	}

	public int getBorderSize() {
		return borderSize;
	}

	public void setBorderSize(int borderSize) {
		this.borderSize = borderSize;
		applyCssDecoration();
		requestUpdate();
	}

	public String getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
		applyCssDecoration();
	}

	private void clearCssBorder() {
		Style style = getHasCssBorderWidget().getElement().getStyle();

		cssBorderType = null;
		borderColor = "#000000";
		borderSize = 1;

		style.clearBorderStyle();
		style.clearBorderColor();
		style.clearBorderWidth();
	}

	private void applyCssDecoration() {
		clearImageDecoration();
		Style style = getHasCssBorderWidget().getElement().getStyle();

		// clear decoration
		if (cssBorderType == null) {
			clearCssBorder();
		}

		// set decoration
		if (cssBorderType != null) {
			style.setProperty("borderStyle", cssBorderType.name().toLowerCase());
			style.setBorderColor(borderColor);
			style.setBorderWidth(borderSize, Unit.PX);
		}
	}

	// ### HasShadow ###

	protected Widget getHasShadowWidget() {
		return this;
	}

	private boolean shadowInset = false;
	private int shadowOffset = 0;
	private int shadowSize = 0;
	private String shadowColor = "#000000";

	public int getShadowSize() {
		return shadowSize;
	}

	public void setShadowSize(int shadowSize) {
		this.shadowSize = shadowSize;
		applyShadow();
	}

	public int getShadowOffset() {
		return shadowOffset;
	}

	public void setShadowOffset(int shadowOffset) {
		this.shadowOffset = shadowOffset;
		applyShadow();
	}

	public String getShadowColor() {
		return shadowColor;
	}

	public void setShadowColor(String shadowColor) {
		this.shadowColor = shadowColor;
		applyShadow();
	}

	public boolean isShadowInset() {
		return shadowInset;
	}

	public void setShadowInset(boolean shadowInset) {
		this.shadowInset = shadowInset;
		applyShadow();
	}

	private void clearShadow() {
		Style style = getHasShadowWidget().getElement().getStyle();
		shadowSize = 0;
		shadowInset = false;
		shadowOffset = 0;
		shadowColor = "#000000";
		style.clearProperty("boxShadow");
	}

	private void applyShadow() {
		clearImageDecoration();
		Style style = getHasShadowWidget().getElement().getStyle();

		if (shadowSize == 0) {
			clearShadow();
		}
		if (shadowSize != 0) {
			String boxShadowValue = new StringBuilder(shadowInset ? "inset " : "").append(shadowOffset).append("px ").append(shadowOffset).append("px ").append(shadowSize).append("px ").append(
			    shadowSize / 2).append("px ").append(shadowColor).toString();
			style.setProperty("boxShadow", boxShadowValue);
		}
	}

	// ### HasRotation ###

	protected Widget getHasRotationWidget() {
		return this;
	}

	private int rotation;

	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
		StyleSetter.set(getHasRotationWidget().getElement(), "transform", "rotate(" + rotation + "deg)", true, false);
	}

	// ### HasOpacity ###

	protected Widget getHasOpacityWidget() {
		return this;
	}

	private int opacity = 10;

	public int getOpacity() {
		return opacity;
	}

	public void setOpacity(int opacity) {
		this.opacity = opacity;
		getHasOpacityWidget().getElement().getStyle().setOpacity((double) opacity / 10);
	}

	// ### HasBorderRadius

	protected Widget getHasBorderRadiusWidget() {
		return this;
	}

	private int borderRadius = 0;

	public int getBorderRadius() {
		return borderRadius;
	}

	public void setBorderRadius(int borderRadius) {
		this.borderRadius = borderRadius;
		getHasBorderRadiusWidget().getElement().getStyle().setProperty("borderRadius", borderRadius + "px");
	}
}