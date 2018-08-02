package com.aidado.viewer.client.slideshow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.aidado.common.client.StyleSetter;
import com.aidado.common.client.widget.Button;
import com.aidado.common.client.widget.DropDownPanel;
import com.aidado.common.client.widget.LayoutBuilder;
import com.aidado.common.client.widget.ListEntry;
import com.aidado.common.client.widget.Button.ButtonListener;
import com.aidado.common.client.widget.DropDownPanel.DropDownPanelListener;
import com.aidado.commoneditorviewer.client.model.BasePagePanel;
import com.aidado.commoneditorviewer.client.model.BaseRootPanel;
import com.aidado.commoneditorviewer.client.model.Panel;
import com.aidado.viewer.client.icon.Icons;
import com.aidado.viewer.client.locale.MessageBundle;
import com.aidado.viewer.client.model.ViewerImagePanel;
import com.google.gwt.animation.client.Animation;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ScrollEvent;
import com.google.gwt.user.client.Window.ScrollHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlowPanel;

public class SlideShowPanel extends AbsolutePanel implements ValueChangeHandler<String> {

	private static Icons I = Icons.INSTANCE;
	private static MessageBundle M = MessageBundle.INSTANCE;

	private enum TimerShedule implements ListEntry {
		_3(3), _5(5), _8(8), _10(10), _15(15);

		private int periodSeconds;

		private TimerShedule(int periodSeconds) {
			this.periodSeconds = periodSeconds;
		}

		@Override
		public ImageResource getImage() {
			return null;
		}

		@Override
		public String getText() {
			return periodSeconds + " " + M.seconds();
		}

		public int getMillis() {
			return periodSeconds * 1000;
		}
	}

	private final ActionContainer ac = new ActionContainer();
	private final List<ViewerImagePanel> images;

	private final Button hideButton = Button.createPushButton(ac, M.hideSlideshow(), 22, 22, I.slideshowHide());
	private final Button previousButton = Button.createPushButton(ac, M.previousImage(), 22, 22, I.slideshowPrevious());
	private final Button nextButton = Button.createPushButton(ac, M.nextImage(), 22, 22, I.slideshowNext());

	private final DropDownPanel<TimerShedule> timerSheduleDropDown = new DropDownPanel<TimerShedule>(ac, TimerShedule.values(), 100, null);
	private final Button startTimerButton = Button.createPushButton(ac, M.startSlideshow(), 22, 22, I.slideshowStart());
	private final Button stopTimerButton = Button.createPushButton(ac, M.stopSlideshow(), 22, 22, I.slideshowStop());

	private final FlowPanel controlsPanel = LayoutBuilder.createCellPanel(22, 22, 22, 44, 100, 22, 22).put(previousButton).put(hideButton).put(nextButton).putEmpty().put(timerSheduleDropDown)
	    .put(startTimerButton).put(stopTimerButton).build();
	private final AbsolutePanel controlsContainer = new AbsolutePanel();

	private final SlideShowShowAnimation showAnimation = new SlideShowShowAnimation();
	private final SlideShowHideAnimation hideAnimation = new SlideShowHideAnimation();

	private ImageTitlePanel imageTitlePanel;

	private final class SlideshowTimer extends Timer {

		@Override
		public void run() {
			int currentIndex = getHash();
			if (currentIndex == images.size() - 1) {
				cancel();
			} else {
				triggerShow(++currentIndex);
			}
		}
	};

	private final SlideshowTimer slideshowTimer = new SlideshowTimer();

