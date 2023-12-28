package fr.mistyk.notsogoodstuff.blocks;

import fr.mistyk.notsogoodstuff.NotSoGoodStuffMain;
import fr.mistyk.notsogoodstuff.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class CactusBark extends Block implements IHasModel {
    public CactusBark(){
        super(Material.CACTUS);
        setUnlocalizedName("cactus_bark");
        setRegistryName("cactus_bark");
        BlockInit.BLOCKS.add(this);
        setCreativeTab(NotSoGoodStuffMain.NotSoGoodStuffTab);
    }

    @Override
    public void registerModels(){ NotSoGoodStuffMain.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory"); }
}
