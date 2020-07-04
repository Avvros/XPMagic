package com.gatheredsatyr53.xpmagic;

import com.gatheredsatyr53.xpmagic.proxy.CommonProxy;
import com.gatheredsatyr53.xpmagic.registries.ItemsRegistry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod (modid = "xpmagic", name="XPMagic", version = "0.1")
public class XPMagic {
	
	@SidedProxy(clientSide = "com.gatheredsatyr53.xpmagic.proxy.ClientProxy", serverSide = "com.gatheredsatyr53.xpmagic.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	public static final CreativeTabs XPMagicTab = new CreativeTabs("xpmagic") {

		@Override
	    public ItemStack getTabIconItem()
	    {
	        return new ItemStack(ItemsRegistry.XPCocktail);
	    }
	};
	
	public void preInit(FMLPreInitializationEvent event)
	{
		proxy.preInit(event);
	}
	
	public void Init(FMLInitializationEvent event) {
		proxy.init(event);
	}
	
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}
	
}
