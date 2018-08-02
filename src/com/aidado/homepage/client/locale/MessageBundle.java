package com.aidado.homepage.client.locale;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;
import com.google.gwt.safehtml.shared.SafeHtml;

public interface MessageBundle extends Messages {
  public static final MessageBundle INSTANCE = GWT.create(MessageBundle.class);

  // ### START ###

  String startComic1();

  String startComic2();

  String startComic3();

  String startComic4();

  SafeHtml startTextIntro();

  SafeHtml startDemo();

  // ### FEATURES ###

  String featuresImagesHead();

  String featuresImages();

  String featuresTextHead();

  String featuresText();

  String featuresEffectsHead();

  String featuresEffects();

  String featuresPatternsHead();

  String featuresPatterns();

  String featuresPasswordHead();

  String featuresPassword();

  String featuresSlideshowHead();

  String featuresSlideshow();

  String featuresDatastoreHead();

  String featuresDatastore();

  String featuresFreeHead();

  String featuresFree();

  // ### IMPRESS ###

  SafeHtml impressContent();

  // ### Photobook ###

  String createNewPhotobook();

  String createPhotobookHeader();

  String createPhotobookInfo();

  String editPhotobookInfo();

  String termsHeader();

  SafeHtml terms();
  
  SafeHtml termsDev();
  
  String acceptTerms();

  // ### AGB ###

  SafeHtml agb();
}