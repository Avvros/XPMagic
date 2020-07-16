package com.gatheredsatyr53.xpmagic.tiles;

import javax.annotation.Nullable;

import com.gatheredsatyr53.xpmagic.items.MemoryPowder;
import com.gatheredsatyr53.xpmagic.registries.ItemsRegistry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAir;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

public class XPKMTileEntity extends TileEntity implements ITickable {
	
	/** The number of ticks that the furnace will keep burning */
    public int furnaceBurnTime;
    /** The number of ticks that a fresh copy of the currently-burning item would keep the furnace burning for */
    public int currentItemBurnTime;
    public int cookTime;
    public int totalCookTime;
    
    public EntityPlayer user;
    
    public ItemStackHandler inventory = new ItemStackHandler(4) {

		@Override
		protected void onContentsChanged(int slot) {
			// TODO Auto-generated method stub
			XPKMTileEntity.this.markDirty();
		}
		
		@Override
		public boolean isItemValid(int slot, ItemStack stack) {
			// TODO Auto-generated method stub
			switch (slot) {
				case 3: 
					return false;
				case 2: 
					return stack.getItem() instanceof MemoryPowder;
				case 1:
					ItemStack itemstack = this.stacks.get(1);
		            return TileEntityFurnace.isItemFuel(stack) || SlotFurnaceFuel.isBucket(stack) && itemstack.getItem() != Items.BUCKET;
				case 0:
					return stack.getItem() instanceof ItemGlassBottle;
				default:
					return false;
			}
		}
    	
    };
    
    public void setUser(EntityPlayer player) {
    	this.user = player;
    }

	public boolean allIncluded() {
		for (int i = 0; i < inventory.getSlots() - 1; i++) if (this.inventory.getStackInSlot(i).isEmpty()) return false;
		return true;
	}
	
	/**
     * Don't rename this method to canInteractWith due to conflicts with Container
     */
    public boolean isUsableByPlayer(EntityPlayer player)
    {
        if (this.world.getTileEntity(this.pos) != this)
        {
            return false;
        }
        else
        {
            return player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
        }
    }
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
        boolean flag1 = false;

        if (this.isBurning())
        {
            --this.furnaceBurnTime;
        }

