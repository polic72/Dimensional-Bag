package polic72.dimbag.network;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

/**
 * Represents a velocity message to be sent by rifts to players to change their velocity.
 * 
 * @author polic72
 */
public class VelocityMessage
{
	private double x;
	private double y;
	private double z;
	
	
	/**
	 * Constructs a {@link VelocityMessage} with the given <i>x</i>, <i>y</i>, <i>z</i> velocity components.
	 * 
	 * @param x The x component of the velocity being sent.
	 * @param y The y component of the velocity to send.
	 * @param z The z component of the velocity to send.
	 */
	public VelocityMessage(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	
	/**
	 * Gets the x component of the velocity.
	 * 
	 * @return The x component of the velocity.
	 */
	public double getX()
	{
		return x;
	}
	
	
	/**
	 * Gets the y component of the velocity.
	 * 
	 * @return The y component of the velocity.
	 */
	public double getY()
	{
		return y;
	}
	
	
	/**
	 * Gets the z component of the velocity.
	 * 
	 * @return The z component of the velocity.
	 */
	public double getZ()
	{
		return z;
	}
	
	
	/**
	 * Encodes the velocity of the message in to the given <i>buffer</i>.
	 * 
	 * @param buffer The buffer of data to encode the velocity in to.
	 */
	public void encode(FriendlyByteBuf buffer)
	{
		buffer.writeDouble(x);
		buffer.writeDouble(y);
		buffer.writeDouble(z);
	}
	
	
	/**
	 * Decodes the velocity of the message from the given <i>buffer</i>.
	 * 
	 * @param buffer The buffer of data to decode the velocity from.
	 * @return The {@link VelocityMessage} created by the given <i>buffer</i>.
	 */
	public static VelocityMessage decode(FriendlyByteBuf buffer)
	{
		return new VelocityMessage(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
	}
	
	
	/**
	 * Handles the message based on the given <i>context</i>.
	 * 
	 * @param context The context of the message being sent.
	 */
	public void handle(Supplier<NetworkEvent.Context> context)
	{
		context.get().enqueueWork(() ->
			DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandler.handleVelocityPacket(this, context))
		);
		
		
		context.get().setPacketHandled(true);
	}
}
