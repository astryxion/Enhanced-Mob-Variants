package com.astryxion.moremobvariants.event;

import net.minecraft.util.ResourceLocation;

/**
 * Sheep use body textures under {@code assets/moremobvariants/textures/entity/sheep/}
 * and wool under {@code .../sheep/wool/}, matching modern (1.20+) layout for the same PNGs.
 */
public final class SheepVariants {

    /** Namespace for sheep PNGs (separate from mod id so paths match your resource folder). */
    public static final String ASSET_NAMESPACE = "moremobvariants";

    /** 0 = vanilla; 1–6 = named skins (see {@link #textureSuffix(int)}). */
    public static final int VARIANT_COUNT = 7;

    private static final String[] SUFFIXES = {
        null,
        "fuzzy",
        "inky",
        "long_nosed",
        "patched",
        "rainbow",
        "rocky"
    };

    private SheepVariants() {}

    public static String textureSuffix(int variant) {
        if (variant < 0 || variant >= SUFFIXES.length) {
            return null;
        }
        return SUFFIXES[variant];
    }

    public static ResourceLocation woolTexture(int variant) {
        String s = textureSuffix(variant);
        if (s == null) {
            return null;
        }
        return new ResourceLocation(ASSET_NAMESPACE, "textures/entity/sheep/wool/" + s + ".png");
    }
}
