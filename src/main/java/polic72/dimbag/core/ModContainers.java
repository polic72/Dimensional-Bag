package polic72.dimbag.core;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import polic72.dimbag.Reference;
import polic72.dimbag.container.BagContainer;


/**
 * A storage hub for all of the containers in the mod.
 * 
 * @author polic72
 */
public class ModContainers
{
	/**
	 * The main resister for the containers of the mod.
	 */
	public static final DeferredRegister<MenuType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.CONTAINERS,
			Reference.MOD_ID);
	
	
	public static final RegistryObject<MenuType<BagContainer>> BAG_CONTAINER = REGISTER.register("bag",
			() -> IForgeMenuType.create((windowId, inv, data) -> new BagContainer(windowId, inv, data.readItem())));
}
