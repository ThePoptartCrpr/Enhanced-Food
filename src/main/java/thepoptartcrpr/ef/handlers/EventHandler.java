package thepoptartcrpr.ef.handlers;

import net.minecraftforge.common.MinecraftForge;
import thepoptartcrpr.ef.utils.Utils;
import thepoptartcrpr.ef.events.PlayerEvents;

public class EventHandler {
	
	public void registerEvents() {
		MinecraftForge.EVENT_BUS.register(new PlayerEvents());
		
		Utils.getConsole().info("Registered events");
	}

}
