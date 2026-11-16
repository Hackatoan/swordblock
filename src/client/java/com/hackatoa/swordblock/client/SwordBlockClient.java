package com.hackatoa.swordblock.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;

public class SwordBlockClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Unbound by default so existing behavior is unchanged; bind it in Controls to use it.
        KeyMapping toggleKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.swordblock.toggle",
                InputConstants.UNKNOWN.getValue(),
                KeyMapping.Category.MISC));
        KeyMapping shieldKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.swordblock.toggle_shields",
                InputConstants.UNKNOWN.getValue(),
                KeyMapping.Category.MISC));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleKey.consumeClick()) {
                SwordBlockState.enabled = !SwordBlockState.enabled;
                if (client.player != null) {
                    client.player.sendOverlayMessage(Component.translatable(
                            SwordBlockState.enabled ? "message.swordblock.enabled" : "message.swordblock.disabled"));
                }
            }
            while (shieldKey.consumeClick()) {
                SwordBlockState.hideShields = !SwordBlockState.hideShields;
                if (client.player != null) {
                    client.player.sendOverlayMessage(Component.translatable(
                            SwordBlockState.hideShields ? "message.swordblock.shields_hidden" : "message.swordblock.shields_shown"));
                }
            }
            if (client.player == null || !SwordBlockState.enabled) {
                SwordBlockState.active = false;
                return;
            }
            boolean hasSword = client.player.getMainHandItem().is(ItemTags.SWORDS);
            boolean rightMouseDown = client.options.keyUse.isDown();
            SwordBlockState.active = hasSword && rightMouseDown;
        });
    }
}
