package com.aidado.editor.client.locale;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

public interface MessageBundle extends Messages {
	public static final MessageBundle INSTANCE = GWT.create(MessageBundle.class);

	// ImageDialog / ImageUploadDialog

	String imagesAndPatterns();

	String images();

	String patterns();

	String imageUpload();

	String fileName();

	String fileSize();

	String state();

	String upload();

	String contingent();

	String toManyImages();

	String imageDeleteConfirm();

	// Photobook / CarrierActivePanel

	String bringToFront();

	String sendToBack();

	String removePanel();

	String toManyPages();

	// PhotobookControls

	String textBox();

	String imageBox();

	String save();

	String savePreview();

	String saveOkText();

	String addPage();

	String deletePage();

	String pageDeletePrompt();

	// PropertiesDialog

	String title();

	String properties();

	String shadow();

	String background();

	String borderType();

	String borderColorAndSize();

	String roundedCorner();

	String page();

	String slideshow();

	// Properties

	String inset();

	String offset();

	String color();

	String style();

	String noBorder();

	String size();

	String add();

	String rotationAndOpacity();

	String rotation();

	String opacity();

	// Text

	String text();

	String backgroundColor();

	String foregroundColor();

	String textPanelDefaultText();

	String font();

	String chooseFontSize();

	String fontSize();

	String chooseFontFamily();

	String bold();

	String hr();

	String indent();

	String outdent();

	String italic();

	String justifyLeft();

	String justifyCenter();

	String justifyRight();

	String ol();

	String ul();

	String strikeThrough();

	String subscript();

	String superscript();

	String underline();

	String removeFormat();

	// AdministrationDialog, PasswordDialog, TitleDialog

	String infoAdministration();

	String url();

	String photobookInfo();

	String photobookTitle();

	String passwordForEdit();

	String passwordForView();

	String showPassword();

	String noPassword();

	String changePassword();

	String passwordChanged();

	String infoChangePasswordForEdit();

	String infoChangePasswordForView();

	String titleInfo();

	String noTitle();

	String changeTitle();
	
	String loginInfo();
	
	String deletePhotobook();

	String deletePhotobookWarn();

	// WelcomeDialog

	String welcome();

	String welcomeText();

	String sendMail();
	
	String mail();

	String mailBody(String photobookId);

	String mailSubject();

	String mailOk();

	String mailError();
}