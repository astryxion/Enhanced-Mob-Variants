package com.astryxion.emv;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Locale;

public class EmvConfigScreen extends Screen {
    private static final int BUTTON_WIDTH = 150;
    private static final int BUTTON_HEIGHT = 20;
    private static final int ROW = 24;

    private final Screen parent;

    public EmvConfigScreen(Screen parent) {
        super(Component.translatable("emv.configuration.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int left = this.width / 2 - 155;
        int right = this.width / 2 + 5;
        int y = 36;

        this.addRenderableWidget(
            CycleButton.<Config.SpawnMode>builder(mode -> Component.translatable(
                    "emv.configuration.spawn_mode." + mode.name().toLowerCase(Locale.ROOT)))
                .withValues(Config.SpawnMode.RANDOM, Config.SpawnMode.UNIFORM)
                .withInitialValue(Config.spawnMode())
                .withTooltip(mode -> Tooltip.create(Component.translatable("emv.configuration.spawn_mode.tooltip")))
                .create(left, y, 310, BUTTON_HEIGHT, Component.translatable("emv.configuration.spawn_mode"),
                    (button, value) -> Config.SPAWN_MODE.set(value))
        );
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

        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> this.onClose())
            .bounds(this.width / 2 - 100, this.height - 27, 200, BUTTON_HEIGHT)
            .build());
    }

    private void addToggle(int x, int y, String key, ForgeConfigSpec.BooleanValue value) {
        this.addRenderableWidget(
            CycleButton.booleanBuilder(CommonComponents.OPTION_ON, CommonComponents.OPTION_OFF)
                .withInitialValue(Config.enabled(value))
                .withTooltip(on -> Tooltip.create(Component.translatable("emv.configuration." + key + ".tooltip")))
                .create(x, y, BUTTON_WIDTH, BUTTON_HEIGHT, Component.translatable("emv.configuration." + key),
                    (button, enabled) -> value.set(enabled))
        );
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);
        graphics.drawCenteredString(this.font, this.title, this.width / 2, 15, 0xFFFFFF);
        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public void onClose() {
        Config.save();
        this.minecraft.setScreen(this.parent);
    }
}
