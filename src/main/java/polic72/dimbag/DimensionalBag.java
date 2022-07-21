package polic72.dimbag;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;

import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;

import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.world.ForgeChunkManager;
import net.minecraftforge.common.world.ForgeChunkManager.LoadingValidationCallback;
import net.minecraftforge.common.world.ForgeChunkManager.TicketHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import polic72.dimbag.core.ModContainers;
import polic72.dimbag.core.ModEntities;
import polic72.dimbag.core.ModItems;
import polic72.dimbag.core.ModSounds;
import polic72.dimbag.network.PacketHandler;


/**
 * The entry point of the mod.
 * 
 * @author polic72
 */
@Mod(Reference.MOD_ID)
public class DimensionalBag
{
	/**
	 * The logger of the mod.
	 */
	public static final Logger LOGGER = LogUtils.getLogger();
	
	
	/**
	 * The creative tab instance to use in the mod.
	 */
	public static final BagCreativeTab BAGTAB = new BagCreativeTab(Reference.MOD_ID);
	
	
	public DimensionalBag()
	{
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		
		ModItems.REGISTER.register(eventBus);
		ModContainers.REGISTER.register(eventBus);
		ModEntities.REGISTER.register(eventBus);
		ModSounds.REGISTER.register(eventBus);
		
		PacketHandler.init();
		
		//TODO Add the danger blocks to the TeleportHelper.
		
//		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> eventBus.addListener(DimensionalBag::clientInit));
		
		ForgeChunkManager.setForcedChunkLoadingCallback(Reference.MOD_ID, (level, ticketHelper) -> {
			Map<UUID, Pair<LongSet, LongSet>> scs = ticketHelper.getEntityTickets();
			
			for (UUID id : scs.keySet())
			{
				ticketHelper.removeAllTickets(id);
			}
			
			Set<BlockPos> bruh = ticketHelper.getBlockTickets().keySet();
			
			for (BlockPos pos : bruh)
			{
				ticketHelper.removeAllTickets(pos);
			}
		});
	}
}
