package com.aidado.editor.client.dialog;

import com.aidado.common.client.widget.Button;
import com.aidado.common.client.widget.DropDownPanel;
import com.aidado.common.client.widget.LayoutBuilder;
import com.aidado.common.client.widget.ListEntry;
import com.aidado.common.client.widget.Button.ButtonListener;
import com.aidado.common.client.widget.DropDownPanel.DropDownPanelListener;
import com.aidado.common.client.widget.LayoutBuilder.CellPanelBuilder.Alignment;
import com.aidado.editor.client.icon.Icons;
import com.aidado.editor.client.model.EditorTextPanel;
import com.google.gwt.dom.client.IFrameElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.InitializeEvent;
import com.google.gwt.event.logical.shared.InitializeHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.RichTextArea.FontSize;
import com.google.gwt.user.client.ui.RichTextArea.Formatter;

public class TextEditorDialog extends ModelAwareDialog<EditorTextPanel> {

	private static final Icons EI = Icons.INSTANCE;

	public TextEditorDialog(EditorTextPanel textPanel) {
		super(EM.text());
		setModel(textPanel);		

		textArea.addInitializeHandler(new InitializeHandler() {
			public void onInitialize(InitializeEvent ie) {
				IFrameElement frame = (IFrameElement) textArea.getElement().cast();
				frame.getStyle().setBackgroundColor("#ffffff");
				Style style = frame.getContentDocument().getBody().getStyle();
				style.setMargin(0, Unit.PX);
			}
		});

		FlowPanel content = new FlowPanel();
		FlowPanel controls = LayoutBuilder.createCellPanel(30, 30, 30, 30, 20, 30, 30, 30, 30).put(bold).put(italic).put(underline).put(strikethrough).putEmpty().put(subscript).put(superscript).put(
		    indent).put(outdent).put(justifyLeft).put(justifyCenter).put(justifyRight).put(hr).putEmpty().put(ol).put(ul).put(backColor).put(foreColor).setRowSpacing(5).build();
		content.add(controls);

		FlowPanel font = LayoutBuilder.createCellPanel(92, 160).put(EM.font()).put(fontBox).put(EM.fontSize()).put(fontSizeBox).setRowSpacing(5).setMargins(0, 5).build();
		content.add(font);

		Style textAreaStyle = textArea.getElement().getStyle();
		textAreaStyle.setMarginTop(10, Unit.PX);
		textAreaStyle.setWidth(250, Unit.PX);
		textAreaStyle.setProperty("border", "1px solid #000000");
		textAreaStyle.setProperty("borderRadius", "3px");
		textAreaStyle.setProperty("boxShadow", "inset 0 0 1px 1px #AAAAAA");
		textAreaStyle.setPadding(3, Unit.PX);
		content.add(textArea);

		FlowPanel buttons = LayoutBuilder.createCellPanel(105, 100).put(cancelButton).put(okButton).setMargins(0, 10, 3, 0).setAlignment(Alignment.RIGHT).build();
		content.add(buttons);
		add(content, 10);
		showCenter();
	}

	private enum Font implements ListEntry {
		ARIAL("Arial"), TIMES_NEW_ROMAN("Times New Roman"), COMIC_SANS_MS("Comic Sans MS"), COURIER_NEW("Courier New"), WIDE_LATIN("Wide Latin"), BODONI("Bodoni"), IMPACT("Impact"), GARAMOND("Garamond"), HELVETICA(
		    "Helvetica,Helv"), BALLOON("Balloon"), GEORGIA("Georgia"), TREBUCHET("Trebuchet"), VERANDA("Verdana");

		private final String font;

		private Font(String font) {
			this.font = font;
		}

		public String getFont() {
			return font;
		}

		@Override
		public String getText() {
			return "<span style=\"font-family:" + font + "\">" + font + "</span>";
		}

		@Override
		public ImageResource getImage() {
			return null;
		}
	}

	private enum Size implements ListEntry {

		XX_SMALL("XX-Small", FontSize.XX_SMALL), X_SMALL("X-Small", FontSize.X_SMALL), SMALL("Small", FontSize.SMALL), MEDIUM("Medium", FontSize.MEDIUM), LARGE("Large", FontSize.LARGE), X_LARGE(
		    "X-Large", FontSize.X_LARGE), XX_LARGE("XX-Large", FontSize.XX_LARGE);

		private final String text;
		private final FontSize fontSize;

		private Size(String text, FontSize fontSize) {
			this.text = text;
			this.fontSize = fontSize;
		}

		@Override
		public String getText() {
			return text;
		}

		@Override
		public ImageResource getImage() {
			return null;
		}

		public FontSize getFontSize() {
			return fontSize;
		}
	}

	private final ActionContainer handler = new ActionContainer();
	private final RichTextArea textArea = new RichTextArea();
	private final Button bold = Button.createToggleButton(handler, EM.bold(), 22, 22, EI.textBold());
	private final Button italic = Button.createToggleButton(handler, EM.italic(), 22, 22, EI.textItalic());
	private final Button underline = Button.createToggleButton(handler, EM.underline(), 22, 22, EI.textUnderline());
	private final Button subscript = Button.createToggleButton(handler, EM.subscript(), 22, 22, EI.textSubscript());
	private final Button superscript = Button.createToggleButton(handler, EM.superscript(), 22, 22, EI.textSuperscript());
	private final Button strikethrough = Button.createToggleButton(handler, EM.strikeThrough(), 22, 22, EI.textStrikeThrough());
	private final Button indent = Button.createPushButton(handler, EM.indent(), 22, 22, EI.textIndent());
	private final Button outdent = Button.createPushButton(handler, EM.outdent(), 22, 22, EI.textOutdent());

