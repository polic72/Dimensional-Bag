package polic72.dimbag.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
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
	
	
	/**
	 * The render type for the rift.
	 */
	private static final RenderType RENDER_TYPE = RenderType.entityCutout(RIFT_TEXTURE);
	
	
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
		
		float scale = 1F;
		matrixStack.scale(scale, scale, scale);
		
		matrixStack.translate(0, rift.getBbHeight() - 1.0F, 0);
		
		matrixStack.mulPose(entityRenderDispatcher.cameraOrientation());
		
		matrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
		
		PoseStack.Pose poseStack_pose = matrixStack.last();
        Matrix4f matrix4f = poseStack_pose.pose();
        Matrix3f matrix3f = poseStack_pose.normal();
        
        VertexConsumer vertexConsumer = buffer.getBuffer(RENDER_TYPE);
        
        vertex(vertexConsumer, matrix4f, matrix3f, packedLight, 0.0F, 0.0F, 0, 1);
        vertex(vertexConsumer, matrix4f, matrix3f, packedLight, 1.0F, 0.0F, 1, 1);
        vertex(vertexConsumer, matrix4f, matrix3f, packedLight, 1.0F, 1.0F, 1, 0);
        vertex(vertexConsumer, matrix4f, matrix3f, packedLight, 0.0F, 1.0F, 0, 0);
		
		matrixStack.popPose();
		
		
		super.render(rift, entityYaw, partialTicks, matrixStack, buffer, packedLight);
	}
	
	
	/**
	 * Applies a vertex with full color and no overlay with all the given parameters.
	 * 
	 * @param consumer The {@link VertexConsumer} to add the vertex to.
	 * @param matrix4f The 4D matrix for the 3D rotation of the vertex.
	 * @param matrix3f The 3d matrix for the normal vector of the lighting.
	 * @param packedLight The light level of the entity.
	 * @param x The X coordinate of the vertex.
	 * @param y The Y coordinate of the vertex.
	 * @param textureU The U coordinate of the texture.
	 * @param textureV The V coordinate of the texture.
	 */
	private static void vertex(VertexConsumer consumer, Matrix4f matrix4f, Matrix3f matrix3f, 
			int packedLight, float x, float y, float textureU, float textureV)
	{
		consumer.vertex(matrix4f, x - 0.5F, y - 0.5F, 0.0F).color(255, 255, 255, 255)
			.uv(textureU, textureV).overlayCoords(OverlayTexture.NO_OVERLAY)
			.uv2(packedLight).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
	}
}
