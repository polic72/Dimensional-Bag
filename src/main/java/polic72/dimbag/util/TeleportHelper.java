package polic72.dimbag.util;

import java.util.Random;
import java.util.Set;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.portal.PortalForcer;
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
	 * This is safe enough for players and most other mobs to not immediately die, but it doesn't necessarily guarantee a fun 
	 * time immediately after...
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
		
		
//		level.getBlockState(pos.below()).has.canOcclude();
		
//		if (level.getBlockState(pos.below()).isValidSpawn(level, pos, null).hasLargeCollisionShape())
//		{
//			
//		}
		
		return false;
	}
}
