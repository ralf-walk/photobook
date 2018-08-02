package com.aidado.common.shared;

public enum AidadoPage {
	START("homepage.html", "index.html", "homepage/homepage.nocache.js"), FEATURES("homepage.html", "features.html", "homepage/homepage.nocache.js"), ADMINISTRATION("homepage.html",
	    "administration.html", "homepage/homepage.nocache.js"), ACTIVATE("homepage.html", "activate.html", "homepage/homepage.nocache.js"), IMPRESS("homepage.html", "impress.html",
	    "homepage/homepage.nocache.js"), AGB("homepage.html", "agb.html", "homepage/homepage.nocache.js"), EDITOR("editor.html", "editor.html", "editor/editor.nocache.js"), VIEWER("viewer.html",
	    "viewer.html", "viewer/viewer.nocache.js");

	private String realName;
	private String displayName;
	private String moduleName;

	private AidadoPage(String realName, String displayName, String moduleName) {
		this.realName = realName;
		this.displayName = displayName;
		this.moduleName = moduleName;
	}

	public static AidadoPage getFromPath(String path) {
		for (AidadoPage page : AidadoPage.values()) {
			if (path.equals(page.getDisplayName())) {
				return page;
			}
		}
		return null;
	}

	public String getRealName() {
		return realName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getModuleName() {
		return moduleName;
	}
}