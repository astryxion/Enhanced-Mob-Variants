package com.astryxion.emv;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Locale;

public class EmvConfigScreen extends Screen {
    private static final int BUTTON_WIDTH = 150;
    private static final int BUTTON_HEIGHT = 20;
    private static final int ROW = 24;

    private final Screen parent;

    public EmvConfigScreen(Screen parent) {
        super(new TranslationTextComponent("emv.configuration.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int left = this.width / 2 - 155;
        int right = this.width / 2 + 5;
        int y = 36;

        this.addButton(new Button(left, y, 310, BUTTON_HEIGHT, spawnModeLabel(Config.spawnMode()), button -> {
            Config.SpawnMode next = Config.spawnMode() == Config.SpawnMode.RANDOM
                ? Config.SpawnMode.UNIFORM
                : Config.SpawnMode.RANDOM;
            Config.SPAWN_MODE.set(next);
            button.setMessage(spawnModeLabel(next));
        }));
        y += ROW + 6;

        addToggle(left, y, "chicken", Config.CHICKEN);
        addToggle(right, y, "cow", Config.COW);
        y += ROW;
        addToggle(left, y, "cat", Config.CAT);
        addToggle(right, y, "pig", Config.PIG);
        y += ROW;
        addToggle(left, y, "sheep", Config.SHEEP);
        addToggle(right, y, "wolf", Config.WOLF);
        y += ROW;
        addToggle(left, y, "zombie", Config.ZOMBIE);
        addToggle(right, y, "skeleton", Config.SKELETON);
        y += ROW;
        addToggle(left, y, "spider", Config.SPIDER);

        this.addButton(new Button(this.width / 2 - 100, this.height - 27, 200, BUTTON_HEIGHT,
            new TranslationTextComponent("gui.done"), button -> this.onClose()));
    }

    private void addToggle(int x, int y, String key, ForgeConfigSpec.BooleanValue value) {
        this.addButton(new Button(x, y, BUTTON_WIDTH, BUTTON_HEIGHT, toggleLabel(key, Config.enabled(value)), button -> {
            boolean next = !value.get();
            value.set(next);
            button.setMessage(toggleLabel(key, next));
        }));
    }

    private static ITextComponent spawnModeLabel(Config.SpawnMode mode) {
        return new TranslationTextComponent("emv.configuration.spawn_mode")
            .append(new StringTextComponent(": "))
            .append(new TranslationTextComponent("emv.configuration.spawn_mode." + mode.name().toLowerCase(Locale.ROOT)));
    }

    private static ITextComponent toggleLabel(String key, boolean enabled) {
        return new TranslationTextComponent("emv.configuration." + key)
            .append(new StringTextComponent(": "))
            .append(new TranslationTextComponent(enabled ? "options.on" : "options.off"));
    }

    @Override
    public void render(com.mojang.blaze3d.matrix.MatrixStack stack, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(stack);
        drawCenteredString(stack, this.font, this.title, this.width / 2, 15, 0xFFFFFF);
        super.render(stack, mouseX, mouseY, partialTick);
    }

    @Override
    public void onClose() {
        Config.save();
        this.minecraft.setScreen(this.parent);
    }
}