	private final Button justifyLeft = Button.createPushButton(handler, EM.justifyLeft(), 22, 22, EI.textAlignLeft());
	private final Button justifyCenter = Button.createPushButton(handler, EM.justifyCenter(), 22, 22, EI.textAlignCenter());
	private final Button justifyRight = Button.createPushButton(handler, EM.justifyRight(), 22, 22, EI.textAlignRight());
	private final Button hr = Button.createPushButton(handler, EM.hr(), 22, 22, EI.textSeparator());
	private final Button ol = Button.createPushButton(handler, EM.ol(), 22, 22, EI.textNumbered());
	private final Button ul = Button.createPushButton(handler, EM.ul(), 22, 22, EI.textNumberedBullets());
	private final Button removeFormat = Button.createPushButton(handler, EM.removeFormat(), 22, 22, EI.textUnderline());
	private final Button backColor = Button.createPushButton(handler, EM.backgroundColor(), 22, 22, EI.textBackColor());
	private final Button foreColor = Button.createPushButton(handler, EM.foregroundColor(), 22, 22, EI.textForeColor());

	private final Button okButton = Button.createOkButton(handler);
	private final Button cancelButton = Button.createCancelButton(handler);

	private final DropDownPanel<Font> fontBox = new DropDownPanel<Font>(new DropDownPanelListener<Font>() {

		@Override
		public void onChange(DropDownPanel<Font> source, Font font) {
			getFormatter().setFontName(font.getFont());
		}
	}, Font.values(), 160, new ListEntry() {

		@Override
		public ImageResource getImage() {
			return null;
		}

		@Override
		public String getText() {
			return EM.chooseFontFamily();
		}
	});

	private final DropDownPanel<Size> fontSizeBox = new DropDownPanel<Size>(new DropDownPanelListener<Size>() {

		@Override
		public void onChange(DropDownPanel<Size> source, Size size) {
			getFormatter().setFontSize(size.getFontSize());
		}
	}, Size.values(), 160, new ListEntry() {

		@Override
		public ImageResource getImage() {
			return null;
		}

		@Override
		public String getText() {
			return EM.chooseFontSize();
		}
	});

	public void init() {
		textArea.setHTML(getModel().getHtmlText());
		textArea.addKeyUpHandler(handler);
	}

	private Formatter getFormatter() {
		return textArea.getFormatter();
	}

	private void updateStatus() {
		if (getFormatter() != null) {
			bold.setPushed(getFormatter().isBold());
			italic.setPushed(getFormatter().isItalic());
			underline.setPushed(getFormatter().isUnderlined());
			subscript.setPushed(getFormatter().isSubscript());
			superscript.setPushed(getFormatter().isSuperscript());
			strikethrough.setPushed(getFormatter().isStrikethrough());
		}
	}

	private class ActionContainer implements ButtonListener, KeyUpHandler {

		@Override
		public void onClick(Button button, boolean pushed) {
			if (button == okButton) {
				getModel().setHtmlText(textArea.getHTML());
				hide();
			} else if (button == cancelButton) {
				hide();
			} else if (button == bold) {
				getFormatter().toggleBold();
			} else if (button == italic) {
				getFormatter().toggleItalic();
			} else if (button == underline) {
				getFormatter().toggleUnderline();
			} else if (button == subscript) {
				getFormatter().toggleSubscript();
			} else if (button == superscript) {
				getFormatter().toggleSuperscript();
			} else if (button == strikethrough) {
				getFormatter().toggleStrikethrough();
			} else if (button == indent) {
				getFormatter().rightIndent();
			} else if (button == outdent) {
				getFormatter().leftIndent();
			} else if (button == justifyLeft) {
				getFormatter().setJustification(RichTextArea.Justification.LEFT);
			} else if (button == justifyCenter) {
				getFormatter().setJustification(RichTextArea.Justification.CENTER);
			} else if (button == justifyRight) {
				getFormatter().setJustification(RichTextArea.Justification.RIGHT);
			} else if (button == hr) {
				getFormatter().insertHorizontalRule();
			} else if (button == ol) {
				getFormatter().insertOrderedList();
			} else if (button == ul) {
				getFormatter().insertUnorderedList();
			} else if (button == removeFormat) {
				getFormatter().removeFormat();
			} else if (button == backColor) {
				new ColorDialog(new HasColor() {

					@Override
					public void setColor(String color) {
						getFormatter().setBackColor(color);
					}
				});
			} else if (button == foreColor) {
				new ColorDialog(new HasColor() {

					@Override
					public void setColor(String color) {
						getFormatter().setForeColor(color);
					}
				});
			}
		}

		public void onKeyUp(KeyUpEvent event) {
			Widget sender = (Widget) event.getSource();
			if (sender == textArea) {
				updateStatus();
			}
		}
	}
}