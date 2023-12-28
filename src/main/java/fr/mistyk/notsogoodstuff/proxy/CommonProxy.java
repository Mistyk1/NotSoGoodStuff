package fr.mistyk.notsogoodstuff.proxy;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;

public class CommonProxy{
    public void registerItemRenderer(Item item, int meta, String id){}

    public void registerItemVariantRenderer(Item item, ItemMeshDefinition meshDefinition, ModelResourceLocation... names) {}

    public void preInit(){}

    public void init(){}

    public void postInit(){}
}
