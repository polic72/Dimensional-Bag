package polic72.dimbag.inventory;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;


/**
 * Represents a capability for a bag. This is used for the storage of the bag.
 * 
 * @author polic72
 */
public class BagCapabilityProvider implements ICapabilityProvider
{
	private final ItemStack stack;
	
	private LazyOptional<IItemHandler> optional = LazyOptional.empty();
	
	
	/**
	 * Constructs a {@link BagCapabilityProvider} with the given item stack.
	 * 
	 * @param stack The {@link ItemStack} to link to this capability.
	 */
	public BagCapabilityProvider(ItemStack stack)
	{
		this.stack = stack;
	}
	
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side)
	{
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
//			cap.orEmpty(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, optional);
			
			//ItemStackHandler
			
			//ItemStack
			
			return optional.cast();
		}
		
		return LazyOptional.empty();
		
//		return cap.orEmpty(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, op);
	}
}
