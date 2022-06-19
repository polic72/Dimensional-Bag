package polic72.dimbag.core;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import polic72.dimbag.Reference;
import polic72.dimbag.container.BagScreen;
import polic72.dimbag.entity.render.RiftRenderer;


/**
 * A storage hub for all client-specific registrations in the mod.
 * 
 * @author polic72
 */
@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientRegistries
{
	@SubscribeEvent
	public static void clientInit(FMLClientSetupEvent event)
	{
		event.enqueueWork(() ->
		{
			MenuScreens.register(ModContainers.BAG_CONTAINER.get(), BagScreen::new);
		});
	}
	
	
	@SubscribeEvent
	public static void registerEntityRenderers(RegisterRenderers event)
	{
		event.registerEntityRenderer(ModEntities.RIFT.get(), RiftRenderer::new);
	}
}
