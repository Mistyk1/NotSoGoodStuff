package fr.mistyk.notsogoodstuff.proxy;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ClientProxy extends CommonProxy{
    @Override
    public void registerItemRenderer(Item item, int meta, String id){
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
    }

    @Override
    public void registerItemVariantRenderer(Item item, ItemMeshDefinition meshDefinition, ModelResourceLocation... names) {
        ModelBakery.registerItemVariants(item, names);
        ModelLoader.setCustomMeshDefinition(item, meshDefinition);
    }

    @Override
    public void preInit(){
    }

    @Override
    public void init(){
    }

    @Override
    public void postInit(){
    }
}
