package com.aidado.commoneditorviewer.client;

import com.aidado.common.client.StyleSetter;
import com.aidado.common.client.icon.Icons;
import com.aidado.common.client.locale.MessageBundle;
import com.aidado.common.client.widget.Button;
import com.aidado.common.client.widget.Button.ButtonListener;
import com.aidado.commoneditorviewer.client.Photobook.PageSwapAnimation.SwapDirection;
import com.aidado.commoneditorviewer.client.model.BasePagePanel;
import com.aidado.commoneditorviewer.client.model.BaseRootPanel;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.SimplePanel;

public abstract class Photobook extends AbsolutePanel {

    private final static Icons I = Icons.INSTANCE;
    private final static MessageBundle M = MessageBundle.INSTANCE;

    protected BaseRootPanel rootPanel;
    private String photobookJson;
    private BasePagePanel currentPage = null;

    private final ActionContainer ac = new ActionContainer();
    private final Button previousButton = Button.createPushButton(ac, M.previousPage(), 52, 52, I.pagePrevious());
    private final Button nextButton = Button.createPushButton(ac, M.nextPage(), 52, 52, I.pageNext());
    private final PageCounterPanel pageCounter = new PageCounterPanel();

    public Photobook(String photobookJson) {
        this.photobookJson = photobookJson;
        setPixelSize(1000, 740);
        Style style = getElement().getStyle();
        style.setOverflow(Overflow.VISIBLE);

        add(pageCounter);
        setWidgetPosition(pageCounter, 475, 0);
        previousButton.getElement().getStyle().setOpacity(0.7);
        add(previousButton);
        setWidgetPosition(previousButton, 0, 350);
        nextButton.getElement().getStyle().setOpacity(0.7);
        add(nextButton);
        setWidgetPosition(nextButton, 948, 350);
    }

    @Override
    public void onLoad() {
        init(photobookJson);
        photobookJson = null;
    }

    public void init(String photobookJson) {
        if (photobookJson != null) {
            rootPanel = PhotobookLoader.loadItem(photobookJson);
        }
    }

    protected void showPage(BasePagePanel page) {
        insert(page, 0, 0, 0);
        int index = rootPanel.getPagePanels().indexOf(page);

        // update counter
        pageCounter.update(index);

        // update buttons
        previousButton.setEnabled(index > 0);
        nextButton.setEnabled(index < rootPanel.getPagePanels().size() - 1);
        currentPage = page;
    }

    protected void swapPage(BasePagePanel page, SwapDirection swapDirection) {
        SimplePanel pageSwapPanel = new SimplePanel();
        pageSwapPanel.getElement().appendChild(DOM.clone(page.getElement(), true));
        insert(pageSwapPanel, 0, 0, 0);
        pageSwapPanel.getElement().getStyle().setWidth(100, Unit.PCT);
        new PageSwapAnimation(pageSwapPanel, swapDirection).start();
        page.removeFromParent();
    }

    public BasePagePanel getCurrentPage() {
        return currentPage;
    }

    public BaseRootPanel getRootPanel() {
        return (BaseRootPanel) rootPanel;
    }

    private class ActionContainer implements ButtonListener {

        @Override
        public void onClick(Button button, boolean pushed) {
            if (button == previousButton) {
                swapPage(currentPage, SwapDirection.RIGHT);
                showPage(rootPanel.getPagePanels().get(rootPanel.getPagePanels().indexOf(currentPage) - 1));
            } else if (button == nextButton) {
                swapPage(currentPage, SwapDirection.LEFT);
                showPage(rootPanel.getPagePanels().get(rootPanel.getPagePanels().indexOf(currentPage) + 1));
            }
        }
    }

    public static class PageSwapAnimation {

        public enum SwapDirection {
            LEFT, RIGHT;
        }

        private final SimplePanel pageSwapPanel;

        public PageSwapAnimation(SimplePanel pageSwapPanel, SwapDirection swapDirection) {
            StyleSetter.set(pageSwapPanel.getElement(), "transform-origin", swapDirection == SwapDirection.LEFT ? "left center" : "right center", true, false);
            this.pageSwapPanel = pageSwapPanel;
        }

        public void start() {
            pageSwapPanel.removeFromParent();
            //run(1200, pageSwapPanel.getElement());
        }
/*
		@Override
		protected void onUpdate(double progress) {
			pageSwapPanel.getElement().getStyle().setOpacity((double) 1 - progress);
			StyleSetter.set(pageSwapPanel.getElement(), "transform", "rotateY(" + (double) 90 * progress + "deg)", true, false);
		}

		@Override
		protected void onComplete() {
			pageSwapPanel.removeFromParent();
		}*/
    }

    private class PageCounterPanel extends AbsolutePanel {

        SpanElement text = Document.get().createSpanElement();

        public PageCounterPanel() {
            setPixelSize(50, 20);
            Style style = getElement().getStyle();
            style.setPosition(Position.ABSOLUTE);
            style.setBackgroundColor("orange");
            style.setProperty("border", "1px solid black");
            style.setProperty("borderRadius", "5px");
            style.setProperty("textAlign", "center");
            getElement().appendChild(text);
        }

        public void update(int index) {
            text.setInnerText(M.page() + " " + String.valueOf(index + 1));
        }
    }
}