package com.thepoptartcrpr.eh;

import com.thepoptartcrpr.eh.proxy.CommonProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = EHGlobal.MOD_ID, name = EHGlobal.MOD_NAME, version = EHGlobal.VERSION)
public class Main {
	
	@Instance(EHGlobal.MOD_ID)
	public static Main instance;
	
	@SidedProxy(clientSide = EHGlobal.EH_CLIENT_PROXY, serverSide = EHGlobal.EH_COMMON_PROXY)
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent preEvent) {
		
		this.proxy.preInit(preEvent);
		
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		
		this.proxy.init(event);
		
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent postEvent) {
		
		this.proxy.postInit(postEvent);
		
	}

}
