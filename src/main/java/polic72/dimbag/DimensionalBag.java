package polic72.dimbag;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
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
	 * The creative tab instance to use in the mod.
	 */
	public static final BagCreativeTab BAGTAB = new BagCreativeTab(Reference.MOD_ID);
	
	
	public DimensionalBag()
	{
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		
		ModItems.REGISTER.register(eventBus);
	}
}
