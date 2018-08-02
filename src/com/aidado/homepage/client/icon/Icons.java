package com.aidado.homepage.client.icon;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface Icons extends ClientBundle {
	public static final Icons INSTANCE = GWT.create(Icons.class);

	@Source("aidado_start.png")
	ImageResource aidadoStart();

	@Source("comic_1.png")
	ImageResource comic1();

	@Source("comic_2.png")
	ImageResource comic2();

	@Source("comic_3.png")
	ImageResource comic3();

	@Source("comic_4.png")
	ImageResource comic4();

	@Source("features_effects.png")
	ImageResource featuresEffects();

	@Source("features_images.png")
	ImageResource featuresImages();

	@Source("features_password.png")
	ImageResource featuresPassword();

	@Source("features_patterns.png")
	ImageResource featuresPatterns();

	@Source("features_slideshow.png")
	ImageResource featuresSlideshow();

	@Source("features_text.png")
	ImageResource featuresText();

	@Source("features_datastore.png")
	ImageResource featuresDatastore();

	@Source("features_free.png")
	ImageResource featuresFree();

	@Source("photobook_edit.png")
	ImageResource photobookEdit();

	@Source("photobook_view.png")
	ImageResource photobookView();
}