package polic72.dimbag.container;

import java.util.UUID;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import polic72.dimbag.items.BagItem;


/**
 * A slot specifically used for not allowing the player to pick up the opened bag.
 * 
 * @author polic72
 */
public class BagSlot extends SlotItemHandler
{
	/**
	 * Have to store the index of the slot myself because someone thought it'd be a good idea to make 
	 * it private in the other class.
	 */
	protected int index2;
	
	
	/**
	 * The UUID that represents the actual bag being opened.
	 */
	protected UUID bagID;
	
	
	public BagSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, UUID bagID)
	{
		super(itemHandler, index, xPosition, yPosition);
		
		this.index2 = index;
		this.bagID = bagID;
	}
	
	
	@Override
	public boolean mayPickup(Player playerIn)
	{
		ItemStack itemStack = getItemHandler().extractItem(index2, 1, true);
		
		if (itemStack.isEmpty())
		{
			return false;
		}
		
		
		if (itemStack.getItem() instanceof BagItem)
		{
			return !itemStack.getTag().getUUID(BagItem.ID_TAG).equals(bagID);
		}
		
		
		return true;
	}
}
