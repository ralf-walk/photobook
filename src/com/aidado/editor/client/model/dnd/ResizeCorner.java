package com.aidado.editor.client.model.dnd;

import com.aidado.commoneditorviewer.client.model.Panel;
import com.aidado.editor.client.Accessor;
import com.aidado.editor.client.icon.Icons;
import com.aidado.editor.client.model.EditorPagePanel;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;


public class ResizeCorner extends AbsolutePanel {
  enum CornerType {
    TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT
  }

  private static final Icons EI = Icons.INSTANCE;
  private final ActionContainer ac = new ActionContainer();
  private final CornerType cornerType;
  private final CarrierActivePanel carrierPanel;

  public ResizeCorner(CarrierActivePanel carrierPanel, CornerType cornerType) {
    this.carrierPanel = carrierPanel;
    this.cornerType = cornerType;
    addDomHandler(ac, MouseDownEvent.getType());
    addDomHandler(ac, MouseUpEvent.getType());
    addDomHandler(ac, MouseMoveEvent.getType());
    int resizeCornerSize = EI.dragCorner().getWidth();
    setPixelSize(resizeCornerSize, resizeCornerSize);
    getElement().getStyle().setCursor(Cursor.POINTER);
    Image resizeCornerImage = new Image(EI.dragCorner());
    resizeCornerImage.getElement().getStyle().setPosition(Position.ABSOLUTE);
    add(resizeCornerImage);
  }

  public CornerType getCornerType() {
    return cornerType;
  }

  private class ActionContainer extends MouseHandler {

    private boolean resizePossible = false;
    private int oldX;
    private int oldY;
    private int offsetCornerLeft;
    private int offsetCornerTop;

    @Override
    public void onMouseDown(MouseDownEvent event) {
      super.onMouseDown(event);
      ResizeCorner dragCorner = (ResizeCorner) event.getSource();
      DOM.setCapture(dragCorner.getElement());
      RootPanel.get().getElement().getStyle().setCursor(Cursor.MOVE);
      getElement().getStyle().clearCursor();
      resizePossible = true;
      EditorPagePanel pagePanel = (EditorPagePanel)Accessor.getPhotobook().getCurrentPage();
      int resizeCornerHalfSize = EI.dragCorner().getWidth() / 2;
      offsetCornerLeft = event.getRelativeX(dragCorner.getElement()) - resizeCornerHalfSize;
      offsetCornerTop = event.getRelativeY(dragCorner.getElement()) - resizeCornerHalfSize;
      oldX = event.getRelativeX(pagePanel.getElement()) - offsetCornerLeft;
      oldY = event.getRelativeY(pagePanel.getElement()) - offsetCornerTop;
    }

    @Override
    public void onMouseUp(MouseUpEvent event) {
      super.onMouseUp(event);
      ResizeCorner dragCorner = (ResizeCorner) event.getSource();
      DOM.releaseCapture(dragCorner.getElement());
      if (resizePossible) {
        RootPanel.get().getElement().getStyle().clearCursor();
        getElement().getStyle().setCursor(Cursor.POINTER);
        resizePossible = false;
        oldX = 0;
        oldY = 0;
        offsetCornerLeft = 0;
        offsetCornerTop = 0;
      }
    }

    @Override
    public void onMouseMove(MouseMoveEvent event) {
      super.onMouseMove(event);
      if (resizePossible) {
        ResizeCorner dragCorner = (ResizeCorner) event.getSource();

        EditorPagePanel pagePanel = (EditorPagePanel)Accessor.getPhotobook().getCurrentPage();
        int newX = event.getRelativeX(pagePanel.getElement()) - offsetCornerLeft;
        int newY = event.getRelativeY(pagePanel.getElement()) - offsetCornerTop;

        // keep coordinates in page panel
        newX = newX < 0 ? 0 : newX;
        newX = newX > pagePanel.getOffsetWidth() ? pagePanel.getOffsetWidth() : newX;
        newY = newY < 0 ? 0 : newY;
        newY = newY > pagePanel.getOffsetHeight() ? pagePanel.getOffsetHeight() : newY;

        if (newX != oldX || newY != oldY) {
          int newWidth = 0;
          int newHeight = 0;
          if (dragCorner.getCornerType() == CornerType.TOP_LEFT) {
            newWidth = pagePanel.getDistanceLeft(carrierPanel) + carrierPanel.getOffsetWidth() - newX;
            newHeight = pagePanel.getDistanceTop(carrierPanel) + carrierPanel.getOffsetHeight() - newY;
            updatePanel(carrierPanel, newX, newY, newWidth, newHeight);
          } else if (dragCorner.getCornerType() == CornerType.TOP_RIGHT) {
            newWidth = newX - pagePanel.getDistanceLeft(carrierPanel);
            newHeight = pagePanel.getDistanceTop(carrierPanel) + carrierPanel.getOffsetHeight() - newY;
            updatePanel(carrierPanel, -1, newY, newWidth, newHeight);
          } else if (dragCorner.getCornerType() == CornerType.BOTTOM_LEFT) {
            newWidth = pagePanel.getDistanceLeft(carrierPanel) + carrierPanel.getOffsetWidth() - newX;
            newHeight = newY - pagePanel.getDistanceTop(carrierPanel);
            updatePanel(carrierPanel, newX, -1, newWidth, newHeight);
          } else {
            newWidth = newX - pagePanel.getDistanceLeft(carrierPanel);
            newHeight = newY - pagePanel.getDistanceTop(carrierPanel);
            updatePanel(carrierPanel, -1, -1, newWidth, newHeight);
          }
        }
      }
    }

    private void updatePanel(Panel child, int x, int y, int width, int height) {

      // possibly overwrite arguments with min Sizes
      int minWidth = child.getMinWidth();
      int minHeight = child.getMinHeight();
      if (minWidth > width) {
        if (x != -1) {
          x -= minWidth - width;
        }
        width = minWidth;
      }
      if (minHeight > height) {
        if (y != -1) {
          y -= minHeight - height;
        }
        height = minHeight;
      }
      width = width >= minWidth ? width : minWidth;
      height = height >= minHeight ? height : minWidth;
      child.setPixelSize(width, height);

      Panel pagePanel = Accessor.getPhotobook().getCurrentPage();
      child.setLeft(x != -1 ? x - pagePanel.getLeft() : child.getLeft());
      child.setTop(y != -1 ? y - pagePanel.getTop() : child.getTop());

      // setting new positions
      if (x != -1) {
        oldX = x;
      }
      if (y != -1) {
        oldY = y;
      }
    }
  }
}