package com.gatheredsatyr53.xpmagic;

import com.gatheredsatyr53.xpmagic.proxy.CommonProxy;
import com.gatheredsatyr53.xpmagic.registries.ItemsRegistry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod (modid = XPMagic.MODID, name = XPMagic.NAME, version = XPMagic.VERSION)
public class XPMagic {
	
	public static final String MODID = "xpmagic";
	public static final String NAME = "XPMagic";
	public static final String VERSION = "0.1";
	
	@SidedProxy(clientSide = "com.gatheredsatyr53.xpmagic.proxy.ClientProxy", serverSide = "com.gatheredsatyr53.xpmagic.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	//public static volatile boolean initCompleted = false;
	
	@Instance
	public static XPMagic instance;
	
	public static final CreativeTabs XPMagicTab = new CreativeTabs("xpmagic") {

		@Override
	    public ItemStack getTabIconItem()
	    {
	        return new ItemStack(ItemsRegistry.XPCocktail);
	    }
	};
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		proxy.preInit(event);
	}
	
	@EventHandler
	public void Init(FMLInitializationEvent event) {
		proxy.init(event);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}
	
}
