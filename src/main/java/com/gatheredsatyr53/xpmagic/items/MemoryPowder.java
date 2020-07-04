package com.gatheredsatyr53.xpmagic.items;

import com.gatheredsatyr53.xpmagic.XPMagic;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class MemoryPowder extends Item {
	
	public MemoryPowder() {
		maxStackSize = 64;
		this.setContainerItem(this);
		this.setRegistryName("memory_powder");
        this.setUnlocalizedName("memory_powder");
        this.setCreativeTab(XPMagic.XPMagicTab);
	}
	
}
