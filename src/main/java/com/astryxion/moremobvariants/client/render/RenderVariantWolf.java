package com.astryxion.moremobvariants.client.render;

import net.minecraft.client.model.ModelWolf;
import net.minecraft.client.renderer.entity.RenderWolf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.ResourceLocation;

public class RenderVariantWolf extends RenderWolf {

    private static final String[] BREEDS = {
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

    // REQUIRED in 1.7.10
    public RenderVariantWolf() {
        super(new ModelWolf(), new ModelWolf(), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        EntityWolf wolf = (EntityWolf) entity;

        // Pick breed deterministically
        int breedIndex = Math.abs(wolf.getEntityId()) % BREEDS.length;
        String breed = BREEDS[breedIndex];

        String state;
        if (wolf.isAngry()) {
            state = "angry";
        } else if (wolf.isTamed()) {
            state = "tame";
        } else {
            state = "wild";
        }

        return new ResourceLocation(
            "moremobvariants",
            "textures/entity/wolf/" + breed + "_" + state + ".png"
        );
    }
}
