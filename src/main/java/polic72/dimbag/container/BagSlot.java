package polic72.dimbag.container;

import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;


/**
 * A slot specifically used for not allowing the player to pick up the opened bag.
 * 
 * @author polic72
 */
public class BagSlot extends SlotItemHandler
{
	public BagSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition)
	{
		super(itemHandler, index, xPosition, yPosition);
	}
}
