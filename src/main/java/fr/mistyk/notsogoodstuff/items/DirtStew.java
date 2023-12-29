package fr.mistyk.notsogoodstuff.items;

import fr.mistyk.notsogoodstuff.NotSoGoodStuffMain;
import fr.mistyk.notsogoodstuff.util.IHasModel;
import net.minecraft.item.ItemSoup;

public class DirtStew extends ItemSoup implements IHasModel{
    public DirtStew(){
        super(1);
        setUnlocalizedName("dirt_stew");
        setRegistryName("dirt_stew");
        setMaxStackSize(16);
        ItemInit.ITEMS.add(this);
        setCreativeTab(NotSoGoodStuffMain.NotSoGoodStuffTab);
    }

    @Override
    public void registerModels(){
        NotSoGoodStuffMain.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
