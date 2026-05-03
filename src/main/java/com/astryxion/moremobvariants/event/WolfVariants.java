package com.astryxion.moremobvariants.event;

import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.ResourceLocation;

/**
 * Wolf skins under {@code assets/moremobvariants/textures/entity/wolf/}, using the same
 * wild / tame / angry split as 1.12 vanilla and modern wolf texture sets.
 * <p>
 * Expected files per variant basename, e.g. {@code ashen}:
 * {@code ashen_wild.png}, {@code ashen_tame.png}, {@code ashen_angry.png}
 */
public final class WolfVariants {

    public static final String ASSET_NAMESPACE = "moremobvariants";

    /** 0 = vanilla; 1–15 match basenames under {@code assets/moremobvariants/textures/entity/wolf/}. */
    public static final int VARIANT_COUNT = 16;

    private static final String[] SUFFIXES = {
        null,
        "ashen",
        "basenji",
        "black",
        "chestnut",
        "french_bulldog",
        "german_shepherd",
        "golden_retriever",
        "husky",
        "jupiter",
        "rusty",
        "snowy",
        "spotted",
        "striped",
        "white_terrier",
        "woods"
    };

    private WolfVariants() {}

    public static String textureSuffix(int variant) {
        if (variant < 0 || variant >= SUFFIXES.length) {
            return null;
        }
        return SUFFIXES[variant];
    }

    /**
     * Same state selection as {@link net.minecraft.client.renderer.entity.RenderWolf} / the old
     * variant renderer: tame wins, else angry, else wild.
     */
    public static ResourceLocation textureForWolf(EntityWolf wolf, int variant) {
        String base = textureSuffix(variant);
        if (base == null) {
            return null;
        }
        String state = wolf.isTamed()
            ? "tame"
            : wolf.isAngry()
                ? "angry"
                : "wild";
        return new ResourceLocation(ASSET_NAMESPACE, "textures/entity/wolf/" + base + "_" + state + ".png");
    }
}
