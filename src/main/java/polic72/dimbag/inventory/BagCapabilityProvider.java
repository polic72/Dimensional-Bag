package polic72.dimbag.inventory;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;


/**
 * Represents a capability for a bag. This is used for the storage of the bag.
 * 
 * @author polic72
 */
public class BagCapabilityProvider implements ICapabilitySerializable<CompoundTag>
{
	//private final ItemStack stack;
	
	private ItemStackHandler stackHandler;
	
	private final LazyOptional<IItemHandler> optional = LazyOptional.of(() -> stackHandler);
	
	
	/**
	 * Constructs a {@link BagCapabilityProvider} with the given item stack.
	 * 
	 * @param stack The {@link ItemStack} to link to this capability.
	 */
	public BagCapabilityProvider(/*ItemStack stack*/)
	{
		//this.stack = stack;
		
		stackHandler = new ItemStackHandler(27);
	}
	
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side)
	{
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			//ItemStackHandler
			
			//ItemStack
			
			return optional.cast();
		}
		
		//return LazyOptional.empty();
		
		return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(cap, optional);
	}


	@Override
	public CompoundTag serializeNBT()
	{
		return stackHandler.serializeNBT();
	}


	@Override
	public void deserializeNBT(CompoundTag nbt)
	{
		stackHandler.deserializeNBT(nbt);
	}
}
