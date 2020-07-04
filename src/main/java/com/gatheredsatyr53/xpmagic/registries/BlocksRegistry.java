package com.gatheredsatyr53.xpmagic.registries;

import com.gatheredsatyr53.xpmagic.blocks.XPKeepingMachine;
import com.gatheredsatyr53.xpmagic.items.MemoryPowder;
import com.gatheredsatyr53.xpmagic.tiles.XPKMTileEntity;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@ObjectHolder("xpmagic")
@Mod.EventBusSubscriber// Автоматическая регистрация статичных обработчиков событий
public class BlocksRegistry
{
	
	@ObjectHolder("xp_keeping_machine")
    public static final Block XPKM = new XPKeepingMachine();

    @SideOnly(Side.CLIENT)
    public static void registerRender()
    {
        setRender(XPKM);
    }
    
    @SubscribeEvent
    public static void onRegistryBlock(RegistryEvent.Register<Block> e) {
        // Также вместо `register` можно использовать `registerAll`, чтобы прописать все предметы разом
        e.getRegistry().register(XPKM);
        GameRegistry.registerTileEntity(XPKMTileEntity.class, XPKM.getRegistryName());
    }

    @SideOnly(Side.CLIENT)
    private static void setRender(Block block)
    {
    	Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
    }
}
