package com.aidado.common.client;

import com.aidado.common.shared.AidadoPage;
import com.aidado.common.shared.CookieAndUrlManager;
import com.google.gwt.core.client.GWT;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;

public class CookieAndUrlManagerClient extends CookieAndUrlManager {

    private static final CookieAndUrlManagerClient INSTANCE = new CookieAndUrlManagerClient();

    public String getPhotobook() {
        return Storage.getLocalStorageIfSupported().getItem("photobook");
    }

    public void setPhotobook(String photobook) {
        Storage.getLocalStorageIfSupported().setItem("photobook", photobook);
    }
    public void clearPhotobook() {
        Storage.getLocalStorageIfSupported().removeItem("photobook");
    }



    public static CookieAndUrlManagerClient get() {
        return INSTANCE;
    }

    @Override
    public boolean isProdMode() {
        return GWT.isProdMode();
    }

    @Override
    public String getPathPart() {
        String path = Window.Location.getPath();
        if (path != null) {
            path = path.replaceFirst("^/", "").trim();
            path = path.replaceFirst("/.*", "").trim();
            path = path.length() == 0 ? null : path.toLowerCase();
        }
        return path;
    }

    @Override
    public void redirectPage(AidadoPage page) {
        Location.assign("/" + page.getDisplayName());
		/*if (isProdMode()) {
			Location.assign("http://www.aidado.com/" + page.getDisplayName());
		} else {
			Location.assign(page.getDisplayName() + "?gwt.codesvr=127.0.0.1:9997");
		}*/
    }

    public void redirectViewer(String photobookId) {
        if (GWT.isProdMode()) {
            Location.assign("http://www.aidado.com/" + photobookId);
        } else {
            StringBuilder urlBuilder = new StringBuilder(AidadoPage.VIEWER.getDisplayName());
            urlBuilder.append("?gwt.codesvr=127.0.0.1:9997");
            urlBuilder.append("&pid=").append(photobookId);
            Location.assign(urlBuilder.toString());
        }
    }

    @Override
    public Session getSession() {
        String pid = Cookies.getCookie("aidado_pid");
        String sid = Cookies.getCookie("aidado_sid");
        return new Session(pid, sid);
    }

    @Override
    public void setSession(Session session) {
        if (session == null) {
            Cookies.removeCookie("aidado_pid");
            Cookies.removeCookie("aidado_sid");
        } else {
            Cookies.setCookie("aidado_pid", session.getPhotobookId());
            Cookies.setCookie("aidado_sid", session.getSessionId());
        }
    }

    @Override
    public String getPhotobookIdUrl() {
        String photobookId = null;
        if (GWT.isProdMode()) {
            photobookId = getPathPart();
            if (photobookId == null) {
                photobookId = Location.getHost().split("\\.")[0];
            }
        } else {
            photobookId = Location.getParameter("pid");
        }
        return (photobookId != null && photobookId.toUpperCase().matches("[" + getPossiblePhotobookCharacters() + "]{5}")) ? photobookId.toUpperCase() : null;
    }
}