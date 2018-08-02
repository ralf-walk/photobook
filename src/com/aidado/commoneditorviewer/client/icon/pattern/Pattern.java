package com.aidado.commoneditorviewer.client.icon.pattern;

import com.google.gwt.resources.client.ImageResource;

public enum Pattern {
  FENCE_1(PatternBundle.INSTANCE.fence1(), Categorie.SURFACE),
  FLAGSTONE_1(PatternBundle.INSTANCE.flagstone1(), Categorie.SURFACE),
  FLAGSTONE_2(PatternBundle.INSTANCE.flagstone2(), Categorie.SURFACE),
  GRASS_1(PatternBundle.INSTANCE.grass1(), Categorie.SURFACE),
  GRASS_2(PatternBundle.INSTANCE.grass2(), Categorie.SURFACE),
  GRASS_3(PatternBundle.INSTANCE.grass3(), Categorie.SURFACE),
  METAL_1(PatternBundle.INSTANCE.metal1(), Categorie.SURFACE),
  METAL_2(PatternBundle.INSTANCE.metal2(), Categorie.SURFACE),
  MODERN_1(PatternBundle.INSTANCE.modern1(), Categorie.MODERN),
  MODERN_2(PatternBundle.INSTANCE.modern2(), Categorie.MODERN),
  MODERN_3(PatternBundle.INSTANCE.modern3(), Categorie.MODERN),
  MODERN_4(PatternBundle.INSTANCE.modern4(), Categorie.MODERN),
  MODERN_5(PatternBundle.INSTANCE.modern5(), Categorie.MODERN),
  MODERN_6(PatternBundle.INSTANCE.modern6(), Categorie.MODERN),
  MODERN_7(PatternBundle.INSTANCE.modern7(), Categorie.MODERN),
  MODERN_8(PatternBundle.INSTANCE.modern8(), Categorie.MODERN),
  MODERN_9(PatternBundle.INSTANCE.modern9(), Categorie.MODERN),
  MODERN_10(PatternBundle.INSTANCE.modern10(), Categorie.MODERN),
  MODERN_11(PatternBundle.INSTANCE.modern11(), Categorie.MODERN),
  MODERN_12(PatternBundle.INSTANCE.modern12(), Categorie.MODERN),
  MODERN_13(PatternBundle.INSTANCE.modern13(), Categorie.MODERN),
  MODERN_14(PatternBundle.INSTANCE.modern14(), Categorie.MODERN),
  MODERN_15(PatternBundle.INSTANCE.modern15(), Categorie.MODERN),
  MODERN_16(PatternBundle.INSTANCE.modern16(), Categorie.MODERN),
  MODERN_17(PatternBundle.INSTANCE.modern17(), Categorie.MODERN),
  MODERN_18(PatternBundle.INSTANCE.modern18(), Categorie.MODERN),
  MODERN_19(PatternBundle.INSTANCE.modern19(), Categorie.MODERN),
  MODERN_20(PatternBundle.INSTANCE.modern20(), Categorie.MODERN),
  MODERN_21(PatternBundle.INSTANCE.modern21(), Categorie.MODERN),
  PAPER_1(PatternBundle.INSTANCE.paper1(), Categorie.SURFACE),
  RETRO_1(PatternBundle.INSTANCE.retro1(), Categorie.RETRO),
  RETRO_2(PatternBundle.INSTANCE.retro2(), Categorie.RETRO),
  RETRO_3(PatternBundle.INSTANCE.retro3(), Categorie.RETRO),
  RETRO_4(PatternBundle.INSTANCE.retro4(), Categorie.RETRO),
  RETRO_5(PatternBundle.INSTANCE.retro5(), Categorie.RETRO),
  RETRO_6(PatternBundle.INSTANCE.retro6(), Categorie.RETRO),
  RETRO_7(PatternBundle.INSTANCE.retro7(), Categorie.RETRO),
  RETRO_8(PatternBundle.INSTANCE.retro8(), Categorie.RETRO),
  RETRO_9(PatternBundle.INSTANCE.retro9(), Categorie.RETRO),
  RETRO_10(PatternBundle.INSTANCE.retro10(), Categorie.RETRO),
  RETRO_11(PatternBundle.INSTANCE.retro11(), Categorie.RETRO),
  RETRO_12(PatternBundle.INSTANCE.retro12(), Categorie.RETRO),
  RETRO_13(PatternBundle.INSTANCE.retro13(), Categorie.RETRO),
  WALL_1(PatternBundle.INSTANCE.wall1(), Categorie.SURFACE),
  WALL_2(PatternBundle.INSTANCE.wall2(), Categorie.SURFACE),
  WALL_3(PatternBundle.INSTANCE.wall3(), Categorie.SURFACE),
  WALL_4(PatternBundle.INSTANCE.wall4(), Categorie.SURFACE),
  WOOD_1(PatternBundle.INSTANCE.wood1(), Categorie.SURFACE),
  WOOD_2(PatternBundle.INSTANCE.wood2(), Categorie.SURFACE),
  WOOD_3(PatternBundle.INSTANCE.wood3(), Categorie.SURFACE),
  WOOD_4(PatternBundle.INSTANCE.wood4(), Categorie.SURFACE),
  WOOD_5(PatternBundle.INSTANCE.wood5(), Categorie.SURFACE),
  WOOD_6(PatternBundle.INSTANCE.wood6(), Categorie.SURFACE),
  WOOD_7(PatternBundle.INSTANCE.wood7(), Categorie.SURFACE),
  WOOD_8(PatternBundle.INSTANCE.wood8(), Categorie.SURFACE);

  public enum Categorie {
    MODERN("Modern"), RETRO("Retro"), SURFACE("Surface"), OTHER("Other");

    private final String name;

    private Categorie(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }
  }

  private final ImageResource imageResource;
  private final Categorie categorie;
  private final boolean hasTransparency;

  private Pattern(ImageResource imageResource, Categorie categorie) {
    this(imageResource, categorie, false);
  }

  private Pattern(ImageResource imageResource, Categorie categorie, boolean hasTransparency) {
    this.imageResource = imageResource;
    this.categorie = categorie;
    this.hasTransparency = hasTransparency;
  }

  public ImageResource getImageResource() {
    return imageResource;
  }

  public Categorie getCategorie() {
    return categorie;
  }

  public boolean hasTransparency() {
    return hasTransparency;
  }
}