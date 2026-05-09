package com.hackatoa.swordblock.client.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ShieldItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(AvatarRenderer.class)
public class MixinAvatarRenderer {

    // Hide the left arm in first-person when the local player has a shield in their off-hand.
    // renderLeftHand is called by renderPlayerArm (empty main hand) and renderMapHand, both
    // of which are first-person-only paths inside ItemInHandRenderer.
    @Inject(method = "renderLeftHand", at = @At("HEAD"), cancellable = true)
    private void swordblock$renderLeftHand(PoseStack poseStack, SubmitNodeCollector collector, int light, Identifier texture, boolean showSleeve, CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null && mc.player.getOffhandItem().getItem() instanceof ShieldItem) {
            ci.cancel();
        }
    }
}
