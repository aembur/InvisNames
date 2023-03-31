package com.aembr.healthtags.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.EntityLivingBase;

@Mixin(RenderLivingBase.class)
public abstract class RenderLivingMixin<T extends EntityLivingBase> extends Render<T> {

    public RenderLivingMixin(RenderManager renderManager) {
        super(renderManager);
    }

    @Inject(method = "doRender(Lnet/minecraft/entity/EntityLivingBase;DDDFF)V", at = @At("HEAD"))
    public void doRender(EntityLivingBase entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;

            if(!player.isInvisible()) {
                return;
            }

            String nameStr = player.getName();

            // Background rectangle stuff
            float rectOpacity = 0.2f;
            int rectPadding = 1;
            int rectWidth = Minecraft.getMinecraft().fontRenderer.getStringWidth(nameStr) + rectPadding * 2;
            int rectHeight = Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT + rectPadding * 2;

            GlStateManager.pushMatrix();
            GlStateManager.translate(x, y + entity.height + 0.5F, z);
            GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
            GlStateManager.scale(-0.025F, -0.025F, 0.025F);
            GlStateManager.disableLighting();
            RenderHelper.disableStandardItemLighting();

            // Draw background
            GlStateManager.disableTexture2D();
            GlStateManager.color(0.0f, 0.0f, 0.0f, rectOpacity);
            GlStateManager.disableDepth();
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferBuilder = tessellator.getBuffer();
            bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
            bufferBuilder.pos(-rectWidth / 2, -rectHeight / 2, 0).endVertex();
            bufferBuilder.pos(-rectWidth / 2, rectHeight / 2, 0).endVertex();
            bufferBuilder.pos(rectWidth / 2, rectHeight / 2, 0).endVertex();
            bufferBuilder.pos(rectWidth / 2, -rectHeight / 2, 0).endVertex();
            tessellator.draw();
            GlStateManager.enableDepth();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.enableTexture2D();

            // Draw name string
            Minecraft.getMinecraft().fontRenderer.drawString(nameStr, -Minecraft.getMinecraft().fontRenderer.getStringWidth(nameStr) / 2, -Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT / 2, 0xFFFFFF, false);

            RenderHelper.enableStandardItemLighting();
            GlStateManager.enableLighting();
            GlStateManager.popMatrix();
        }
    }
}


