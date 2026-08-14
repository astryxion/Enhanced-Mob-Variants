package com.astryxion.emv;

import com.mojang.serialization.Codec;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class Registration {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
        DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, EnhancedMobVariants.MODID);

    public static final Supplier<AttachmentType<Integer>> CHICKEN_VARIANT = variant("chicken_variant");
    public static final Supplier<AttachmentType<Integer>> COW_VARIANT = variant("cow_variant");
    public static final Supplier<AttachmentType<Integer>> CAT_VARIANT = variant("cat_variant");
    public static final Supplier<AttachmentType<Integer>> PIG_VARIANT = variant("pig_variant");
    public static final Supplier<AttachmentType<Integer>> SKELETON_VARIANT = variant("skeleton_variant");
    public static final Supplier<AttachmentType<Integer>> SPIDER_VARIANT = variant("spider_variant");
    public static final Supplier<AttachmentType<Integer>> ZOMBIE_VARIANT = variant("zombie_variant");
    public static final Supplier<AttachmentType<Integer>> SHEEP_VARIANT = variant("sheep_variant");
    public static final Supplier<AttachmentType<Integer>> WOLF_VARIANT = variant("wolf_variant");

    private static Supplier<AttachmentType<Integer>> variant(String name) {
        return ATTACHMENT_TYPES.register(name, () -> AttachmentType.builder(() -> 0)
            .serialize(Codec.INT)
            .sync(ByteBufCodecs.VAR_INT)
            .build());
    }
}
