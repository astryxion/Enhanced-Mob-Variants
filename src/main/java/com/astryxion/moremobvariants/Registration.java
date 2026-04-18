package com.astryxion.moremobvariants;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class Registration {
    private static final MapCodec<Integer> INT_ATTACHMENT_CODEC = Codec.INT.fieldOf("value");

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
        DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, MoreMobVariants.MODID);

    public static final Supplier<AttachmentType<Integer>> CHICKEN_VARIANT =
        ATTACHMENT_TYPES.register("chicken_variant", () -> AttachmentType.builder(() -> 0).serialize(INT_ATTACHMENT_CODEC).build());

    public static final Supplier<AttachmentType<Integer>> COW_VARIANT =
        ATTACHMENT_TYPES.register("cow_variant", () -> AttachmentType.builder(() -> 0).serialize(INT_ATTACHMENT_CODEC).build());

    // 0 = vanilla body texture; 1-5 = textures/entity/cat/<name>.png (see ClientHandler#getCatTextureBaseName)
    public static final Supplier<AttachmentType<Integer>> CAT_VARIANT =
        ATTACHMENT_TYPES.register("cat_variant", () -> AttachmentType.builder(() -> 0).serialize(INT_ATTACHMENT_CODEC).build());

    public static final Supplier<AttachmentType<Integer>> PIG_VARIANT =
        ATTACHMENT_TYPES.register("pig_variant", () -> AttachmentType.builder(() -> 0).serialize(INT_ATTACHMENT_CODEC).build());

    public static final Supplier<AttachmentType<Integer>> SKELETON_VARIANT =
        ATTACHMENT_TYPES.register("skeleton_variant", () -> AttachmentType.builder(() -> 0).serialize(INT_ATTACHMENT_CODEC).build());

    public static final Supplier<AttachmentType<Integer>> SPIDER_VARIANT =
        ATTACHMENT_TYPES.register("spider_variant", () -> AttachmentType.builder(() -> 0).serialize(INT_ATTACHMENT_CODEC).build());

    public static final Supplier<AttachmentType<Integer>> ZOMBIE_VARIANT =
        ATTACHMENT_TYPES.register("zombie_variant", () -> AttachmentType.builder(() -> 0).serialize(INT_ATTACHMENT_CODEC).build());

    // Stores the Sheep variant ID (0-6)
    public static final Supplier<AttachmentType<Integer>> SHEEP_VARIANT =
        ATTACHMENT_TYPES.register("sheep_variant", () -> AttachmentType.builder(() -> 0).serialize(INT_ATTACHMENT_CODEC).build());

    // Stores the Wolf cosmetic variant ID (0 = vanilla textures, 1-7 = textures/entity/wolf/<name>_{wild,tame,angry}.png)
    public static final Supplier<AttachmentType<Integer>> WOLF_VARIANT =
        ATTACHMENT_TYPES.register("wolf_variant", () -> AttachmentType.builder(() -> 0).serialize(INT_ATTACHMENT_CODEC).build());
}
