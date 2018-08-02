package com.aidado.editor.client;

import java.util.List;

import com.aidado.commoneditorviewer.client.model.BaseImagePanel;
import com.aidado.commoneditorviewer.client.model.BasePagePanel;
import com.aidado.commoneditorviewer.client.model.BaseRootPanel;
import com.aidado.commoneditorviewer.client.model.BaseTextPanel;
import com.aidado.commoneditorviewer.client.model.HasBackground;
import com.aidado.commoneditorviewer.client.model.HasBorderRadius;
import com.aidado.commoneditorviewer.client.model.HasCssBorder;
import com.aidado.commoneditorviewer.client.model.HasImageBorder;
import com.aidado.commoneditorviewer.client.model.HasOpacity;
import com.aidado.commoneditorviewer.client.model.HasPanel;
import com.aidado.commoneditorviewer.client.model.HasRotation;
import com.aidado.commoneditorviewer.client.model.HasShadow;
import com.aidado.commoneditorviewer.client.model.HasTitle;
import com.aidado.commoneditorviewer.client.model.HasCssBorder.CssBorderType;
import com.aidado.commoneditorviewer.client.model.HasImageBorder.ImageBorderType;
import com.aidado.commoneditorviewer.client.model.Model.Item;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;


public class PhotobookSaver {

	public static String save(Item item) {
		JSONObject targetObject = new JSONObject();
		if (item instanceof BaseRootPanel) {
			BaseRootPanel panel = (BaseRootPanel) item;
			setString(targetObject, "type", "rootpanel");
			setItemArray(targetObject, "pages", panel.getPagePanels());
		} else if (item instanceof BaseTextPanel) {
			BaseTextPanel panel = (BaseTextPanel) item;
			setString(targetObject, "type", "textpanel");
			setString(targetObject, "htmlText", panel.getHtmlText());
		} else if (item instanceof BaseImagePanel) {
			BaseImagePanel panel = (BaseImagePanel) item;
			setString(targetObject, "type", "imagepanel");
			setString(targetObject, "imageUrl", panel.getImageUrl());
			setBoolean(targetObject, "slideshowEnabled", panel.isSlideshowEnabled());
		} else if (item instanceof BasePagePanel) {
			BasePagePanel panel = (BasePagePanel) item;
			setString(targetObject, "type", "pagepanel");
			setString(targetObject, "pageTitle", panel.getTitle());
			setItemArray(targetObject, "children", panel.getChildPanels());
		}
		if (item instanceof HasBackground) {
			HasBackground background = (HasBackground) item;
			setString(targetObject, "backgroundColor", background.getBackgroundColor());
			setEnum(targetObject, "backgroundPattern", background.getBackgroundPattern());
		}
		if (item instanceof HasCssBorder) {
			HasCssBorder hasCssBorder = (HasCssBorder) item;
			CssBorderType cssBorderType = hasCssBorder.getCssBorderType();
			setEnum(targetObject, "cssBorderType", cssBorderType);
			setInteger(targetObject, "borderSize", hasCssBorder.getBorderSize());
			setString(targetObject, "borderColor", hasCssBorder.getBorderColor());
		}
		if (item instanceof HasBorderRadius) {
			HasBorderRadius hasBorderRadius = (HasBorderRadius) item;
			setInteger(targetObject, "borderRadius", hasBorderRadius.getBorderRadius());
		}
		if (item instanceof HasShadow) {
			HasShadow hasShadow = (HasShadow) item;
			setInteger(targetObject, "shadowSize", hasShadow.getShadowSize());
			setBoolean(targetObject, "shadowInset", hasShadow.isShadowInset());
			setInteger(targetObject, "shadowOffset", hasShadow.getShadowOffset());
			setString(targetObject, "shadowColor", hasShadow.getShadowColor());
		}
		if (item instanceof HasImageBorder) {
			HasImageBorder hasImageBorder = (HasImageBorder) item;
			ImageBorderType imageBorderType = hasImageBorder.getImageBorderType();
			setEnum(targetObject, "imageBorderType", imageBorderType);
		}
		if (item instanceof HasRotation) {
			setInteger(targetObject, "rotation", ((HasRotation)item).getRotation());
		}
		if (item instanceof HasOpacity) {
			setInteger(targetObject, "opacity", ((HasOpacity)item).getOpacity());
		}
		if (item instanceof HasTitle) {
			HasTitle hasTitle = (HasTitle) item;
			setString(targetObject, "title", hasTitle.getTitle());
		}
		if (item instanceof HasPanel) {
			HasPanel panel = (HasPanel) item;
			setInteger(targetObject, "left", panel.getLeft());
			setInteger(targetObject, "top", panel.getTop());
			setInteger(targetObject, "width", panel.getWidth());
			setInteger(targetObject, "height", panel.getHeight());
		}
		return targetObject.toString();
	}

	private static void setString(JSONObject object, String name, String value) {
		if (value != null) {
			object.put(name, new JSONString(value));
		}
	}

	private static void setInteger(JSONObject object, String name, int value) {
		object.put(name, new JSONNumber(value));
	}

	private static void setBoolean(JSONObject object, String name, boolean value) {
		object.put(name, JSONBoolean.getInstance(value));
	}

	private static void setItemArray(JSONObject object, String name, List<? extends Item> list) {
		if (list != null) {
			JSONArray array = new JSONArray();
			int i = 0;
			for (Item item : list) {
				array.set(i++, JSONParser.parseStrict(save(item)));
			}
			object.put(name, array);
		}
	}

	private static void setEnum(JSONObject object, String name, Enum<?> e) {
		if (e != null) {
			object.put(name, new JSONString(e.name().toUpperCase()));
		}
	}
}