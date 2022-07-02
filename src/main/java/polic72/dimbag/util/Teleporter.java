package polic72.dimbag.util;

import java.util.function.Function;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;


/**
 * Represents a teleporter used to change the dimensions of entities.
 * 
 * @author polic72
 */
public class Teleporter implements ITeleporter
{
	private BlockPos pos;
	
	
	/**
	 * Constructs a {@link Teleporter} with the given <i>pos</i>.
	 * 
	 * @param pos The position to teleport entities to.
	 */
	public Teleporter(BlockPos pos)
	{
		this.pos = pos;
	}
	
	
	@Override
	public PortalInfo getPortalInfo(Entity entity, ServerLevel destWorld,
			Function<ServerLevel, PortalInfo> defaultPortalInfo)
	{
		return new PortalInfo(new Vec3(pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5), 
			Vec3.ZERO, entity.getYRot(), entity.getXRot());
	}
}
