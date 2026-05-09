package com.hackatoa.swordblock.client.mixin;

import com.hackatoa.swordblock.client.SwordBlockState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.InteractionHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(AvatarRenderer.class)
public class MixinAvatarRenderer {

    @Inject(
        method = "extractRenderState(Lnet/minecraft/world/entity/Avatar;Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;F)V",
        at = @At("RETURN")
    )
    private void swordblock$extractRenderState(Avatar entity, AvatarRenderState renderState, float partialTick, CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        if (entity != mc.player || !SwordBlockState.active) return;

        if (renderState.mainArm == HumanoidArm.RIGHT) {
            renderState.rightArmPose = HumanoidModel.ArmPose.BLOCK;
        } else {
            renderState.leftArmPose = HumanoidModel.ArmPose.BLOCK;
        }
    }
}
