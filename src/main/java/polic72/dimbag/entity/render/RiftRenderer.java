package polic72.dimbag.entity.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import com.mojang.math.Vector3f;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import polic72.dimbag.Reference;
import polic72.dimbag.core.ModEntities;
import polic72.dimbag.entity.RiftEntity;


/**
 * Represents the renderer for the Rift.
 * 
 * @author polic72
 */
public class RiftRenderer extends EntityRenderer<RiftEntity>
{
	/**
	 * The location of the texture for the rift.
	 */
	private static final ResourceLocation RIFT_TEXTURE = new ResourceLocation(Reference.MOD_ID, 
		"textures/entity/rift.png");
	
	
	protected EntityRenderDispatcher dispatcher;
	
	
	public RiftRenderer(Context context)
	{
		super(context);
		
		dispatcher = context.getEntityRenderDispatcher();
	}
	
	
	@Override
	public ResourceLocation getTextureLocation(RiftEntity rift)
	{
		return RIFT_TEXTURE;
	}
	
	
	@Override
	public void render(RiftEntity rift, float entityYaw, float partialTicks, PoseStack matrixStack,
			MultiBufferSource buffer, int packedLight)
	{
		matrixStack.pushPose();
		matrixStack.mulPose(Vector3f.YP.rotation(180F - entityYaw));
		
		float scale = 0.0625F;
		matrixStack.scale(scale, scale, scale);
		
		matrixStack.mulPose(dispatcher.cameraOrientation());
		
		VertexConsumer vertexConsumer = buffer.getBuffer(RenderType..entityCutout(RIFT_TEXTURE));
		
		renderRift(rift, vertexConsumer, rift.getWidthPixels(), rift.getHeightPixels(), 0, 0);
		
		matrixStack.popPose();
		
		matrixStack.
		
		
		super.render(rift, entityYaw, partialTicks, matrixStack, buffer, packedLight);
	}
	
	
	private void renderRift(RiftEntity rift, VertexConsumer consumer, int width, int height, int textureU, int textureV)
    {
        float negHalfWidth = (float)(-width) / 2.0F;
        float negHalfHeight = (float)(-height) / 2.0F;
        
        float textureWidth = 16.0F;
        float textureHeight = 32.0F;

        for (int i = 0; i < width / 16; ++i)	//i = blocks in width
        {
            for (int q = 0; q < height / 16; ++q)	//q = blocks in height
            {
                float blockCenter_width = negHalfWidth + (float)((i + 1) * 16);
                float blockCenter_width_0 = negHalfWidth + (float)(i * 16);
                
                float blockCenter_height = negHalfHeight + (float)((q + 1) * 16) + 16;
                float blockCenter_height_0 = negHalfHeight + (float)(q * 16) + 16;
                
//                setLightmap(entityRift, (blockCenter_width + blockCenter_width_0) / 2.0F, 
//                		(blockCenter_height + blockCenter_height_0) / 2.0F);
                
                float f19 = (float)(textureU + width - i * 16) / textureWidth;
                float f20 = (float)(textureU + width - (i + 1) * 16) / textureWidth;
                
                float f21 = (float)(textureV + height - q * 16) / textureHeight;
                float f22 = (float)(textureV + height - (q + 1) * 16) / textureHeight;
                
                
//                Tesselator tesselator = Tesselator.getInstance();
//                BufferBuilder bufferbuilder = tesselator.getBuilder();
                
                double position = 0;
                
                
//                con.begin(Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR_NORMAL);
                consumer.vertex((double)blockCenter_width, (double)blockCenter_height_0, position).uv(f20, f21).color(255, 255, 255, 255).overlayCoords(OverlayTexture.NO_OVERLAY).endVertex();
                consumer.vertex((double)blockCenter_width_0, (double)blockCenter_height_0, position).uv(f19, f21).color(255, 255, 255, 255).overlayCoords(OverlayTexture.NO_OVERLAY).endVertex();
                consumer.vertex((double)blockCenter_width_0, (double)blockCenter_height, position).uv(f19, f22).color(255, 255, 255, 255).overlayCoords(OverlayTexture.NO_OVERLAY).endVertex();
                consumer.vertex((double)blockCenter_width, (double)blockCenter_height, position).uv(f20, f22).color(255, 255, 255, 255).overlayCoords(OverlayTexture.NO_OVERLAY).endVertex();
                
//                bufferbuilder..pos((double)blockCenter_width, (double)blockCenter_height_0, position).tex((double)f20, (double)f21).normal(0.0F, 0.0F, -1.0F).endVertex();
//                bufferbuilder.pos((double)blockCenter_width_0, (double)blockCenter_height_0, position).tex((double)f19, (double)f21).normal(0.0F, 0.0F, -1.0F).endVertex();
//                bufferbuilder.pos((double)blockCenter_width_0, (double)blockCenter_height, position).tex((double)f19, (double)f22).normal(0.0F, 0.0F, -1.0F).endVertex();
//                bufferbuilder.pos((double)blockCenter_width, (double)blockCenter_height, position).tex((double)f20, (double)f22).normal(0.0F, 0.0F, -1.0F).endVertex();
                
//                tesselator.end();
            }
        }
    }
}
