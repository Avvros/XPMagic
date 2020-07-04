package com.gatheredsatyr53.xpmagic.registries;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;

public class CraftingRegistry
{
    public static void register()
    {
        registerRecipes("memory_powder");
        registerRecipes("processing_chip");
    }

    private static void registerRecipes(String name)
    {
        CraftingHelper.register(new ResourceLocation("xpmagic", name), (IRecipeFactory) (context, json) -> CraftingHelper.getRecipe(json, context));
    }
}
