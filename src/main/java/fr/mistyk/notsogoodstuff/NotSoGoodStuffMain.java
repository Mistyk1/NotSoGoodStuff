package fr.mistyk.notsogoodstuff;

import fr.mistyk.notsogoodstuff.items.ItemInit;
import fr.mistyk.notsogoodstuff.proxy.CommonProxy;
import fr.mistyk.notsogoodstuff.util.References;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//TODO: Spamton Shades
//Helmet Slot / Head Bauble Slot (if Baubles is installed)
//+1 armor
//Gives a yellow and pink interface
//
//TODO: (overdetailed item)

@Mod(modid = References.MODID, name = References.NAME, version = References.VERSION)
public class NotSoGoodStuffMain {
    @Mod.Instance(References.MODID)
    public static NotSoGoodStuffMain instance;

    public static final CreativeTabs NotSoGoodStuffTab = new CreativeTabs("notsogoodstuff.tab") {
        @Override
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem() { return new ItemStack(ItemInit.TACTICAL_SHOVEL); }
    };

    @SidedProxy(clientSide = References.CLIENT_PROXY, serverSide = References.SERVER_PROXY)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e){
        proxy.preInit();
    }
    @Mod.EventHandler
    public void init(FMLInitializationEvent e){
        proxy.init();
    }
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e){
        proxy.postInit();
    }
}
