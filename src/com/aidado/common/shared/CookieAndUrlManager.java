package com.aidado.common.shared;

import java.io.Serializable;

public abstract class CookieAndUrlManager {

  @SuppressWarnings("serial")
  public static class Session implements Serializable {

    private String photobookId;

    private String sessionId;

    public Session(String photobookId, String sessionId) {
      this.photobookId = photobookId;
      this.sessionId = sessionId;
    }

    public Session() {
    }

    public String getPhotobookId() {
      return photobookId;
    }

    public String getSessionId() {
      return sessionId;
    }
  }

  public AidadoPage getCurrentPage() {
    String path = getPathPart();
    if (path != null) {
      for (AidadoPage page : AidadoPage.values()) {
        if (path.contains(page.getDisplayName())) {
          return page;
        }
      }
    }
    if (getPhotobookIdUrl() != null) {
      return AidadoPage.VIEWER;
    }
    return AidadoPage.START;
  };

  public String getPossiblePhotobookCharacters() {
    return "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
  }

  public abstract void redirectPage(AidadoPage page);

  public abstract boolean isProdMode();

  public abstract String getPathPart();

  public abstract Session getSession();

  public abstract void setSession(Session session);

  public abstract String getPhotobookIdUrl();
}