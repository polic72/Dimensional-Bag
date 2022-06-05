package polic72.dimbag.core;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import polic72.dimbag.DimensionalBag;
import polic72.dimbag.Reference;
import polic72.dimbag.items.BagItem;


/**
 * A storage hub for all of the items in the mod.
 * 
 * @author polic72
 */
public class ModItems
{
	/**
	 * The main resister for the items of the mod.
	 */
	public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS,
			Reference.MOD_ID);
	
	/**
	 * The bag item.
	 */
	public static final RegistryObject<Item> BAG = REGISTER.register("bag", () -> new BagItem(
			new Properties().stacksTo(1).tab(DimensionalBag.BAGTAB)));
}
