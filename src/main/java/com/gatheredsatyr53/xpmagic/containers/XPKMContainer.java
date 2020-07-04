package com.gatheredsatyr53.xpmagic.containers;

import com.gatheredsatyr53.xpmagic.containers.slots.XPKMFuelSlot;
import com.gatheredsatyr53.xpmagic.containers.slots.XPKMOutputSlot;
import com.gatheredsatyr53.xpmagic.tiles.XPKMTileEntity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class XPKMContainer extends Container {
	
	private final XPKMTileEntity tileentity;
	private int cookTime, totalCookTime, burnTime, currentBurnTime;

	public XPKMContainer(InventoryPlayer player, XPKMTileEntity tileentity) {
		this.tileentity = tileentity;
		
		this.addSlotToContainer(new Slot((IInventory) tileentity, 0, 44, 17)); //Bottle Slot
		this.addSlotToContainer(new XPKMFuelSlot((IInventory) tileentity, 1, 44, 38)); //Fuel Slot
		this.addSlotToContainer(new Slot((IInventory) tileentity, 2, 44, 60)); //MemoryPowder Slot
		this.addSlotToContainer(new XPKMOutputSlot((EntityPlayerMP) player.player, (IInventory) tileentity, 3, 112, 35)); //Output Slot
	

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
	public void addListener(IContainerListener listener) {
		// TODO Auto-generated method stub
		super.addListener(listener);
		listener.sendAllWindowProperties(this, (IInventory) this.tileentity);
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

        this.cookTime = this.tileentity.cookTime;
        this.burnTime = this.tileentity.furnaceBurnTime;
        this.currentBurnTime = this.tileentity.currentItemBurnTime;
        this.totalCookTime = this.tileentity.totalCookTime;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		// TODO Auto-generated method stub
		super.updateProgressBar(id, data);
	}
	
	

}
