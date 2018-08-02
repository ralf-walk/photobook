package com.aidado.editor.client.dialog;

import com.aidado.common.client.CommonAsyncCallback;
import com.aidado.common.client.InfoDialog;
import com.aidado.common.client.InfoDialog.InfoDialogListener;
import com.aidado.common.client.StyleSetter;
import com.aidado.common.client.service.ImageBlobInfoService;
import com.aidado.common.client.service.PhotobookService;
import com.aidado.common.client.widget.Button;
import com.aidado.common.client.widget.Button.ButtonListener;
import com.aidado.common.client.widget.*;
import com.aidado.common.client.widget.LayoutBuilder.CellPanelBuilder;
import com.aidado.common.client.widget.LayoutBuilder.FlowPanelBuilder;
import com.aidado.common.client.widget.LayoutBuilder.Mode;
import com.aidado.commoneditorviewer.client.PanelFactoryManager;
import com.aidado.commoneditorviewer.client.PhotobookLoader;
import com.aidado.commoneditorviewer.client.icon.pattern.Pattern;
import com.aidado.commoneditorviewer.client.image.ImageEnum;
import com.aidado.commoneditorviewer.client.model.BasePagePanel;
import com.aidado.commoneditorviewer.client.model.HasBackground;
import com.aidado.commoneditorviewer.client.model.ImageBlobInfo;
import com.aidado.commoneditorviewer.client.model.Panel;
import com.aidado.editor.client.Accessor;
import com.aidado.editor.client.event.EventManager;
import com.aidado.editor.client.icon.Icons;
import com.aidado.editor.client.locale.MessageBundle;
import com.aidado.editor.client.model.EditorImagePanel;
import com.aidado.editor.client.model.EditorPagePanel;
import com.aidado.editor.client.model.dnd.CarrierPanel.CarrierAwarePanel;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ImageDialog extends CommonDialog {

    private static final Icons EI = Icons.INSTANCE;
    private static final MessageBundle EM = MessageBundle.INSTANCE;

    private final ActionContainer ac = new ActionContainer();
    private final List<ImageBlobInfoPanel> imageBlobInfoPanelList = new ArrayList<ImageBlobInfoPanel>();

    private final Button imagesButton = Button.createToggleButton(ac, EM.images(), 120, 22, EI.images(), EM.images());
    private final Button patternsButton = Button.createToggleButton(ac, EM.patterns(), 120, 22, EI.patterns(), EM.patterns());

    private final FlowPanel content = new FlowPanel();
    private final FlowPanel imagesPatternsContentPanel = new FlowPanel();
    private final ScrollPanel patternsScrollPanel;
    private final FlowPanel imagesContentPanel;

    private final Button imagesUploadButton = Button.createPushButton(null, EM.add(), 120, 22, I.add(), EM.add());
    private final Button imagesRemoveButton = Button.createPushButton(ac, M.delete(), 120, 22, EI.imageDelete(), M.delete());
    private final ScrollPanel imagesScrollPanel;

    private int imageCount;

    public ImageDialog() {
        super(EM.imagesAndPatterns());

        // navigation
        CellPanelBuilder navigationBuilder = LayoutBuilder.createCellPanel(120, 120).put(imagesButton).put(patternsButton);
        content.add(navigationBuilder.build());

        // add images or patternsPanel
        content.add(imagesPatternsContentPanel);

        // patterns
        FlowPanelBuilder patternsPanelBuilder = LayoutBuilder.createFlowPanel();
        for (Pattern.Categorie categorie : Pattern.Categorie.values()) {
            TitleComposite patternCategorie = new TitleComposite();
            CellPanelBuilder categorieBuilder = LayoutBuilder.createCellPanel(70, 70, 70).setRowSpacing(10).setMargins(10, 0, 0, 10);
            for (Pattern pattern : Pattern.values()) {
                if (pattern.getCategorie() == categorie) {
                    PatternWidget patternWidget = new PatternWidget(pattern);
                    patternWidget.addDomHandler(ac, MouseDownEvent.getType());
                    categorieBuilder.put(patternWidget);
                }
            }
            patternCategorie.initWidget(categorieBuilder.build(), categorie.getName());
            patternsPanelBuilder.put(patternCategorie);
        }
        patternsScrollPanel = new ScrollPanel();
        patternsScrollPanel.getElement().getStyle().setProperty("maxHeight", "422px");
        patternsScrollPanel.getElement().getStyle().setOverflowY(Overflow.SCROLL);
        patternsScrollPanel.add(patternsPanelBuilder.build());

        // images
        imagesScrollPanel = new ScrollPanel();
        imagesScrollPanel.getElement().getStyle().setProperty("maxHeight", "400px");
        imagesScrollPanel.getElement().getStyle().setOverflowY(Overflow.SCROLL);
        imagesScrollPanel.getElement().getStyle().setOverflowX(Overflow.HIDDEN);

        imagesUploadButton.setId("pickfiles");
        CellPanelBuilder imageButtonsBuilder = LayoutBuilder.createCellPanel(120, 120).put(imagesUploadButton).put(imagesRemoveButton);
        imagesContentPanel = new FlowPanel();
        imagesContentPanel.add(imagesScrollPanel);
        imagesContentPanel.add(imageButtonsBuilder.build());

        add(content);
        clickCategorie(imagesButton);
        showAlways(new PositionCallback() {

            @Override
            public void setPosition(int offsetWidth, int offsetHeight) {
                setPopupPosition(100, 200);
            }
        });
    }

    @Override
    protected void onLoad() {
        updateImageList();
    }

    private void clickCategorie(Button button) {
        if (button == imagesButton) {
            imagesPatternsContentPanel.clear();
            imagesPatternsContentPanel.add(imagesContentPanel);
            imagesButton.setEnabled(false);
            patternsButton.setEnabled(true);
            patternsButton.setPushed(false);
        } else if (button == patternsButton) {
            imagesPatternsContentPanel.clear();
            imagesPatternsContentPanel.add(patternsScrollPanel);
            patternsButton.setEnabled(false);
            imagesButton.setEnabled(true);
            imagesButton.setPushed(false);
        }
    }

    public void updateImageList() {

        FlowPanelBuilder imagesPanelBuilder = LayoutBuilder.createFlowPanel();

        boolean colorBackground = false;
        for (ImageEnum image : ImageEnum.values()) {
            ImageBlobInfoPanel panel = new ImageBlobInfoPanel(image.getImageResource());
            Style style = panel.getElement().getStyle();
            style.setMarginTop(1, Unit.PX);
            style.setMarginBottom(1, Unit.PX);
            style.setPaddingTop(1, Unit.PX);
            style.setPaddingBottom(1, Unit.PX);
            if (colorBackground) {
                imagesPanelBuilder.put(LayoutBuilder.createFlowPanel().put(panel).setStyleProperty("backgroundColor", "#E3E8F3").build());
            } else {
                imagesPanelBuilder.put(panel);
            }
            colorBackground = !colorBackground;
            imageBlobInfoPanelList.add(panel);
        }
        imagesScrollPanel.add(imagesPanelBuilder.build());
/*
        ImageBlobInfoService.getImageBlobInfos(new CommonAsyncCallback<String>() {


            @Override
            public void onSuccess(String result) {
                List<ImageBlobInfo> imageBlobInfoList = PhotobookLoader.loadItemArray(result);

                imageCount = imageBlobInfoList.size();
                imagesScrollPanel.clear();
                imageBlobInfoPanelList.clear();

                if (imageCount > 0) {
                    // sort and add new image infos
                    Collections.sort(imageBlobInfoList, new Comparator<ImageBlobInfo>() {

                        @Override
                        public int compare(ImageBlobInfo info1, ImageBlobInfo info2) {
                            return info1.getFileName().compareToIgnoreCase(info2.getFileName());
                        }
                    });

                    FlowPanelBuilder imagesPanelBuilder = LayoutBuilder.createFlowPanel();
                    for (ImageBlobInfo info : imageBlobInfoList) {
                        ImageBlobInfoPanel panel = new ImageBlobInfoPanel(info);
                        Style style = panel.getElement().getStyle();
                        style.setMarginTop(1, Unit.PX);
                        style.setMarginBottom(1, Unit.PX);
                        style.setPaddingTop(1, Unit.PX);
                        style.setPaddingBottom(1, Unit.PX);
                        if (imageBlobInfoList.indexOf(info) % 2 == 1) {
                            imagesPanelBuilder.put(LayoutBuilder.createFlowPanel().put(panel).setStyleProperty("backgroundColor", "#E3E8F3").build());
                        } else {
                            imagesPanelBuilder.put(panel);
                        }
                        imageBlobInfoPanelList.add(panel);
                    }
                    imagesScrollPanel.add(imagesPanelBuilder.build());
                } else {


                    // set placeholder image
                    SimplePanel noImageContainer = new SimplePanel();
                    noImageContainer.getElement().getStyle().setProperty("textAlign", "center");
                    Image noImage = new Image();
                    noImage.setUrl(EI.noImage().getSafeUri().asString());
                    Style noImageStyle = noImage.getElement().getStyle();
                    noImageStyle.setMargin(10, Unit.PX);
                    noImageStyle.setProperty("border", "10px solid #E3E8F3");
                    noImageStyle.setProperty("borderRadius", "5px");
                    noImage.setPixelSize(100, 100);
                    noImageContainer.add(noImage);
                    imagesScrollPanel.add(noImageContainer);
                }

                // remove images from photobook
                Panel activePanel = Accessor.getPhotobook().getCarrierLayer().getActivePanel();
                List<String> imageUrls = new ArrayList<String>();
                for (ImageBlobInfo info : imageBlobInfoList) {
                    imageUrls.add(info.getUrl());
                }
                List<EditorImagePanel> removeImages = new ArrayList<EditorImagePanel>();
                for (EditorImagePanel imagePanel : EditorImagePanel.getAllImages()) {
                    if (!imageUrls.contains(imagePanel.getImageUrl())) {
                        removeImages.add(imagePanel);
                    }
                }
                for (EditorImagePanel imagePanel : removeImages) {
                    if (activePanel != null && activePanel == imagePanel) {
                        EventManager.get().firePanelUpdatedEvent(Accessor.getPhotobook().getCurrentPage());
                    }
                    imagePanel.removeFromParent();
                }
                if (removeImages.size() > 0) {
                    PhotobookService.savePhotobookContent(Accessor.getPhotobook().getPhotobookJson(), new CommonAsyncCallback<Void>());
                }
            }
        });*/
    }

    public int getImageCount() {
        return imageCount;
    }

    @Override
    protected void dragStopped() {
    }

    private class ImageBlobInfoPanel extends Composite {
        private final ImageResource imageResource;
        private final CheckBox checkBox = new CheckBox();

        public ImageBlobInfoPanel(ImageResource imageResource) {
            this.imageResource = imageResource;
            ImageWidget imageWidget = new ImageWidget(imageResource.getSafeUri().asString(), imageResource.getName());
            imageWidget.addDomHandler(ac, MouseDownEvent.getType());
            HTML fileName = new HTML(imageResource.getName());
            fileName.getElement().getStyle().setMarginTop(20, Unit.PX);
            checkBox.getElement().getStyle().setMarginTop(20, Unit.PX);
            checkBox.getElement().getStyle().setDisplay(Display.BLOCK);
            initWidget(LayoutBuilder.createCellPanel(20, 70, 130).put(checkBox).put(imageWidget).put(fileName).build());
        }

        public boolean checked() {
            return checkBox.getValue();
        }

        public ImageResource getImageBlobInfo() {
            return imageResource;
        }
    }

    private class ActionContainer implements ButtonListener, MouseDownHandler {

        @Override
        public void onClick(Button button, boolean pushed) {
            if (button == imagesButton || button == patternsButton) {
                clickCategorie(button);
            } else if (button == imagesRemoveButton) {
/*                final List<String> keepBlobKeys = new ArrayList<String>();

                boolean inUse = false;
                StringBuilder inUseBuilder = new StringBuilder(EM.imageDeleteConfirm()).append("<br /><br />");
                for (ImageBlobInfoPanel imageBlobInfoPanel : imageBlobInfoPanelList) {
                    if (imageBlobInfoPanel.checked()) {
                        ImageBlobInfo info = imageBlobInfoPanel.getImageBlobInfo();
                        for (EditorImagePanel imagePanel : EditorImagePanel.getAllImages()) {
                            if (imagePanel.getImageUrl().equals(info.getUrl())) {
                                inUseBuilder.append(!inUse ? "<ul>" : "");
                                inUse = true;
                                inUseBuilder.append("<li>").append(info.getFileName()).append("</li>");
                                break;
                            }
                        }
                    } else {
                        keepBlobKeys.add(imageBlobInfoPanel.getImageBlobInfo().getKey());
                    }
                }
                inUseBuilder.append(inUse ? "</ul>" : "");
                if (inUse) {
                    new InfoDialog(LayoutBuilder.createBox(inUseBuilder.toString(), Mode.WARNING, 400, 10), true, new InfoDialogListener() {

                        @Override
                        public void onOkClicked() {
                            syncImageBlobs(keepBlobKeys);
                        }

                        @Override
                        public void onCancelClicked() {
                        }
                    });
                } else {
                    syncImageBlobs(keepBlobKeys);
                }*/
            }
        }

        private void syncImageBlobs(List<String> keepBlobKeys) {
            ImageBlobInfoService.syncImageBlobs(keepBlobKeys, new CommonAsyncCallback<Void>() {

                @Override
                public void onSuccess(Void result) {
                    updateImageList();
                }
            });
        }

        @Override
        public void onMouseDown(MouseDownEvent event) {
            Panel panel = null;
            if (event.getSource() instanceof PatternWidget) {
                panel = new PatternWidget(((PatternWidget) event.getSource()).getBackgroundPattern());
            } else {
                ImageWidget imageWidget = (ImageWidget) event.getSource();
                panel = new ImageWidget(imageWidget.getUrl(), imageWidget.getTitle());
            }
            Element relativElement = ((Widget) event.getSource()).getElement();
            panel.setPixelSize(60, 60);

            BasePagePanel pagePanel = Accessor.getPhotobook().getCurrentPage();
            pagePanel.add(panel);
            panel.setLeft(DOM.getAbsoluteLeft(relativElement) - DOM.getAbsoluteLeft(pagePanel.getElement()));
            panel.setTop(DOM.getAbsoluteTop(relativElement) - DOM.getAbsoluteTop(pagePanel.getElement()));

            Accessor.getPhotobook().getCarrierLayer().initDragCarrier((CarrierAwarePanel) panel, event.getNativeEvent());
        }
    }

    private class ImageWidget extends Panel implements CarrierAwarePanel {

        private final RatioKeepingImage image;

        public ImageWidget(String url, String title) {
            setTitle(title);
            Style style = getElement().getStyle();
            style.setOverflow(Overflow.HIDDEN);
            setPixelSize(60, 60);

            image = new RatioKeepingImage(url, RatioKeepingImage.Mode.ENLARGE_TO_PARENT);
            image.getElement().getStyle().setCursor(Cursor.POINTER);
            add(image);
        }

        public String getUrl() {
            return image.getUrl();
        }

        @Override
        public boolean isDropPossible(Widget w, boolean dragging) {
            return w instanceof EditorImagePanel || w instanceof EditorPagePanel;
        }

        @Override
        public void onDrop(Widget w) {
            if (w instanceof EditorImagePanel) {
                ((EditorImagePanel) w).setImageUrl(getUrl());
                removeFromParent();
            } else {
                EditorImagePanel imagePanel = (EditorImagePanel) PanelFactoryManager.getFactory().createImagePanel();
                imagePanel.setPixelSize(getOffsetWidth(), getOffsetHeight());
                imagePanel.setImageUrl(getUrl());

                EditorPagePanel pagePanel = (EditorPagePanel) w;
                pagePanel.addChildPanel(imagePanel);

                imagePanel.setLeft(DOM.getAbsoluteLeft(getElement()) - DOM.getAbsoluteLeft(pagePanel.getElement()));
                imagePanel.setTop(DOM.getAbsoluteTop(getElement()) - DOM.getAbsoluteTop(pagePanel.getElement()));

                removeFromParent();
            }
        }

        @Override
        public void onClick() {
            removeFromParent();
        }
    }

    private class PatternWidget extends Panel implements CarrierAwarePanel {

        private final Pattern pattern;

        public PatternWidget(Pattern backgroundPattern) {
            this.pattern = backgroundPattern;
            Style style = getElement().getStyle();
            style.setBackgroundImage("url(" + backgroundPattern.getImageResource().getSafeUri().asString() + ")");
            style.setProperty("backgroundRepeat", "repeat");
            style.setCursor(Cursor.POINTER);
            StyleSetter.set(getElement(), "box-shadow", "0 0 5px black", true, false);
            setPixelSize(60, 60);
        }

        public Pattern getBackgroundPattern() {
            return pattern;
        }

        @Override
        public boolean isDropPossible(Widget w, boolean dragging) {
            return dragging && w instanceof HasBackground;
        }

        @Override
        public void onDrop(Widget w) {
            ((HasBackground) w).setBackgroundPattern(pattern);
            removeFromParent();
        }

        @Override
        public void onClick() {
            removeFromParent();
        }
    }
}