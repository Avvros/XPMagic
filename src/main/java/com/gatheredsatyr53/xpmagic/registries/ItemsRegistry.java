package com.gatheredsatyr53.xpmagic.registries;

import com.gatheredsatyr53.xpmagic.items.*;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/*
 * ��������� ModId ��� ������ ObjectHolder � ������
 * ���� �� ��������� ��������� ��� �������, �� ������ ��� ������� ����������� ModId �������.
 * ��������� ��. https://mcforge.readthedocs.io/en/latest/concepts/registries/#injecting-registry-values-into-fields
 */
@ObjectHolder("xpmagic")
@Mod.EventBusSubscriber// �������������� ����������� ��������� ������������ �������
public class ItemsRegistry {
    /*
     * ��������� �������� �� �����. �� ����� ������ ������������ ������ ��������� ��� ��������� ��������� ���������
     * ���� �� �� ��������� ��������� ��� �������, �� � ����� ������ ��� ����� ��������� ������ `key` -> `tut:key`
     */
    @ObjectHolder("memory_powder")
    public static final Item MemoryPowder = new MemoryPowder();
    
    public static final Item XPKM_Item = new ItemBlock(BlocksRegistry.XPKM).setRegistryName(BlocksRegistry.XPKM.getRegistryName());

    @ObjectHolder("processing_chip")
    public static final Item ProcessingChip = new ProcessingChip();
    
    @ObjectHolder("xp_cocktail")
    public static final Item XPCocktail = new XPCocktail();
    
    /*
     * ������� � 1.12 ����������� ���������/������/������� � �.�. ������� ��������� � ����������� �������.
     * ������� Register<IForgeRegistryEntry> ������������ �����������: Block, Item, Potion, Biome, SoundEvent, 
     * PotionType, Enchantment, IRecipe,  VillagerProfession, EntityEntry.
     * �������� ��������! ����� �������� ���������, ��� ��� �� ���������� EventBusSubscriber
     */
    @SubscribeEvent
    public static void onRegistryItem(RegistryEvent.Register<Item> e) {
        // ����� ������ `register` ����� ������������ `registerAll`, ����� ��������� ��� �������� �����
        e.getRegistry().registerAll(MemoryPowder, 
        							ProcessingChip, 
        							XPKM_Item,
        							XPCocktail);
    }

    /*
     * ������� � 1.11 ����������� ������� ��� ���������/������ ������� ��������� � ����������� �������.
     * �������� ��������! ����� �������� ���������, ��� ��� �� ���������� EventBusSubscriber
     */
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onRegistryModel(ModelRegistryEvent e) {
        registryModel(MemoryPowder);
        registryModel(ProcessingChip);
        registryModel(XPKM_Item);
        registryModel(XPCocktail);
    }
    
    @SideOnly(Side.CLIENT)
    private static void registryModel(Item item) {
    	final ResourceLocation regName = item.getRegistryName();// �� ��������, ��� getRegistryName ����� ������� Null!
        final ModelResourceLocation mrl = new ModelResourceLocation(regName, "inventory");
        ModelBakery.registerItemVariants(item, mrl);// ����������� ��������� ��������. ��� ����� ���� �� ����� ������������ ������� ���������/������(��. ������ �������)
        ModelLoader.setCustomModelResourceLocation(item, 0, mrl);// ������������� ������� ������ ��� ������ ��������. ��� ����������� �������� ������, ���� ������ �� ����� ����������� ��� ��������/�����(��. ������ �������)
    }
}
