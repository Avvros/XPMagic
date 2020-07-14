package com.gatheredsatyr53.xpmagic.containers.slots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class XPKMOutputSlot extends SlotItemHandler {

	private final EntityPlayer player;
	private int removeCount;
	
	public XPKMOutputSlot(EntityPlayer player, IItemHandler inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
		this.player = player;
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack) {
		// TODO Auto-generated method stub
		this.onCrafting(stack);
		super.onTake(thePlayer, stack);
		return stack; 
	}

	@Override
	public ItemStack decrStackSize(int amount) {
		// TODO Auto-generated method stub
		if (this.getHasStack()) this.removeCount += Math.min(amount, this.getStack().getCount());
		return super.decrStackSize(amount);
	}

	
	
}
