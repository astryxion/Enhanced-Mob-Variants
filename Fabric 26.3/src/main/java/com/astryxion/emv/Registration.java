package com.astryxion.emv;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;

public final class Registration {
    public static final AttachmentType<Integer> CHICKEN_VARIANT = variant("chicken_variant");
    public static final AttachmentType<Integer> COW_VARIANT = variant("cow_variant");
    public static final AttachmentType<Integer> CAT_VARIANT = variant("cat_variant");
    public static final AttachmentType<Integer> PIG_VARIANT = variant("pig_variant");
    public static final AttachmentType<Integer> SKELETON_VARIANT = variant("skeleton_variant");
    public static final AttachmentType<Integer> SPIDER_VARIANT = variant("spider_variant");
    public static final AttachmentType<Integer> ZOMBIE_VARIANT = variant("zombie_variant");
    public static final AttachmentType<Integer> SHEEP_VARIANT = variant("sheep_variant");
    public static final AttachmentType<Integer> WOLF_VARIANT = variant("wolf_variant");

    private Registration() {}

    public static void init() {
        // Attachment types register themselves when this class is loaded.
    }

    public static int get(Entity entity, AttachmentType<Integer> type) {
        return entity.getAttachedOrElse(type, 0);
    }

    private static AttachmentType<Integer> variant(String name) {
        return AttachmentRegistry.create(
            Identifier.fromNamespaceAndPath(EnhancedMobVariants.MODID, name),
            builder -> builder
                .persistent(Codec.INT)
                .syncWith(ByteBufCodecs.VAR_INT, AttachmentSyncPredicate.all())
        );
    }
}
