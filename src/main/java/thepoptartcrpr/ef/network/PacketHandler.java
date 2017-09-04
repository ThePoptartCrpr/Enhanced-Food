package thepoptartcrpr.ef.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
	
	public static SimpleNetworkWrapper INSTANCE;
	
	private static int ID = 0;
	
	private static int nextID() {
		return ID++;
	}
	
	public static void registerMessages(String channelName) {
		INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);
		
		// Server-Side
		INSTANCE.registerMessage(PacketGetWorker.Handler.class, PacketGetWorker.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PacketGetBurnTime.Handler.class, PacketGetBurnTime.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PacketGetCookTime.Handler.class, PacketGetCookTime.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PacketGetCuttingBoard.Handler.class, PacketGetCuttingBoard.class, nextID(), Side.SERVER);
		
		// Client-Side
		INSTANCE.registerMessage(PacketReturnWorker.Handler.class, PacketReturnWorker.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketReturnBurnTime.Handler.class, PacketReturnBurnTime.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketReturnCookTime.Handler.class, PacketReturnCookTime.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketReturnCuttingBoard.Handler.class, PacketReturnCuttingBoard.class, nextID(), Side.CLIENT);
	}

}