	public SlideShowPanel(BaseRootPanel rootPanel) {
		timerSheduleDropDown.setSelected(TimerShedule._3);
		Window.addWindowScrollHandler(ac);
		Window.addResizeHandler(ac);

		Style style = getElement().getStyle();
		style.setWidth(100, Unit.PCT);
		style.setHeight(100, Unit.PCT);
		style.setBackgroundColor("#000000");
		style.setPosition(Position.ABSOLUTE);
		style.setTop(Window.getScrollTop(), Unit.PX);
		style.setOpacity(0);

		controlsContainer.addDomHandler(ac, MouseOverEvent.getType());
		controlsContainer.addDomHandler(ac, MouseOutEvent.getType());
		controlsContainer.getElement().getStyle().setTop(0, Unit.PX);
		controlsContainer.getElement().getStyle().setWidth(100, Unit.PCT);
		controlsPanel.getElement().getStyle().setPosition(Position.RELATIVE);
		controlsPanel.getElement().getStyle().setProperty("marginLeft", "auto");
		controlsPanel.getElement().getStyle().setProperty("marginRight", "auto");
		controlsPanel.getElement().getStyle().setProperty("marginTop", "5px");
		controlsPanel.getElement().getStyle().setProperty("marginBottom", "15px");
		controlsPanel.getElement().getStyle().setTop(0, Unit.PX);
		controlsPanel.getElement().getStyle().setOpacity(0.9);
		StyleSetter.set(controlsPanel.getElement(), "transition", "top 0.5s linear", true, false);
		controlsContainer.add(controlsPanel);
		add(controlsContainer);

		images = new ArrayList<ViewerImagePanel>();
		ArrayList<ViewerImagePanel> unsortedImages = new ArrayList<ViewerImagePanel>();
		TopComparator topComparator = new TopComparator();
		LeftComparator leftComparator = new LeftComparator();

		for (BasePagePanel page : rootPanel.getPagePanels()) {
			for (Panel child : page.getChildPanels()) {
				if (child instanceof ViewerImagePanel && ((ViewerImagePanel) child).isSlideshowEnabled()) {
					unsortedImages.add((ViewerImagePanel) child);
				}
			}
			Collections.sort(unsortedImages, topComparator);
			for (int i = 0; i < unsortedImages.size(); i++) {
				ViewerImagePanel firstInRow = unsortedImages.get(i);
				if (!images.contains(firstInRow)) {
					images.add(firstInRow);
					int distanceTopPlusHeight = firstInRow.getTop() + firstInRow.getHeight();
					for (int j = i + 1; j < unsortedImages.size(); j++) {
						ViewerImagePanel compare = unsortedImages.get(j);
						if (!images.contains(compare) && distanceTopPlusHeight > compare.getTop()) {
							images.add(compare);
						}
					}
					Collections.sort(images.subList(images.indexOf(firstInRow), images.size()), leftComparator);
				}
			}
		}
		setVisible(false);
	}

	private int getHash() {
		String hash = Window.Location.getHash();
		return (hash == null || hash.trim().length() == 0) ? -1 : Integer.valueOf(hash.replaceAll("#", ""));
	}

	public void triggerShow(ViewerImagePanel image) {
		triggerShow(images.indexOf(image));
	}

	public void triggerShow(int index) {
		History.newItem(String.valueOf(index));
	}

	public void triggerHide() {
		History.newItem(null);
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		int currentIndex = getHash();

		if (currentIndex >= 0) {
			ViewerImagePanel imagePanel = images.get(currentIndex);
			ImageTitlePanel newImageTitlePanel = new ImageTitlePanel(imagePanel);

			if (isVisible() && imageTitlePanel != null) {
				insert(newImageTitlePanel, 0);
				new ImageSwapAnimation().start(imageTitlePanel);
			} else {
				setVisible(true);
				insert(newImageTitlePanel, 0);
				controlsPanel.getElement().getStyle().setTop(0, Unit.PX);
				showAnimation.start(this);
			}
			imageTitlePanel = newImageTitlePanel;
		} else {
			hideAnimation.start(this);
		}
	}

	private class SlideShowShowAnimation extends Animation {

		private SlideShowPanel slideShowPanel;

