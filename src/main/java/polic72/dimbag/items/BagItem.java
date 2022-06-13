package polic72.dimbag.items;

import java.util.UUID;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.CraftingTableBlock;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.network.NetworkHooks;
import polic72.dimbag.DimensionalBag;
import polic72.dimbag.inventory.BagCapabilityProvider;


/**
 * The bag item class. Where the logic for using the item remains.
 * 
 * @author polic72
 */
public class BagItem extends Item
{
	/**
	 * The name of the NBT tag for telling different Bags.
	 */
	public static final String ID_TAG = "ID";
	
	
	/**
	 * The display name of the screen of all dimensional bags.
	 */
	public static final String SCREEN_BAG = "screen.dimbag.bag";
	
	
	public BagItem(Properties properties)
	{
		super(properties);
	}
	
	
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt)
	{
//		CompoundTag tag = stack.getTag();
//		
//		if (tag == null)
//		{
//			tag = new CompoundTag();
//		}
	
//		nbt.putUUID(ID_TAG, UUID.randomUUID());
		
		return new BagCapabilityProvider();
	}
	
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand)
	{
		if (!level.isClientSide)
		{
//			DimensionalBag.LOGGER.info("Bag used!");
			
			LazyOptional<IItemHandler> optional = player.getItemInHand(interactionHand)
					.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
			
			DimensionalBag.LOGGER.info(Integer.toString(optional.resolve().get().getSlots()));
			
			NetworkHooks.openGui((ServerPlayer)player, new MenuProvider()
			{
				@Override
				public AbstractContainerMenu createMenu(int windowID, Inventory playerInventory, Player player)
				{
					// TODO Auto-generated method stub
					return null;
				}
				
				
				@Override
				public Component getDisplayName()
				{
					return new TranslatableComponent(SCREEN_BAG);
				}
			});
//			MenuScreens.
			
			// CraftingTableBlock
		}
		
		
		return super.use(level, player, interactionHand);
	}
}
