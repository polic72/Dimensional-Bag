package polic72.dimbag.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import polic72.dimbag.Reference;


/**
 * Represents the network handler for the mod.
 * 
 * @author polic72
 */
public class PacketHandler
{
	/**
	 * The protocol version for the network communication.
	 */
	private static final String PROTOCOL_VERSION = "1";
	
	
	/**
	 * The instance of the network channel.
	 */
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
		new ResourceLocation(Reference.MOD_ID, "main"), 
		() -> PROTOCOL_VERSION, 
		PROTOCOL_VERSION::equals, 
		PROTOCOL_VERSION::equals);
	
	
	
	
	private static boolean initialized = false;
	
	
	/**
	 * Initializes the packet handler by registering all the mod's packets into it. Can only be run once.
	 */
	public static void init()
	{
		if (initialized)
		{
			return;
		}
		
		
		int id = 0;
		
		
		INSTANCE.registerMessage(id++, VelocityMessage.class, VelocityMessage::encode, 
			VelocityMessage::decode, VelocityMessage::handle);
		
		
		initialized = true;
	}
}
