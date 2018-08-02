package com.aidado.commoneditorviewer.client;

import java.util.ArrayList;
import java.util.List;

import com.aidado.commoneditorviewer.client.PanelFactoryManager.PanelFactory;
import com.aidado.commoneditorviewer.client.icon.pattern.Pattern;
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
import com.aidado.commoneditorviewer.client.model.ImageBlobInfo;
import com.aidado.commoneditorviewer.client.model.Panel;
import com.aidado.commoneditorviewer.client.model.HasCssBorder.CssBorderType;
import com.aidado.commoneditorviewer.client.model.HasImageBorder.ImageBorderType;
import com.aidado.commoneditorviewer.client.model.Model.Item;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;

public class PhotobookLoader {

	@SuppressWarnings("unchecked")
	public static <T extends Item> List<T> loadItemArray(String jsonArray) {
		List<T> itemList = new ArrayList<T>();
		JSONArray pageArray = JSONParser.parseStrict(jsonArray).isArray();
		for (int i = 0; i < pageArray.size(); i++) {
			itemList.add((T) loadItem(pageArray.get(i).isObject()));
		}
		return itemList;
	}

	@SuppressWarnings("unchecked")
	public static <T extends Item> T loadItem(String json) {
		return (T) loadItem(JSONParser.parseStrict(json).isObject());
	}

	private static Item loadItem(JSONObject itemJson) {
		Item item = null;
		if (itemJson != null) {
			PanelFactory factory = PanelFactoryManager.getFactory();
			String type = getString(itemJson, "type");
			if (type.equals("rootpanel")) {
				BaseRootPanel rootPanel = factory.createRootPanel();
				JSONArray pages = itemJson.get("pages").isArray();
				if (pages != null) {
					for (int i = 0; i < pages.size(); i++) {
						JSONObject pagePanelObject = pages.get(i).isObject();
						BasePagePanel pagePanel = (BasePagePanel) loadItem(pagePanelObject);
						if (pagePanel != null) {
							rootPanel.getPagePanels().add(pagePanel);
						}
					}
				}
				item = rootPanel;
			} else if (type.equals("textpanel")) {
				BaseTextPanel textPanel = factory.createTextPanel();
				textPanel.setHtmlText(getString(itemJson, "htmlText"));
				item = textPanel;
			} else if (type.equals("imagepanel")) {
				BaseImagePanel imagePanel = factory.createImagePanel();
				imagePanel.setImageUrl(getString(itemJson, "imageUrl"));
				imagePanel.setSlideshowEnabled(getBoolean(itemJson, "slideshowEnabled"));
				item = imagePanel;
			} else if (type.equals("pagepanel")) {
				BasePagePanel pagePanel = factory.createPagePanel();
				pagePanel.setTitle(getString(itemJson, "pageTitle"));
				JSONArray children = itemJson.get("children").isArray();
				if (children != null) {
					for (int i = 0; i < children.size(); i++) {
						JSONObject childPanelObject = children.get(i).isObject();
						Panel childPanel = (Panel) loadItem(childPanelObject);
						if (childPanel != null) {
							pagePanel.addChildPanel(childPanel);
						}
					}
				}
				item = pagePanel;
			} else if (type.equals("bloblink")) {
				String key = getString(itemJson, "key");
				String fileName = getString(itemJson, "fileName");
				String url = getString(itemJson, "url");
				int size = getInteger(itemJson, "size");
				item = new ImageBlobInfo(key, fileName, url, size);
			}
			if (item instanceof HasBackground) {
				HasBackground background = (HasBackground) item;
				background.setBackgroundColor(getString(itemJson, "backgroundColor"));
				background.setBackgroundPattern(getEnum(itemJson, "backgroundPattern", Pattern.class));
			}
			if (item instanceof HasCssBorder) {
				HasCssBorder hasCssBorder = (HasCssBorder) item;
				hasCssBorder.setCssBorderType(getEnum(itemJson, "cssBorderType", CssBorderType.class));
				hasCssBorder.setBorderSize(getInteger(itemJson, "borderSize"));
				hasCssBorder.setBorderColor(getString(itemJson, "borderColor"));
			}
			if (item instanceof HasBorderRadius) {
				HasBorderRadius hasBorderRadius = (HasBorderRadius) item;
				hasBorderRadius.setBorderRadius(getInteger(itemJson, "borderRadius"));
			}
			if (item instanceof HasShadow) {
				HasShadow hasShadow = (HasShadow) item;
				hasShadow.setShadowSize(getInteger(itemJson, "shadowSize"));
				hasShadow.setShadowInset(getBoolean(itemJson, "shadowInset"));
				hasShadow.setShadowOffset(getInteger(itemJson, "shadowOffset"));
				hasShadow.setShadowColor(getString(itemJson, "shadowColor"));
			}
			if (item instanceof HasImageBorder) {
				HasImageBorder hasImageBorder = (HasImageBorder) item;
				hasImageBorder.setImageBorderType(getEnum(itemJson, "imageBorderType", ImageBorderType.class));
			}
			if (item instanceof HasRotation) {
				((HasRotation) item).setRotation(getInteger(itemJson, "rotation"));
			}
			if (item instanceof HasOpacity) {
				((HasOpacity) item).setOpacity(getInteger(itemJson, "opacity"));
			}
			if (item instanceof HasTitle) {
				HasTitle hasTitle = (HasTitle) item;
				hasTitle.setTitle(getString(itemJson, "title"));
			}
			if (item instanceof HasPanel) {
				HasPanel panel = (HasPanel) item;
				panel.setLeft(getInteger(itemJson, "left"));
				panel.setTop(getInteger(itemJson, "top"));
				panel.setWidth(getInteger(itemJson, "width"));
				panel.setHeight(getInteger(itemJson, "height"));
			}
		}
		return item;
	}

	private static String getString(JSONObject object, String name) {
		return object.get(name) != null ? object.get(name).isString().stringValue() : null;
	}

	private static int getInteger(JSONObject object, String name) {
		return object.get(name) != null ? (int) object.get(name).isNumber().doubleValue() : 0;
	}

	private static Boolean getBoolean(JSONObject object, String name) {
		return object.get(name) != null ? object.get(name).isBoolean().booleanValue() : false;
	}

	private static <T extends Enum<T>> T getEnum(JSONObject object, String name, Class<T> enumType) {
		String value = getString(object, name);
		try {
			return value != null ? Enum.valueOf(enumType, value.toUpperCase()) : null;
		} catch (IllegalArgumentException e) {
			return null;
		}
	}
}