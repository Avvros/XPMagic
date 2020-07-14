package com.gatheredsatyr53.xpmagic.services;

import com.gatheredsatyr53.xpmagic.containers.XPKMContainer;
import com.gatheredsatyr53.xpmagic.gui.XPKMGui;
import com.gatheredsatyr53.xpmagic.tiles.XPKMTileEntity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class MachineGuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		// TODO Auto-generated method stub
		//System.out.println("Gui ID: " + ID);
		if (ID == XPKMGui.GUI_ID) return new XPKMContainer(player.inventory, (XPKMTileEntity) world.getTileEntity(new BlockPos(x, y, z)));
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		// TODO Auto-generated method stub
		//System.out.println("Gui ID: " + ID);
		if (ID == XPKMGui.GUI_ID) return new XPKMGui(player.inventory, (XPKMTileEntity) world.getTileEntity(new BlockPos(x, y, z)));
		return null;
	}

}
