package com.aidado.common.client.icon;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface Icons extends ClientBundle {

	public static final Icons INSTANCE = GWT.create(Icons.class);

	@Source("content_head.png")
	ImageResource contentHead();

	@Source("content_foot.png")
	ImageResource contentFoot();

	@Source("page_previous.png")
	ImageResource pagePrevious();

	@Source("page_next.png")
	ImageResource pageNext();

	@Source("ok.png")
	ImageResource ok();

	@Source("cancel.png")
	ImageResource cancel();

	@Source("color.png")
	ImageResource color();

	@Source("add.png")
	ImageResource add();

	@Source("aidado_logo.png")
	ImageResource aidadoLogo();

	@Source("dropdown_down.png")
	ImageResource dropdownDown();

	@Source("slider_slider.png")
	ImageResource slider();

	@Source("slider_left.png")
	ImageResource sliderLeft();

	@Source("slider_right.png")
	ImageResource sliderRight();

	@Source("slider_line.png")
	ImageResource sliderLine();
}
