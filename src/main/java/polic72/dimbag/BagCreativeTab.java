package polic72.dimbag;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import polic72.dimbag.core.ModItems;


/**
 * The creative mode tab for this mod.
 * 
 * @author polic72
 */
public class BagCreativeTab extends CreativeModeTab
{
	/**
	 * Constructs a {@link BagCreativeTab} with the given label.
	 * 
	 * @param label The label of the tab. The lang file must set "itemGroup.[label]" to be seen correctly.
	 */
	public BagCreativeTab(String label)
	{
		super(label);
	}
	
	
	@Override
	public ItemStack makeIcon()
	{
		return new ItemStack(ModItems.BAG.get());
	}
}
