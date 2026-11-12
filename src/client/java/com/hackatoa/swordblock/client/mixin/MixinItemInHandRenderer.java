package com.hackatoa.swordblock.client.mixin;

import com.hackatoa.swordblock.client.SwordBlockState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.FirstPersonHandsAndItemsRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.FirstPersonHandsAndItemsRenderState;
import net.minecraft.client.renderer.state.level.PlayerRenderState;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(FirstPersonHandsAndItemsRenderer.class)
public class MixinItemInHandRenderer {

    @Inject(method = "submitArmWithItem", at = @At("HEAD"), cancellable = true)
    private void swordblock$cancelShield(PlayerRenderState playerState, FirstPersonHandsAndItemsRenderState handsState, float partialTick, float pitch, InteractionHand hand, float swingProgress, ItemStack item, float equipProgress, PoseStack poseStack, SubmitNodeCollector collector, int packedLight, CallbackInfo ci) {
        if (hand == InteractionHand.OFF_HAND && item.getItem() instanceof ShieldItem) {
            ci.cancel();
        }
    }

    // Apply the vanilla BLOCK arm transform before the sword item is submitted,
    // giving the first-person sword a guard/blocking position.
    // ordinal=1 targets the ItemStackRenderState.submit call at the end of the default item path,
    // after applyItemArmTransform has already positioned the arm.
    @Inject(method = "submitArmWithItem",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/client/renderer/item/ItemStackRenderState;submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;III)V",
                     shift = At.Shift.BEFORE,
                     ordinal = 1))
    private void swordblock$applyBlockingPose(PlayerRenderState playerState, FirstPersonHandsAndItemsRenderState handsState, float partialTick, float pitch, InteractionHand hand, float swingProgress, ItemStack item, float equipProgress, PoseStack poseStack, SubmitNodeCollector collector, int packedLight, CallbackInfo ci) {
        if (hand == InteractionHand.MAIN_HAND && SwordBlockState.active) {
            poseStack.translate(-0.14142136f, 0.08f, 0.14142136f);
            poseStack.rotateDegrees(Axis.XP, -102.25f);
            poseStack.rotateDegrees(Axis.YP, 13.365f);
            poseStack.rotateDegrees(Axis.ZP, 78.05f);
        }
    }
}
