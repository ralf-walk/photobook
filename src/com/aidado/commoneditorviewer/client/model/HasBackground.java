package com.aidado.commoneditorviewer.client.model;

import com.aidado.commoneditorviewer.client.icon.pattern.Pattern;
import com.aidado.commoneditorviewer.client.model.Model.Extension;

public interface HasBackground extends Extension {
	
  String getBackgroundColor();
  
  void setBackgroundColor(String backgroundColor);
  
  Pattern getBackgroundPattern();
  
  void setBackgroundPattern(Pattern backgroundPattern);
}