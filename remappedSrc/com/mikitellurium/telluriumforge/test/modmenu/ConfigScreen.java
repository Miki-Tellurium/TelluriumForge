package com.mikitellurium.telluriumforge.test.modmenu;

import com.mikitellurium.telluriumforge.TelluriumForge;
import com.mikitellurium.telluriumforge.option.BooleanConfigOption;
import com.mikitellurium.telluriumforge.option.EnumConfigOption;
import com.mikitellurium.telluriumforge.option.IntConfigOption;
import com.mikitellurium.telluriumforge.test.ExampleConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.OptionListWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ConfigScreen extends GameOptionsScreen {

    Screen parent;
    private OptionListWidget list;

    public ConfigScreen(Screen parent) {
        super(parent, MinecraftClient.getInstance().options, Text.literal("Clock Overlay"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        this.list = new OptionListWidget(this.client, this.width, this.height, 32, this.height - 32, 25);
        this.list.addSingleOptionEntry(
                new EnumConfigOption<>(new Identifier(TelluriumForge.modId(), "enum_config"), ExampleConfig.ENUM_CONFIG).asOption());
        this.list.addSingleOptionEntry(
                new BooleanConfigOption(new Identifier(TelluriumForge.modId(), "boolean_config"), "enabled", "disabled",
                        ExampleConfig.BOOLEAN_CONFIG).asOption());
        this.list.addSingleOptionEntry(
                new IntConfigOption(new Identifier(TelluriumForge.modId(), "int_config"), ExampleConfig.INT_RANGED_CONFIG).asOption());

        this.addSelectableChild(this.list);
        this.addDrawableChild(
                ButtonWidget.builder(ScreenTexts.DONE, (button) -> {
                            ExampleConfig.CONFIG.save();
                            this.client.setScreen(this.parent);
                        }).position(this.width / 2 - 100, this.height - 27)
                        .size(200, 20)
                        .build());
    }

    @Override
    public void removed() {
        ExampleConfig.CONFIG.save();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        this.list.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 15, 16777215);
        super.render(context, mouseX, mouseY, delta);
    }

}
