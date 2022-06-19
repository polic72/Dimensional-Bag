package polic72.dimbag;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import polic72.dimbag.core.ModContainers;
import polic72.dimbag.core.ModEntities;
import polic72.dimbag.core.ModItems;


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
		
//		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> eventBus.addListener(DimensionalBag::clientInit));
	}
}
