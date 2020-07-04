package com.gatheredsatyr53.xpmagic.tiles;

import com.gatheredsatyr53.xpmagic.items.MemoryPowder;
import com.gatheredsatyr53.xpmagic.registries.ItemsRegistry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class XPKMTileEntity extends TileEntity implements ITickable, IItemHandler {
	
	public NonNullList<ItemStack> keeperItemStacks = NonNullList.<ItemStack>withSize(4, ItemStack.EMPTY);
	/** The number of ticks that the furnace will keep burning */
    public int furnaceBurnTime;
    /** The number of ticks that a fresh copy of the currently-burning item would keep the furnace burning for */
    public int currentItemBurnTime;
    public int cookTime;
    public int totalCookTime;
    
    public EntityPlayerMP user;

	@Override
	public ItemStack getStackInSlot(int index) {
		// TODO Auto-generated method stub
		return this.keeperItemStacks.get(index);
	}

	public boolean allIncluded() {
		for (int i = 0; i < getSlots() - 1; i++) if (this.keeperItemStacks.get(i).isEmpty()) return false;
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
            ItemStack itemstack = this.keeperItemStacks.get(1);

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
                            itemstack.shrink(1);

                            if (itemstack.isEmpty())
                            {
                                ItemStack item1 = item.getContainerItem(itemstack);
                                this.keeperItemStacks.set(1, item1);
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
                        this.totalCookTime = this.getCookTime(this.keeperItemStacks.get(0));
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
			ItemStack itemstack = this.keeperItemStacks.get(0);
            //ItemStack itemstack1 = keeperRecipes.instance().getPushingResult(itemstack);
			ItemStack itemstack1 = new ItemStack(ItemsRegistry.XPCocktail);
            ItemStack itemstack2 = this.keeperItemStacks.get(2);

            if (itemstack2.isEmpty())
            {
            	int expCount = 10;
            	itemstack1.getTagCompound().setInteger("StoredExp", expCount);
            	
            	// forum.mcmodding.ru/threads/1-6-x-kak-umenshit-opyt-igroka-na-chislo-ne-na-uroven.4242/
            	// Снятие опыта с игрока
            	// Спасибо dimka
            	int experienceTotalEnd = user.experienceTotal - expCount;
            	do
            	{
            	    user.addExperienceLevel(-1);
            	} while (user.experienceTotal >= experienceTotalEnd);
            	user.addExperience(experienceTotalEnd - user.experienceTotal);
            	// ---------------------------------------------------------------
            	
                this.keeperItemStacks.set(2, itemstack1.copy());
            }
			/*
			 * else if (itemstack2.getItem() == itemstack1.getItem()) {
			 * itemstack2.grow(itemstack1.getCount()); }
			 */		
            itemstack.shrink(1);
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
		if (((ItemStack)this.keeperItemStacks.get(0)).isEmpty())
        {
            return false;
        }
        else
        {
            //ItemStack itemstack = KeeperRecipes.instance().getPushingResult(this.keeperItemStacks.get(0));
        	if (!isItemValid(0, this.keeperItemStacks.get(0)) || !isItemValid(2, this.keeperItemStacks.get(2))) {
        		return false;
        	}
        	
        	ItemStack itemstack = new ItemStack(ItemsRegistry.XPCocktail);
            if (itemstack.isEmpty())
            {
                return false;
            }
            else
            {
                ItemStack itemstack1 = this.keeperItemStacks.get(2);

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

	@Override
	public int getSlots() {
		// TODO Auto-generated method stub
		return 4;
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
				ItemStack itemstack = this.keeperItemStacks.get(1);
	            return TileEntityFurnace.isItemFuel(stack) || SlotFurnaceFuel.isBucket(stack) && itemstack.getItem() != Items.BUCKET;
			case 0:
				return stack.getItem() instanceof ItemGlassBottle;
			default:
				return false;
		}
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		// TODO Auto-generated method stub
		if (!simulate && isItemValid(slot, stack)) this.keeperItemStacks.set(slot, stack);
		return stack;
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		// TODO Auto-generated method stub
		ItemStack stack = this.keeperItemStacks.get(slot);
		amount = MathHelper.clamp(amount, 0, stack.getCount());
		ItemStack result = new ItemStack(stack.getItem(), amount);
		if (!simulate) {
			stack.shrink(amount);
		}
		return result;
	}

	@Override
	public int getSlotLimit(int slot) {
		// TODO Auto-generated method stub
		return 64;
	}
	
	public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.keeperItemStacks = NonNullList.<ItemStack>withSize(this.getSlots(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.keeperItemStacks);
        this.furnaceBurnTime = compound.getInteger("BurnTime");
        this.cookTime = compound.getInteger("CookTime");
        this.totalCookTime = compound.getInteger("CookTimeTotal");
        this.currentItemBurnTime = TileEntityFurnace.getItemBurnTime(this.keeperItemStacks.get(1));
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("BurnTime", (short)this.furnaceBurnTime);
        compound.setInteger("CookTime", (short)this.cookTime);
        compound.setInteger("CookTimeTotal", (short)this.totalCookTime);
        ItemStackHelper.saveAllItems(compound, this.keeperItemStacks);
        return compound;
    }
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
	  if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
	    return (T) this;
	  }
	  return super.getCapability(capability, facing);
	}

}
