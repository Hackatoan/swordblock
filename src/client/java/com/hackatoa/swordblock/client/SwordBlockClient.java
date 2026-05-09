package com.hackatoa.swordblock.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.tags.ItemTags;

public class SwordBlockClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) {
                SwordBlockState.active = false;
                return;
            }
            boolean hasSword = client.player.getMainHandItem().is(ItemTags.SWORDS);
            boolean rightMouseDown = client.options.keyUse.isDown();
            boolean notUsingItem = !client.player.isUsingItem();
            SwordBlockState.active = hasSword && rightMouseDown && notUsingItem;
        });
    }
}
