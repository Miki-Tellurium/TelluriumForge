package com.mikitellurium.telluriumforge.test;

import com.mikitellurium.telluriumforge.event.EventHelper;
import com.mikitellurium.telluriumforge.util.LogUtils;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

public class TestEvents {

    private static final EventHelper HELPER = new EventHelper();

    public static void registerEvents() {
        HELPER
                .addListener(PlayerBlockBreakEvents.AFTER, ((world, player, pos, state, blockEntity) ->
                        LogUtils.clientChatMessage(player.getDisplayName() + " broke " + state.getBlock().getName().getString())))
                .addListener(AttackEntityCallback.EVENT, ((player, world, hand, entity, hitResult) -> {
                    if (!world.isClient) {
                        LogUtils.clientChatMessage(player.getDisplayName() + " attacked " + entity.getName().getString());
                        return ActionResult.PASS;
                    }
                    return ActionResult.PASS;
                }))
                .addListener(ServerEntityEvents.ENTITY_LOAD, TestEvents::onWorldPlayerJoin)
                .registerAll();
    }

    public static void onWorldPlayerJoin(Entity entity, ServerWorld world) {
        if (entity instanceof ServerPlayerEntity) {
            // Send a message on world join using the values specified in our config file
            entity.sendMessage(Text.literal("Int config is: " + ExampleConfig.INT_CONFIG.getValue()));
            entity.sendMessage(Text.literal("String config says: " + ExampleConfig.STRING_CONFIG.getValue()));
            entity.sendMessage(Text.literal("Boolean config is: " + ExampleConfig.BOOLEAN_CONFIG.getValue()));
            entity.sendMessage(Text.literal("Int ranged config is: " + ExampleConfig.INT_RANGED_CONFIG.getValue()));
            entity.sendMessage(Text.literal("Double ranged config is: " + ExampleConfig.DOUBLE_RANGED_CONFIG.getValue()));
            entity.sendMessage(Text.literal("Long ranged config is: " + ExampleConfig.LONG_RANGED_CONFIG.getValue()));
            entity.sendMessage(Text.literal("Enum config is: " + ExampleConfig.ENUM_CONFIG.getValue()));
        }
    }

}
