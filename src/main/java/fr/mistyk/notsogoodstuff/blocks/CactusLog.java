package fr.mistyk.notsogoodstuff.blocks;

import fr.mistyk.notsogoodstuff.NotSoGoodStuffMain;
import fr.mistyk.notsogoodstuff.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class CactusLog extends Block implements IHasModel {
    //TODO: make this block work
    public CactusLog(){
        super(Material.CACTUS);
        setUnlocalizedName("cactus_log");
        setRegistryName("cactus_log");
        BlockInit.BLOCKS.add(this);
        setCreativeTab(NotSoGoodStuffMain.NotSoGoodStuffTab);
    }

    @Override
    public void registerModels(){ NotSoGoodStuffMain.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory"); }
}
