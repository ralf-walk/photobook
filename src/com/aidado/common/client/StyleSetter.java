package com.aidado.common.client;

import java.util.Arrays;

import com.google.gwt.dom.client.Element;

public class StyleSetter {

  public static void set(Element element, String property, String value, boolean prefixProperty, boolean prefixValue) {
    remove(element, property);
    StringBuilder styleBuilder = new StringBuilder(element.getAttribute("style"));
    styleBuilder.append(property).append(":").append(value).append(";");

    for (String prefix : Arrays.asList(new String[] { "moz", "webkit", "ms", "o" })) {
      if (prefixProperty) {
        styleBuilder.append("-").append(prefix).append("-");
      }
      styleBuilder.append(property).append(":");
      if (prefixValue) {
        styleBuilder.append("-").append(prefix).append("-");
      }
      styleBuilder.append(value).append(";");
    }
    element.setAttribute("style", styleBuilder.toString());
  }

  public static void remove(Element element, String property) {
    String style = element.getAttribute("style");
    style = style.replaceAll("(\\-ms\\-|\\-moz\\-|\\-webkit\\-|\\-o\\-)?" + property + "\\s*:\\s*[^;]*;", "");
  }
}