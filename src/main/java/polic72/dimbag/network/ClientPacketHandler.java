package polic72.dimbag.network;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraftforge.network.NetworkEvent;


/**
 * Handles the packets sent to the client. This was suggested by the Forge
 * documentation, so why not?
 * 
 * @author polic72
 */
public class ClientPacketHandler
{
	/**
	 * Handles the velocity packet on the client.
	 * 
	 * @param message The {@link VelocityMessage} to process.
	 * @param context The context of the message to handle.
	 */
	public static void handleVelocityPacket(VelocityMessage message, Supplier<NetworkEvent.Context> context)
	{
		context.get().enqueueWork(() -> 
			Minecraft.getInstance().player.push(message.getX(), message.getY(), message.getZ())
		);
	}
}