		public void start(SlideShowPanel slideShowPanel) {
			this.slideShowPanel = slideShowPanel;
			slideShowPanel.getElement().getStyle().setOpacity(0);
			run(1000);
		}

		@Override
		protected void onUpdate(double progress) {
			slideShowPanel.getElement().getStyle().setOpacity(progress);
		}
	}

	private class SlideShowHideAnimation extends Animation {

		private SlideShowPanel slideShowPanel;

		public void start(SlideShowPanel slideShowPanel) {
			this.slideShowPanel = slideShowPanel;
			slideShowPanel.setVisible(true);
			run(1000);
		}

		@Override
		protected void onUpdate(double progress) {
			slideShowPanel.getElement().getStyle().setOpacity(1 - progress);
		}

		@Override
		protected void onComplete() {
			slideShowPanel.getElement().getStyle().setOpacity(0);
			slideShowPanel.setVisible(false);
			imageTitlePanel.removeFromParent();
		}
	}

	private class ImageSwapAnimation extends Animation {

		private ImageTitlePanel panel;

		public void start(ImageTitlePanel panel) {
			this.panel = panel;
			panel.getElement().getStyle().setOpacity(1);
			run(1000, panel.getElement());
		}

		@Override
		protected void onUpdate(double progress) {
			panel.getElement().getStyle().setOpacity((double) 1 - progress);
		}

		@Override
		protected void onComplete() {
			panel.removeFromParent();
		}
	}

	private class ActionContainer implements ButtonListener, DropDownPanelListener<TimerShedule>, MouseOutHandler, MouseOverHandler, ScrollHandler, ResizeHandler {

		private int periodMillis = TimerShedule._3.getMillis();

		@Override
		public void onClick(Button button, boolean pushed) {
			slideshowTimer.cancel();
			if (button == hideButton) {
				triggerHide();
			} else if (button == startTimerButton) {
				slideshowTimer.scheduleRepeating(periodMillis);
			} else if (button != hideButton) {
				int currentIndex = getHash();
				if (button == previousButton) {
					currentIndex = Math.max(--currentIndex, 0);
				} else if (button == nextButton) {
					currentIndex = Math.min(++currentIndex, images.size() - 1);
				}
				History.newItem(String.valueOf(currentIndex));
			}
		}

		@Override
		public void onChange(DropDownPanel<TimerShedule> source, TimerShedule timerShedule) {
			periodMillis = timerShedule.getMillis();
		}

		@Override
		public void onMouseOut(MouseOutEvent event) {
			if (!timerSheduleDropDown.isSelecting()) {
				controlsPanel.getElement().getStyle().setTop(-27, Unit.PX);
			}
		}

		@Override
		public void onMouseOver(MouseOverEvent event) {
			controlsPanel.getElement().getStyle().setTop(0, Unit.PX);
		}

		@Override
		public void onWindowScroll(ScrollEvent event) {
			if (isVisible()) {
				SlideShowPanel.this.getElement().getStyle().setTop(Window.getScrollTop(), Unit.PX);
			}
		}

		@Override
		public void onResize(ResizeEvent event) {
			if (isVisible()) {
				imageTitlePanel.update();
			}
		}
	}

	private class TopComparator implements Comparator<ViewerImagePanel> {

		@Override
		public int compare(ViewerImagePanel p1, ViewerImagePanel p2) {
			boolean p1p2 = p1.getTop() == p2.getTop() ? p1.getLeft() <= p2.getLeft() : p1.getTop() < p2.getTop();
			return p1p2 ? 0 : 1;
		}
	}

	private class LeftComparator implements Comparator<ViewerImagePanel> {

		@Override
		public int compare(ViewerImagePanel p1, ViewerImagePanel p2) {
			boolean p1p2 = p1.getLeft() == p2.getLeft() ? p1.getTop() <= p2.getTop() : p1.getLeft() < p2.getLeft();
			return p1p2 ? 0 : 1;
		}
	}
}