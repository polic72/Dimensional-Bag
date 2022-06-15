package polic72.dimbag.container;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import polic72.dimbag.Reference;


/**
 * Represents the screen that a player will actually see when they open the bag.
 * 
 * @author polic72
 */
public class BagScreen extends AbstractContainerScreen<BagContainer>
{
	/**
	 * The location of the GUI's texture.
	 */
	private static final ResourceLocation GUI = new ResourceLocation(Reference.MOD_ID, "textures/gui/bag.png");
	
	
	public BagScreen(BagContainer container, Inventory inventory, Component name)
	{
		super(container, inventory, name);
	}
	
	
	@Override
	public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks)
	{
		this.renderBackground(matrixStack);
		
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		
		this.renderTooltip(matrixStack, mouseX, mouseY);
	}
	
	
	@Override
	protected void renderLabels(PoseStack matrixStack, int x, int y)
	{
		super.renderLabels(matrixStack, x, y);
		
		//font.draw(matrixStack, title, x, y, 0x404040);
	}
	
	
	@Override
	protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY)
	{
		RenderSystem.setShaderTexture(0, GUI);
		
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        
        this.blit(matrixStack, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
	}
}
