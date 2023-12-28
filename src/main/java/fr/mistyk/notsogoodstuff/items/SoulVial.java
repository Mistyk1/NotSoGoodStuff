package fr.mistyk.notsogoodstuff.items;

import fr.mistyk.notsogoodstuff.NotSoGoodStuffMain;
import net.minecraft.item.Item;

public class SoulVial extends Item {
    //TODO: Create the item (can capture mobs and use their soul to make food. The more life the mob had, the better the food (mob health capped at 100 HP))
    public SoulVial(){
        super();
        setUnlocalizedName("soul_vial");
        setRegistryName("soul_vial");
        ItemInit.ITEMS.add(this);
        setCreativeTab(NotSoGoodStuffMain.NotSoGoodStuffTab);
    }
}
