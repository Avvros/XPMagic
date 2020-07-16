package com.gatheredsatyr53.xpmagic.gui;

import com.gatheredsatyr53.xpmagic.XPMagic;
import com.gatheredsatyr53.xpmagic.containers.XPKMContainer;
import com.gatheredsatyr53.xpmagic.tiles.XPKMTileEntity;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ResourceLocation;

import static com.gatheredsatyr53.xpmagic.services.bounds.XPKMGuiBounds.*;

public class XPKMGui extends GuiContainer {
	
	private static final ResourceLocation KEEPER_GUI_TEXTURES = new ResourceLocation(XPMagic.MODID + ":textures/gui/xp_keeping_machine.png");
	public static final int GUI_ID = 0;
	private InventoryPlayer playerInventory;
	private XPKMTileEntity tileentity;

	public XPKMGui(InventoryPlayer playerInv, XPKMTileEntity keeperInv) {
		// TODO Auto-generated constructor stub
		super(new XPKMContainer(playerInv, keeperInv));
        this.playerInventory = playerInv;
        this.tileentity = keeperInv;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(KEEPER_GUI_TEXTURES);
        int windowX = (this.width - this.xSize) / 2;
        int windowY = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(windowX, windowY, 0, 0, this.xSize, this.ySize);
		
        if (tileentity.isBurning())
        {
            int k = this.getBurnLeftScaled(13);
            //12 - k — смещение относительно Y позиции индикатора горения для начала отрисовки анимации. 
            //Число меняется в зависимости от времени горения
            this.drawTexturedModalRect(windowX + BURNING_INDICATOR_X, windowY + BURNING_INDICATOR_Y + 12 - k, ANIMATION_SECTOR_X, 12 - k, BURNING_PICTURE_WIDTH, k + 1);
        }

        int l = this.getCookProgressScaled(29);
        this.drawTexturedModalRect(windowX + PROGRESS_INDICATOR_X, windowY + PROGRESS_INDICATOR_Y, ANIMATION_SECTOR_X, PROGRESS_ANIMATION_SECTOR_Y, l, PROGRESS_PICTURE_HEIGHT);
	}
	
	/**
	 * @param maxLength Длина индикатора приготовления
	 * @return Длина заполненной части этого индикатора
	 */
	private int getCookProgressScaled(int maxLength) {
		// TODO Auto-generated method stub
		int cookTime = this.tileentity.cookTime;
        int totalCookTime = this.tileentity.totalCookTime;
        return cookTime != 0 && totalCookTime != 0 ? cookTime * maxLength / totalCookTime : 0;
	}

	/**
     * Draws the screen and all the components in it.
     */
	@Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    /**
	 * @param maxLength Длина индикатора горения
	 * @return Длина заполненной части этого индикатора
	 */
    private int getBurnLeftScaled(int maxLength) {
		// TODO Auto-generated method stub   
		int i = this.tileentity.currentItemBurnTime;
		if (i == 0) i = 200;
		return this.tileentity.furnaceBurnTime * maxLength / i;
	}

	/**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String s = this.tileentity.getDisplayName().getUnformattedText();
        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
        this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

}
