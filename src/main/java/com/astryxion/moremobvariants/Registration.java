package com.astryxion.moremobvariants;

import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import com.mojang.serialization.Codec;
import java.util.function.Supplier;

public class Registration {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = 
        DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, MoreMobVariants.MODID);

    public static final Supplier<AttachmentType<Integer>> CHICKEN_VARIANT = 
        ATTACHMENT_TYPES.register("chicken_variant", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build());

    public static final Supplier<AttachmentType<Integer>> COW_VARIANT = 
        ATTACHMENT_TYPES.register("cow_variant", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build());

    public static final Supplier<AttachmentType<Integer>> PIG_VARIANT = 
        ATTACHMENT_TYPES.register("pig_variant", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build());

    public static final Supplier<AttachmentType<Integer>> SKELETON_VARIANT = 
        ATTACHMENT_TYPES.register("skeleton_variant", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build());

    public static final Supplier<AttachmentType<Integer>> SPIDER_VARIANT = 
        ATTACHMENT_TYPES.register("spider_variant", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build());

    public static final Supplier<AttachmentType<Integer>> ZOMBIE_VARIANT = 
        ATTACHMENT_TYPES.register("zombie_variant", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build());

    // Stores the Sheep variant ID (0-6)
    public static final Supplier<AttachmentType<Integer>> SHEEP_VARIANT = 
        ATTACHMENT_TYPES.register("sheep_variant", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build());
}