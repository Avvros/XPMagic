package com.gatheredsatyr53.xpmagic.blocks;

import com.gatheredsatyr53.xpmagic.XPMagic;
import com.gatheredsatyr53.xpmagic.gui.XPKMGui;
import com.gatheredsatyr53.xpmagic.services.MachineInventoryHelper;
import com.gatheredsatyr53.xpmagic.tiles.XPKMTileEntity;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

public class XPKeepingMachine extends Machine {

	public XPKeepingMachine() {
		// TODO Auto-generated constructor stub
		super("xp_keeping_machine");
	}
    
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		XPKMTileEntity tileentity = (XPKMTileEntity) worldIn.getTileEntity(pos);
		MachineInventoryHelper.dropInventoryItems(worldIn, pos, tileentity.inventory);
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public boolean hasTileEntity() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		// TODO Auto-generated method stub
		//System.out.println(XPMagic.proxy.getClass());
		if (!worldIn.isRemote) {
			//System.out.println("Init: " + XPMagic.initCompleted);
			
			playerIn.openGui(XPMagic.instance, XPKMGui.GUI_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		// TODO Auto-generated method stub
		return new XPKMTileEntity();
	}
	
}
