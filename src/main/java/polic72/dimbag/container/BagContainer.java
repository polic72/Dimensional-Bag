package polic72.dimbag.container;

import java.util.UUID;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import polic72.dimbag.core.ModContainers;
import polic72.dimbag.items.BagItem;


public class BagContainer extends AbstractContainerMenu
{
//	private ItemStack itemStack;
	private IItemHandler itemInventory;
	private UUID bagID;
	
//	private Player player;
	private IItemHandler playerInventory;
	
	
	public BagContainer(int windowID, Inventory inventory, ItemStack itemStack)
	{
		super(ModContainers.BAG_CONTAINER.get(), windowID);
		
//		this.itemStack = itemStack;
		itemInventory = itemStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).resolve().get();
		bagID = itemStack.getTag().getUUID(BagItem.ID_TAG);
		
//		player = inventory.player;
		playerInventory = new InvWrapper(inventory);
		
		addSlotGrid(itemInventory, 0, 8, 18, 9, 18, 3, 18);
		
		layoutPlayerInventorySlots(8, 84);
	}
	
	
	@Override
	public ItemStack quickMoveStack(Player player, int index)
	{
		ItemStack workingStack = ItemStack.EMPTY;
		Slot slot = slots.get(index);
		
		if (slot != null && slot.hasItem())
		{
			ItemStack slotStack = slot.getItem();
			workingStack = slotStack.copy();
			
			if (index < 27)
			{
				if (!this.moveItemStackTo(slotStack, 27, slots.size(), true))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.moveItemStackTo(slotStack, 0, 27, false))
			{
				return ItemStack.EMPTY;
			}
			
			if (slotStack.isEmpty())
			{
				slot.set(ItemStack.EMPTY);
			}
			else
			{
				slot.setChanged();
			}
		}
		
		return workingStack;
		
		
//		ChestMenu
//		
////		super.quickMoveStack(player, index);
//		
////		Slot slot = slots.get(index);
////		
////		return super.quickMoveStack(player, index);
//		ItemStack workingStack = ItemStack.EMPTY;
//		Slot slot = this.slots.get(index);
//		
//		if (slot != null && slot.hasItem())
//		{
//			ItemStack selectedStack = slot.getItem();
//			workingStack = selectedStack.copy();
//			
//			if (index == 0)
//			{
//				if (!this.moveItemStackTo(selectedStack, 1, 37, true))
//				{
//					return ItemStack.EMPTY;
//				}
////				slot.onQuickCraft(selectedStack, workingStack);
//			}
//			else
//			{
//				if (!this.moveItemStackTo(selectedStack, 0, 1, false))
//				{
//					return ItemStack.EMPTY;
//				}
//				else if (index < 28)
//				{
//					if (!this.moveItemStackTo(selectedStack, 28, 37, false))
//					{
//						return ItemStack.EMPTY;
//					}
//				}
//				else if (index < 37 && !this.moveItemStackTo(selectedStack, 1, 28, false))
//				{
//					return ItemStack.EMPTY;
//				}
//			}
//			
//			if (selectedStack.isEmpty())
//			{
//				slot.set(ItemStack.EMPTY);
//			}
//			else
//			{
//				slot.setChanged();
//			}
//			
//			if (selectedStack.getCount() == workingStack.getCount())
//			{
//				return ItemStack.EMPTY;
//			}
//			
//			slot.onTake(playerIn, selectedStack);
//		}
//		
//		return workingStack;
	}
	
	
	@Override
	public boolean stillValid(Player player)
	{
		return true;
	}
	
	
	/**
	 * Adds a row of slots.
	 * 
	 * @param handler The {@link IItemHandler} that the slots are getting their
	 *                ItemStacks from.
	 * @param index   The index to start populating the slot row from.
	 * @param x       The X position of the first slot.
	 * @param y       The Y position of the first slot.
	 * @param count   The number of slots to create.
	 * @param dx      The space between the slots.
	 * @return The index of the next slot immediately after this row.
	 */
	private int addSlotRow(IItemHandler handler, int index, int x, int y, int count, int dx)
	{
		for (int i = 0; i < count; i++)
		{
			addSlot(new BagSlot(handler, index, x, y, bagID));
			
			x += dx;
			index++;
		}
		
		return index;
	}
	
	
	/**
	 * Adds a row of slots.
	 * 
	 * @param handler The {@link IItemHandler} that the slots are getting their
	 *                ItemStacks from.
	 * @param index   The index to start populating the slot row from.
	 * @param x       The X position of the first slot.
	 * @param y       The Y position of the first slot.
	 * @param xCount  The number of slots to create horizontally.
	 * @param dx      The space between the slots horizontally.
	 * @param yCount  The number of slots to create vertically.
	 * @param dy      The space between the slots vertically.
	 * @return The index of the next slot immediately after this grid.
	 */
	private int addSlotGrid(IItemHandler handler, int index, int x, int y, int xCount, int dx, int yCount, int dy)
	{
		for (int j = 0; j < yCount; j++)
		{
			index = addSlotRow(handler, index, x, y, xCount, dx);
			y += dy;
		}
		
		return index;
	}
	
	
	/**
	 * Creates a player inventory as is standard in most GUIs that contain it.
	 * 
	 * @param left The position to start placing the inventory horizontally.
	 * @param top  The position to start placing the inventory vertically.
	 */
	private void layoutPlayerInventorySlots(int left, int top)
	{
		// Player inventory:
		addSlotGrid(playerInventory, 9, left, top, 9, 18, 3, 18);
		
		// Hotbar:
		top += 58;
		addSlotRow(playerInventory, 0, left, top, 9, 18);
	}
}
