package com.mikitellurium.telluriumforge.test;

import com.mikitellurium.telluriumforge.event.EventHelper;
import com.mikitellurium.telluriumforge.util.LogUtils;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.util.ActionResult;

public class TestEvents {

    public static void registerEvents() {
        EventHelper.getInstance()
                .addListener(PlayerBlockBreakEvents.AFTER, ((world, player, pos, state, blockEntity) ->
                        LogUtils.clientChatMessage(player.getDisplayName() + " broke " + state.getBlock().getName().getString())))
                .addListener(AttackEntityCallback.EVENT, ((player, world, hand, entity, hitResult) -> {
                    if (!world.isClient) {
                        LogUtils.clientChatMessage(player.getDisplayName() + " attacked " + entity.getName().getString());
                        return ActionResult.PASS;
                    }
                    return ActionResult.PASS;
                }))
                .addListener(ServerEntityEvents.ENTITY_LOAD, ExampleConfig::onWorldPlayerJoin)
                .registerAll();
    }

}
