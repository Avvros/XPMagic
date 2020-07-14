package com.gatheredsatyr53.xpmagic.containers.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class XPKMFuelSlot extends SlotItemHandler {

	public XPKMFuelSlot(IItemHandler inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		// TODO Auto-generated method stub
		return TileEntityFurnace.isItemFuel(stack);
	}

	@Override
	public int getItemStackLimit(ItemStack stack) {
		// TODO Auto-generated method stub
		return super.getItemStackLimit(stack);
	}
	
	

}
