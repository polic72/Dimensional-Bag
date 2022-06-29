package polic72.dimbag.util;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.datafix.fixes.BlockStateData;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.portal.PortalForcer;
import net.minecraft.world.phys.shapes.CubeVoxelShape;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.registries.DeferredRegister;
import polic72.dimbag.DimensionalBag;


/**
 * Represents a helper for Rift teleportation.
 * 
 * @author polic72
 */
public class TeleportHelper
{
	/**
	 * The most the {@link TeleportHelper} will try to look for a good teleportation location.
	 * @see TeleportHelper#pickDimPos(MinecraftServer)
	 */
	private static final int PRECAUTION = 150;
	
	
	/**
	 * The farthest coordinate in the X/Z plane that entities will be teleported to.
	 */
	private static final int MAX_COORDINATE = 10000;
	
	
	/**
	 * Set of blocks that are deemed dangerous to teleport to, even if a full block.
	 */
	private static final HashSet<ResourceLocation> DANGER_BLOCKS = new HashSet<>();
	
	
	public static void pickDimPos(MinecraftServer server)
	{
		@SuppressWarnings("unchecked")
		ResourceKey<Level>[] levelKeys = server.levelKeys().toArray(new ResourceKey[0]);
		
		
		Random random = new Random();
		
		for (int i = 0; i < PRECAUTION; ++i)
		{
			ResourceKey<Level> chosenKey = levelKeys[random.nextInt(levelKeys.length)];
			
			ServerLevel level = server.getLevel(chosenKey);
			
//			level.chunk
//			level.getHeight(Types.WORLD_SURFACE_WG, i, i)
		}
	}
	
	
	/**
	 * Checks if the given <i>pos</i> in the given <i>level</i> is a safe location to teleport to.
	 * <p/>
	 * "Safe" means that there is air at, a full-block directly underneath, and air directly above the given <i>pos</i>. 
	 * Any blocks inside the internal "danger blocks" determined in the config are avoided during teleportation.This is 
	 * safe enough for players and most other mobs to not immediately die, but it doesn't necessarily guarantee a fun time 
	 * immediately after...
	 * 
	 * @param level The level to check.
	 * @param pos The block position to check.
	 * @return True if the given <i>pos</i> in the given <i>level</i> is a safe location to teleport to. False otherwise. 
	 * Always false on the client logical side.
	 */
	public static boolean isSafeLocation(Level level, BlockPos pos)
	{
		if (level.isClientSide)
		{
			return false;
		}
		
		
		if (DANGER_BLOCKS.contains(level.getBlockState(pos.below()).getBlock().getRegistryName()))
		{
			return false;
		}
		
		
		if (Block.isFaceFull(level.getBlockState(pos.below()).getCollisionShape(level, pos.below()), Direction.UP))
		{
			return level.getBlockState(pos).isAir() && level.getBlockState(pos.above()).isAir();
		}
		
		
		return false;
	}
	
	
	/**
	 * Gets a safe {@link BlockPos} from the given <i>level</i> and <i>pos</i>.
	 * <p/>
	 * Starts at the given <i>pos</i>, looks up on the y coordinate until world height is met, loops to the bottom, then 
	 * back up to <i>pos</i>. If any safe
	 * 
	 * @param level The level to check for a safe location from.
	 * @param pos The initial position to look for a safe location from.
	 * @return The {@link BlockPos} that is safe from the given <i>pos</i>. Null if no safe location could be found.
	 * 
	 * @see TeleportHelper#isSafeLocation(Level, BlockPos)
	 */
	public static BlockPos scanSafeLocation(Level level, BlockPos pos)
	{
		//Loop up blockPos' and try to find a safe location. Return null if you get back to the given pos.
		
		return null;
	}
	
	
	/**
	 * Tries to add the given registry name of a block to the internal danger blocks.
	 * 
	 * @param blockRegistryName The registry name of a block.
	 * @return True if the block was successfully added to the danger blocks. False otherwise.
	 */
	public static boolean addDangerBlock(ResourceLocation blockRegistryName)
	{
		return DANGER_BLOCKS.add(blockRegistryName);
	}
}