        if (!this.world.isRemote)
        {
            ItemStack itemstack = this.inventory.getStackInSlot(1);

            if (this.isBurning() || allIncluded())
            {
                if (!this.isBurning() && this.canPush())
                {
                    this.furnaceBurnTime = TileEntityFurnace.getItemBurnTime(itemstack);
                    this.currentItemBurnTime = this.furnaceBurnTime;

                    if (this.isBurning())
                    {
                        flag1 = true;

                        if (!itemstack.isEmpty())
                        {
                            Item item = itemstack.getItem();
                            this.totalCookTime = this.getCookTime(itemstack);
                            itemstack.shrink(1);

                            if (itemstack.isEmpty())
                            {
                                ItemStack item1 = item.getContainerItem(itemstack);
                                this.inventory.setStackInSlot(1, item1);
                            }
                        }
                    }
                }

                if (this.isBurning() && this.canPush())
                {
                    ++this.cookTime;

                    if (this.cookTime == this.totalCookTime)
                    {
                        this.cookTime = 0;
                        this.totalCookTime = this.getCookTime(this.inventory.getStackInSlot(0));
                        this.pushXP();
                        flag1 = true;
                    }
                }
                else
                {
                    this.cookTime = 0;
                }
            }
            else if (!this.isBurning() && this.cookTime > 0)
            {
                 this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.totalCookTime);
            }
        }

        if (flag1)
        {
            this.markDirty();
        }
	}

	public void pushXP() {
		// TODO Auto-generated method stub
		if (this.canPush()) {
			//ItemStack result = new ItemStack()
			ItemStack itemstack = this.inventory.getStackInSlot(0);
            //ItemStack itemstack1 = keeperRecipes.instance().getPushingResult(itemstack);
			ItemStack itemstack1 = new ItemStack(ItemsRegistry.XPCocktail);
			ItemStack itemstack2 = this.inventory.getStackInSlot(2);
            ItemStack itemstack3 = this.inventory.getStackInSlot(3);

            if (itemstack3.isEmpty())
            {
            	int expCount = 10;
            	NBTTagCompound tag = new NBTTagCompound();
            	tag.setInteger("StoredExp", expCount);
            	itemstack1.setTagCompound(tag);
            	
            	// forum.mcmodding.ru/threads/1-6-x-kak-umenshit-opyt-igroka-na-chislo-ne-na-uroven.4242/
            	// Снятие опыта с игрока
            	// Спасибо dimka
            	int experienceTotalEnd = user.experienceTotal - expCount;
            	if (experienceTotalEnd >= 0) {
            		do
            		{
            			user.addExperienceLevel(-1);
            		} while (user.experienceTotal >= experienceTotalEnd);
            		user.addExperience(experienceTotalEnd - user.experienceTotal);
            	}
            	// ---------------------------------------------------------------
            	
                this.inventory.setStackInSlot(3, itemstack1.copy());
            }
			/*
			 * else if (itemstack2.getItem() == itemstack1.getItem()) {
			 * itemstack2.grow(itemstack1.getCount()); }
			 */		
            itemstack.shrink(1);
            itemstack2.shrink(1);
        }
	}

	public int getCookTime(ItemStack itemStack) {
		// TODO Auto-generated method stub
		return 200;
	}
	
	private int getInventoryStackLimit() {
		// TODO Auto-generated method stub
		return 64;
	}

	private boolean canPush() {
		// TODO Auto-generated method stub
		if (user == null) return false;
		if (((ItemStack)this.inventory.getStackInSlot(0)).isEmpty())
        {
            return false;
        }
        else
        {
            //ItemStack itemstack = KeeperRecipes.instance().getPushingResult(this.inventory.getStackInSlot(0));
        	if (!inventory.isItemValid(0, this.inventory.getStackInSlot(0)) || !inventory.isItemValid(2, this.inventory.getStackInSlot(2))) {
        		return false;
        	}
        	
        	ItemStack itemstack = new ItemStack(ItemsRegistry.XPCocktail);
            if (itemstack.isEmpty())
            {
                return false;
            }
            else
            {
                ItemStack itemstack1 = this.inventory.getStackInSlot(3);

                if (itemstack1.isEmpty())
                {
                    return true;
                }
                else if (!itemstack1.isItemEqual(itemstack))
                {
                    return false;
                }
                else if (itemstack1.getCount() + itemstack.getCount() <= this.getInventoryStackLimit() && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize())  // Forge fix: make keeper respect stack sizes in keeper recipes
                {
                    return true;
                }
                else
                {
                    return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize(); // Forge fix: make keeper respect stack sizes in keeper recipes
                }
            }
        }
	}

	public boolean isBurning() {
		// TODO Auto-generated method stub
		return this.furnaceBurnTime > 0;
	}
	
	public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        inventory.deserializeNBT(compound);
        this.furnaceBurnTime = compound.getInteger("BurnTime");
        this.cookTime = compound.getInteger("CookTime");
        this.totalCookTime = compound.getInteger("CookTimeTotal");
        this.currentItemBurnTime = TileEntityFurnace.getItemBurnTime(this.inventory.getStackInSlot(1));
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("BurnTime", (short)this.furnaceBurnTime);
        compound.setInteger("CookTime", (short)this.cookTime);
        compound.setInteger("CookTimeTotal", (short)this.totalCookTime);
        compound.merge(inventory.serializeNBT());
        return compound;
    }
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
	  if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
	    return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory);
	  }
	  return super.getCapability(capability, facing);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		// TODO Auto-generated method stub
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return true;
		return super.hasCapability(capability, facing);
	}

	@Override
	public ITextComponent getDisplayName() {
		// TODO Auto-generated method stub
		return new TextComponentString(I18n.translateToLocal("tile.xp_keeping_machine.name"));
	}

	/*
	 * @Nullable
	 * 
	 * @Override public SPacketUpdateTileEntity getUpdatePacket() { // TODO
	 * Auto-generated method stub return new SPacketUpdateTileEntity(this.getPos(),
	 * 0, getUpdateTag()); }
	 * 
	 * @Override public NBTTagCompound getUpdateTag() { // TODO Auto-generated
	 * method stub return writeToNBT(new NBTTagCompound()); }
	 */

}
