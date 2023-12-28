package fr.mistyk.notsogoodstuff.items;

import fr.mistyk.notsogoodstuff.NotSoGoodStuffMain;
import net.minecraft.item.Item;

public class SoulVial extends Item {
    public SoulVial(){
        super();
        setUnlocalizedName("soul_vial");
        setRegistryName("soul_vial");
        setHasSubtypes(true);
        ItemInit.ITEMS.add(this);
        setCreativeTab(NotSoGoodStuffMain.NotSoGoodStuffTab);
    }
}
