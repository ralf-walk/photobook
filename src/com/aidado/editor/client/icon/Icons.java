package com.aidado.editor.client.icon;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;

public interface Icons extends ClientBundle {

	public static final Icons INSTANCE = GWT.create(Icons.class);

	@Source("images.png")
	ImageResource images();

	@Source("patterns.png")
	ImageResource patterns();

	@Source("page_add.png")
	ImageResource pageAdd();

	@Source("page_delete.png")
	ImageResource pageDelete();

	@Source("no_image.jpg")
	ImageResource noImage();

	@Source("image_delete.png")
	ImageResource imageDelete();

	@Source("image_upload.png")
	ImageResource imageUpload();

	@Source("image_upload_animation.gif")
	ImageResource imageUploadAnimation();

	@Source("image_upload_ok.png")
	ImageResource imageUploadOk();

	@Source("drag_corner.png")
	ImageResource dragCorner();

	@Source("drag_arrow_left.png")
	ImageResource dragArrowLeft();

	@Source("drag_arrow_right.png")
	ImageResource dragArrowRight();

	@Source("drag_arrow_up.png")
	ImageResource dragArrowUp();

	@Source("drag_arrow_down.png")
	ImageResource dragArrowDown();

	@Source("menu_administration.png")
	ImageResource menuAdministration();

	@Source("menu_save.png")
	ImageResource menuSave();

	@Source("menu_save_and_preview.png")
	ImageResource menuSaveAndPreview();

	@Source("panel_text.png")
	ImageResource panelText();

	@Source("panel_image.png")
	ImageResource panelImage();

	@Source("panel_image.png")
	DataResource picture();

	@Source("panel_delete.png")
	ImageResource panelDelete();

	@Source("panel_bring_to_front.png")
	ImageResource panelBringToFront();

	@Source("panel_send_to_back.png")
	ImageResource panelSendToBack();

	@Source("text_align_center.png")
	ImageResource textAlignCenter();

	@Source("text_align_left.png")
	ImageResource textAlignLeft();

	@Source("text_align_right.png")
	ImageResource textAlignRight();

	@Source("text_back_color.png")
	ImageResource textBackColor();

	@Source("text_bold.png")
	ImageResource textBold();

	@Source("text_fore_colors.png")
	ImageResource textForeColor();

	@Source("text_indent.png")
	ImageResource textIndent();

	@Source("text_italic.png")
	ImageResource textItalic();

	@Source("text_numbered_bullets.png")
	ImageResource textNumberedBullets();

	@Source("text_numbered.png")
	ImageResource textNumbered();

	@Source("text_outdent.png")
	ImageResource textOutdent();

	@Source("text_separator.png")
	ImageResource textSeparator();

	@Source("text_strike_through.png")
	ImageResource textStrikeThrough();

	@Source("text_subscript.png")
	ImageResource textSubscript();

	@Source("text_superscript.png")
	ImageResource textSuperscript();

	@Source("text_underline.png")
	ImageResource textUnderline();

	@Source("password.png")
	ImageResource password();

	@Source("title.png")
	ImageResource title();

	@Source("photobook_delete.png")
	ImageResource photobookDelete();
}