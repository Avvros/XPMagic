package com.gatheredsatyr53.xpmagic.proxy;

import org.apache.logging.log4j.Logger;

import com.gatheredsatyr53.xpmagic.XPMagic;
import com.gatheredsatyr53.xpmagic.registries.CraftingRegistry;
import com.gatheredsatyr53.xpmagic.services.MachineGuiHandler;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy
{
	
	//public static Logger logger;
	
    public void preInit(FMLPreInitializationEvent event)
    {
    	CraftingRegistry.register();
    	//logger = event.getModLog();
    }

    public void init(FMLInitializationEvent event)
    {
    	NetworkRegistry.INSTANCE.registerGuiHandler(XPMagic.instance, new MachineGuiHandler());
    	//logger.info("Handlers registered!");
    	//XPMagic.initCompleted = true;
    }

    public void postInit(FMLPostInitializationEvent event) {

    }

}
