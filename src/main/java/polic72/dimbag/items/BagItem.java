package polic72.dimbag.items;

import java.util.UUID;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.network.NetworkHooks;
import polic72.dimbag.container.BagContainer;
import polic72.dimbag.core.ModEntities;
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
			ItemStack workingItemStack = getWorkingItemStack(player);
//			CompoundTag nbt = workingItemStack.serializeNBT();
			CompoundTag nbt = workingItemStack.getTag();
			
			if (nbt == null)
			{
				nbt = new CompoundTag();
			}
			
			if (!nbt.hasUUID(ID_TAG))
			{
				nbt.putUUID(ID_TAG, UUID.randomUUID());
				
//				workingItemStack.deserializeNBT(nbt);
				workingItemStack.setTag(nbt);
			}
			
			ModEntities.RIFT.get().spawn((ServerLevel)level, null, null, player.getOnPos(), 
					MobSpawnType.EVENT, false, false);
			
			
			MenuProvider containerProvider = new MenuProvider()
			{
				@Override
				public AbstractContainerMenu createMenu(int windowID, Inventory playerInventory, Player player)
				{
					return new BagContainer(windowID, playerInventory, workingItemStack);
				}
				
				
				@Override
				public Component getDisplayName()
				{	
					return new TranslatableComponent(SCREEN_BAG);
				}
			};
			
			NetworkHooks.openGui((ServerPlayer)player, containerProvider, 
					(FriendlyByteBuf t) -> t.writeItem(workingItemStack));
		}
		
		
		return super.use(level, player, interactionHand);
	}
	
	
	/**
	 * Gets the {@link ItemStack} that should be worked with. Needed thanks to dual-wielding.
	 * <p/>
	 * Tries to use the main hand first.
	 * 
	 * @param player The player to get the working ItemStack from.
	 * @return The {@link ItemStack} to work with. Always null on the client side.
	 */
	protected ItemStack getWorkingItemStack(Player player)
	{
		if (!player.level.isClientSide)
		{
			ItemStack itemStack = player.getMainHandItem();
			Item item = itemStack.getItem();
			
			if (item != null && item instanceof BagItem)
			{
				return itemStack;
			}
			
			
			itemStack = player.getOffhandItem();
			item = itemStack.getItem();
			
			if (item != null && item instanceof BagItem)
			{
				return itemStack;
			}
		}
		
		return null;
	}
}
