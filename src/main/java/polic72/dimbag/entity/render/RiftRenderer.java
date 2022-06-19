package polic72.dimbag.entity.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import polic72.dimbag.Reference;
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
	
	
	public RiftRenderer(Context context)
	{
		super(context);
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
		
		//
		
		
		super.render(rift, entityYaw, partialTicks, matrixStack, buffer, packedLight);
	}
}
