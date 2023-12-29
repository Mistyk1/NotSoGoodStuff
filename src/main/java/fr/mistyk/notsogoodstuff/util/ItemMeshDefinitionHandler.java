package fr.mistyk.notsogoodstuff.util;

import fr.mistyk.notsogoodstuff.items.ItemInit;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;

public class ItemMeshDefinitionHandler implements ItemMeshDefinition {
    @Override
    public ModelResourceLocation getModelLocation(ItemStack stack) {
        if (stack.getItem() == ItemInit.TACTICAL_SHOVEL) {
            boolean open = stack.getOrCreateSubCompound("open").getBoolean("isOpen");
            if (open){
                return new ModelResourceLocation(References.MODID + ":tactical_shovel0", "inventory");
            } else {
                return new ModelResourceLocation(References.MODID + ":tactical_shovel1", "inventory");
            }
        }
        return null;
    }
}