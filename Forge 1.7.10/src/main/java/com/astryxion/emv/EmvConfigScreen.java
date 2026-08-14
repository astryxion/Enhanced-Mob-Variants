package com.astryxion.emv;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

import java.util.Locale;

@SideOnly(Side.CLIENT)
public class EmvConfigScreen extends GuiScreen {
    private static final int BUTTON_WIDTH = 150;
    private static final int BUTTON_HEIGHT = 20;
    private static final int ROW = 24;
    private static final int ID_SPAWN = 0;
    private static final int ID_DONE = 10;

    private final GuiScreen parent;

    public EmvConfigScreen(GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        int left = this.width / 2 - 155;
        int right = this.width / 2 + 5;
        int y = 36;

        this.buttonList.add(new GuiButton(ID_SPAWN, left, y, 310, BUTTON_HEIGHT, spawnModeLabel(Config.spawnMode)));
        y += ROW + 6;

        addToggle(1, left, y, "chicken", Config.chicken);
        addToggle(2, right, y, "cow", Config.cow);
        y += ROW;
        addToggle(3, left, y, "cat", Config.cat);
        addToggle(4, right, y, "pig", Config.pig);
        y += ROW;
        addToggle(5, left, y, "sheep", Config.sheep);
        addToggle(6, right, y, "wolf", Config.wolf);
        y += ROW;
        addToggle(7, left, y, "zombie", Config.zombie);
        addToggle(8, right, y, "skeleton", Config.skeleton);
        y += ROW;
        addToggle(9, left, y, "spider", Config.spider);

        this.buttonList.add(new GuiButton(ID_DONE, this.width / 2 - 100, this.height - 27, 200, BUTTON_HEIGHT, I18n.format("gui.done")));
    }

    private void addToggle(int id, int x, int y, String key, boolean enabled) {
        this.buttonList.add(new GuiButton(id, x, y, BUTTON_WIDTH, BUTTON_HEIGHT, toggleLabel(key, enabled)));
    }

    private static String spawnModeLabel(Config.SpawnMode mode) {
        return I18n.format("emv.configuration.spawn_mode")
            + ": "
            + I18n.format("emv.configuration.spawn_mode." + mode.name().toLowerCase(Locale.ROOT));
    }

    private static String toggleLabel(String key, boolean enabled) {
        return I18n.format("emv.configuration." + key)
            + ": "
            + I18n.format(enabled ? "options.on" : "options.off");
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case ID_SPAWN:
                Config.spawnMode = Config.spawnMode == Config.SpawnMode.RANDOM
                    ? Config.SpawnMode.UNIFORM
                    : Config.SpawnMode.RANDOM;
                button.displayString = spawnModeLabel(Config.spawnMode);
                break;
            case 1:
                Config.chicken = !Config.chicken;
                button.displayString = toggleLabel("chicken", Config.chicken);
                break;
            case 2:
                Config.cow = !Config.cow;
                button.displayString = toggleLabel("cow", Config.cow);
                break;
            case 3:
                Config.cat = !Config.cat;
                button.displayString = toggleLabel("cat", Config.cat);
                break;
            case 4:
                Config.pig = !Config.pig;
                button.displayString = toggleLabel("pig", Config.pig);
                break;
            case 5:
                Config.sheep = !Config.sheep;
                button.displayString = toggleLabel("sheep", Config.sheep);
                break;
            case 6:
                Config.wolf = !Config.wolf;
                button.displayString = toggleLabel("wolf", Config.wolf);
                break;
            case 7:
                Config.zombie = !Config.zombie;
                button.displayString = toggleLabel("zombie", Config.zombie);
                break;
            case 8:
                Config.skeleton = !Config.skeleton;
                button.displayString = toggleLabel("skeleton", Config.skeleton);
                break;
            case 9:
                Config.spider = !Config.spider;
                button.displayString = toggleLabel("spider", Config.spider);
                break;
            case ID_DONE:
                this.mc.displayGuiScreen(this.parent);
                break;
            default:
                break;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format("emv.configuration.title"), this.width / 2, 15, 0xFFFFFF);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void onGuiClosed() {
        Config.save();
        super.onGuiClosed();
    }
}
