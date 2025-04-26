package com.mikitellurium.telluriumforge.fluid;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import javax.annotation.Nullable;

public class BaseFluidType extends FluidType {

    private final ResourceLocation stillTexture;
    private final ResourceLocation flowingTexture;
    private final ResourceLocation overlayTexture;
    private final ResourceLocation renderOverlayTexture;
    private final int tintColor;
    private final float fogStart;
    private final float fogEnd;
    private final Vector3f fogColor;

    public BaseFluidType(final ResourceLocation stillTexture, final ResourceLocation flowingTexture, final ResourceLocation overlayTexture,
                         @Nullable final ResourceLocation renderOverlayTexture, final int tintColor, final float fogStart, final float fogEnd,
                         final Vector3f fogColor, final Properties properties) {
        super(properties);
        this.stillTexture = stillTexture;
        this.flowingTexture = flowingTexture;
        this.overlayTexture = overlayTexture;
        this.renderOverlayTexture = renderOverlayTexture;
        this.tintColor = tintColor;
        this.fogStart = fogStart;
        this.fogEnd = fogEnd;
        this.fogColor = fogColor;
    }

    public static void registerClientExtension(RegisterClientExtensionsEvent event, IClientFluidTypeExtensions extensions, BaseFluidType fluidType) {
        event.registerFluidType(extensions, fluidType);
    }

    public static void registerClientExtension(RegisterClientExtensionsEvent event, BaseFluidType fluidType) {
        registerClientExtension(event, new SimpleClientExtensions(fluidType), fluidType);
    }

    public ResourceLocation getStillTexture() {
        return stillTexture;
    }

    public ResourceLocation getFlowingTexture() {
        return flowingTexture;
    }

    public ResourceLocation getOverlayTexture() {
        return overlayTexture;
    }

    public ResourceLocation getRenderOverlayTexture() {
        return renderOverlayTexture;
    }

    public int getTintColor() {
        return tintColor;
    }

    public float getFogStart() {
        return fogStart;
    }

    public float getFogEnd() {
        return fogEnd;
    }

    public Vector3f getFogColor() {
        return fogColor;
    }

    public static class SimpleClientExtensions implements IClientFluidTypeExtensions {

        private final BaseFluidType fluidType;

        public SimpleClientExtensions(BaseFluidType fluidType) {
            this.fluidType = fluidType;
        }

        @Override
        public @NotNull ResourceLocation getStillTexture() {
            return fluidType.stillTexture;
        }

        @Override
        public @NotNull ResourceLocation getFlowingTexture() {
            return fluidType.flowingTexture;
        }

        @Override
        public @Nullable ResourceLocation getOverlayTexture() {
            return fluidType.overlayTexture;
        }

        @Override
        public @Nullable ResourceLocation getRenderOverlayTexture(Minecraft minecraft) {
            return fluidType.renderOverlayTexture;
        }

        @Override
        public int getTintColor() {
            return fluidType.tintColor;
        }

        @Override
        public @NotNull Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
            return fluidType.fogColor;
        }
    }

}
