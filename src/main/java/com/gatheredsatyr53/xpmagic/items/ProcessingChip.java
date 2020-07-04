package com.gatheredsatyr53.xpmagic.items;

import com.gatheredsatyr53.xpmagic.XPMagic;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ProcessingChip extends Item {

	public ProcessingChip() {
		super();
		maxStackSize = 64;
		this.setContainerItem(this);
		setContainerItem(this);
		this.setRegistryName("processing_chip");
        this.setUnlocalizedName("processing_chip");
        this.setCreativeTab(XPMagic.XPMagicTab);
	}
	
	
	
}
