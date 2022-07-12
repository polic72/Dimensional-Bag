package polic72.dimbag.container;

import java.util.UUID;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import polic72.dimbag.DimensionalBag;
import polic72.dimbag.core.ModContainers;
import polic72.dimbag.core.ModEntities;
import polic72.dimbag.items.BagItem;


public class BagContainer extends AbstractContainerMenu
{
//	private ItemStack itemStack;
	private IItemHandler itemInventory;
	private UUID bagID;
	
//	private Player player;
	private IItemHandler playerInventory;
	
	
	private int riftIndex;
	
	
	public BagContainer(int windowID, Inventory inventory, ItemStack itemStack)
	{
		super(ModContainers.BAG_CONTAINER.get(), windowID);
		
//		this.itemStack = itemStack;
		itemInventory = itemStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).resolve().get();
		bagID = itemStack.getTag().getUUID(BagItem.ID_TAG);
		
//		player = inventory.player;
		playerInventory = new InvWrapper(inventory);
		
		
		riftIndex = -1;
		
		
		addSlotGrid(itemInventory, 0, 8, 18, 9, 18, 3, 18);
		
		layoutPlayerInventorySlots(8, 84);
		
		
		addSlotListener(new BagConatinerListener());
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
	}
	
	
	@Override
	public boolean stillValid(Player player)
	{
		if (riftIndex == -1)
		{
			return true;
		}
		
		
		selfDestruct((ServerLevel)player.level, player.getOnPos().above());
		
		
		return false;
	}
	
	
	/**
	 * Spawns a rift at the player's location using the <i>offendingSlotIndex</i> as the index of the Bag inside this bag.
	 * 
	 * @param offendingSlotIndex The index of the slot that has the Bag creating a rift with.
	 */
	public void rift(int offendingSlotIndex)
	{
		riftIndex = offendingSlotIndex;
	}
	
	
	/**
	 * Summons a rift in the given <i>level</i> at the given <i>pos</i>. Destroys the held bag (as well as the one at this 
	 * {@link BagContainer#riftIndex}, dropping all of their items as well.
	 * 
	 * @param level The level to create the rift and drop items in.
	 * @param pos The position to create the rift and drop items at.
	 */
	private void selfDestruct(ServerLevel level, BlockPos pos)
	{
		IItemHandler innerItems = slots.get(riftIndex).getItem().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			.resolve().get();
		
		//Drop inner bag.
		for (int i = 0; i < innerItems.getSlots(); ++i)
		{
			ItemStack checker = innerItems.getStackInSlot(i);
			
			if (!checker.isEmpty())
			{
				ItemStack stack = innerItems.extractItem(i, checker.getCount(), false);
				
				//Find a way to drop the item as an entity.
			}
		}
		
		
		//Drop this bag.
		for (int i = 0; i < 27; ++i)
		{
			if (i != riftIndex)
			{
				//Drop shit
			}
		}
		
		
		ModEntities.RIFT.get().spawn(level, null, null, pos, MobSpawnType.EVENT, false, false);
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
