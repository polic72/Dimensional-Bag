package polic72.dimbag.network;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

/**
 * Represents a velocity message to be sent by rifts to players to change their velocity.
 * 
 * @author polic72
 */
public class VelocityMessage
{
	/**
	 * Encodes the velocity of the message in to the given <i>buffer</i>.
	 * 
	 * @param buffer The buffer of data to encode the velocity in to.
	 */
	public void encode(FriendlyByteBuf buffer)
	{
		//
	}
	
	
	/**
	 * Decodes the velocity of the message from the given <i>buffer</i>.
	 * 
	 * @param buffer The buffer of data to decode the velocity from.
	 * @return The {@link VelocityMessage} created by the given <i>buffer</i>.
	 */
	public static VelocityMessage decode(FriendlyByteBuf buffer)
	{
		return null;
	}
	
	
	/**
	 * Handles the message based on the given <i>context</i>.
	 * 
	 * @param context The context of the message being sent.
	 */
	public void handle(Supplier<NetworkEvent.Context> context)
	{
		//
	}
}
