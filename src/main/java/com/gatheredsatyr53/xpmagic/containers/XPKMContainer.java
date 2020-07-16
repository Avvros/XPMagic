package com.gatheredsatyr53.xpmagic.containers;

import com.gatheredsatyr53.xpmagic.containers.slots.XPKMFuelSlot;
import com.gatheredsatyr53.xpmagic.containers.slots.XPKMOutputSlot;
import com.gatheredsatyr53.xpmagic.items.MemoryPowder;
import com.gatheredsatyr53.xpmagic.tiles.XPKMTileEntity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.SlotItemHandler;

public class XPKMContainer extends Container {
	
	private final XPKMTileEntity tileentity;
	public volatile int cookTime, totalCookTime, burnTime, currentBurnTime;

	public XPKMContainer(InventoryPlayer player, XPKMTileEntity tileentity) {
		this.tileentity = tileentity;
		this.tileentity.setUser(player.player);
		
		this.addSlotToContainer(new SlotItemHandler(tileentity.inventory, 0, 49, 19)); //Bottle Slot
		this.addSlotToContainer(new XPKMFuelSlot(tileentity.inventory, 1, 49, 39)); //Fuel Slot
		this.addSlotToContainer(new SlotItemHandler(tileentity.inventory, 2, 49, 59)); //MemoryPowder Slot
		this.addSlotToContainer(new XPKMOutputSlot(player.player, tileentity.inventory, 3, 116, 39)); //Output Slot
	

        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k)
        {
            this.addSlotToContainer(new Slot(player, k, 8 + k * 18, 142));
        }
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		// TODO Auto-generated method stub
		return this.tileentity.isUsableByPlayer(playerIn);
	}

	@Override
	public void detectAndSendChanges() {
		// TODO Auto-generated method stub
		super.detectAndSendChanges();
		
		for (int i = 0; i < this.listeners.size(); ++i)
        {
            IContainerListener icontainerlistener = this.listeners.get(i);

            if (this.cookTime != this.tileentity.cookTime)
            {
                icontainerlistener.sendWindowProperty(this, 2, this.tileentity.cookTime);
            }

            if (this.burnTime != this.tileentity.furnaceBurnTime)
            {
                icontainerlistener.sendWindowProperty(this, 0, this.tileentity.furnaceBurnTime);
            }

            if (this.currentBurnTime != this.tileentity.currentItemBurnTime)
            {
                icontainerlistener.sendWindowProperty(this, 1, this.tileentity.currentItemBurnTime);
            }

            if (this.totalCookTime != this.tileentity.totalCookTime)
            {
                icontainerlistener.sendWindowProperty(this, 3, this.tileentity.totalCookTime);
            }
        }

        this.cookTime = this.tileentity.cookTime; //Data field 2
        this.burnTime = this.tileentity.furnaceBurnTime; //Data field 0
        this.currentBurnTime = this.tileentity.currentItemBurnTime; //Data field 1
        this.totalCookTime = this.tileentity.totalCookTime; //Data field 3
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		// TODO Auto-generated method stub
		switch(id) {
		case 0:
			this.tileentity.furnaceBurnTime = data;
			return;
		case 1:
			this.tileentity.currentItemBurnTime = data;
			return;
		case 2:
			this.tileentity.cookTime = data;
			return;
		case 3:
			this.tileentity.totalCookTime = data;
			return;
		}
	}
	
	/**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     * <br>
     * При изменении учитывать, что правила взаимодействия со слотами совпадают с таковыми в методе isItemValid объекта {@link XPKMTileEntity#inventory}
     */
	@Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index == 3)
            {
                if (!this.mergeItemStack(itemstack1, 3, 39, true))
                {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (index != 2 && index != 1 && index != 0)
            {
            	if (itemstack1.getItem() instanceof ItemGlassBottle) {   // Взаимодействие со слотом 0
            		if (!this.mergeItemStack(itemstack1, 0, 1, false)) { //
            			return ItemStack.EMPTY; 						 //
            		} 													 //
            	}														 // --------------------------
                if (TileEntityFurnace.isItemFuel(itemstack1))			 // Взаимодействие со слотом 1
                {														 //
                    if (!this.mergeItemStack(itemstack1, 1, 2, false))   //
                    {													 //
                        return ItemStack.EMPTY;							 //
                    }													 //
                }														 // --------------------------
                if (itemstack1.getItem() instanceof MemoryPowder) {		 //  Взаимодействие со слотом 2
                	if (!this.mergeItemStack(itemstack1, 2, 3, false)) { //
                		return ItemStack.EMPTY;							 //
                	}													 //
                }														 // --------------------------
                else if (index >= 4 && index < 30)
                {
                    if (!this.mergeItemStack(itemstack1, 30, 39, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (index >= 30 && index < 39 && !this.mergeItemStack(itemstack1, 4, 30, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 4, 39, false))
            {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount())
            {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }

}
