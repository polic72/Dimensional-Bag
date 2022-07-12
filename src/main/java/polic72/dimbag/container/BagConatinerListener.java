package polic72.dimbag.container;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.item.ItemStack;
import polic72.dimbag.Reference;


/**
 * Represents a listener for the {@link BagContainer}. It's used to check for any Bags inside of other Bags, because it's 
 * about to get very fun if so...
 * 
 * @author polic72
 */
public class BagConatinerListener implements ContainerListener
{
	@Override
	public void slotChanged(AbstractContainerMenu containerToSend, int slotIndex, ItemStack stack)
	{
		if (containerToSend instanceof BagContainer bagContainer)
		{
			if (slotIndex >= 0 && slotIndex < 27)
			{
				//TODO Consider making this based off of implementing an interface or something.
				if (stack.getItem().getRegistryName().equals(new ResourceLocation(Reference.MOD_ID, "bag")))
				{
					//DimensionalBag.LOGGER.info("oh boy...");
					
					bagContainer.rift(slotIndex);
				}
			}
		}
	}
	
	
	@Override
	public void dataChanged(AbstractContainerMenu containerMenu, int dataSlotIndex, int value)
	{
		//Do nothing.
	}
}
