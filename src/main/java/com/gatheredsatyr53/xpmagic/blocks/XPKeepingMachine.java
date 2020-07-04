package com.gatheredsatyr53.xpmagic.blocks;

import com.gatheredsatyr53.xpmagic.XPMagic;
import com.gatheredsatyr53.xpmagic.tiles.XPKMTileEntity;
import com.gatheredsatyr53.xpmagic.tools.MachineInventoryHelper;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

public class XPKeepingMachine extends Machine {
	
	public XPKeepingMachine() {
		// TODO Auto-generated constructor stub
		super(Material.IRON);
        this.setRegistryName("xp_keeping_machine");
        this.setUnlocalizedName("xp_keeping_machine");
        setCreativeTab(XPMagic.XPMagicTab);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}
    
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		XPKMTileEntity tileentity = (XPKMTileEntity) worldIn.getTileEntity(pos);
		MachineInventoryHelper.dropInventoryItems(worldIn, pos, (IItemHandler) tileentity);
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		// TODO Auto-generated method stub
		return new XPKMTileEntity();
	}
	
}
