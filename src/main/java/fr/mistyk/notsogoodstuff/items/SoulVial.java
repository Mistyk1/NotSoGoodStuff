package fr.mistyk.notsogoodstuff.items;

import fr.mistyk.notsogoodstuff.NotSoGoodStuffMain;
import fr.mistyk.notsogoodstuff.util.IHasModel;
import fr.mistyk.notsogoodstuff.util.NBTNames;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

import java.util.List;

public class SoulVial extends Item implements IHasModel {

    //TODO: Create the item (can capture mobs and use their soul to make food. The more life the mob had, the better the food (mob health capped at 50 HP))
    public SoulVial(){
        super();
        setUnlocalizedName("soul_vial");
        setRegistryName("soul_vial");
        ItemInit.ITEMS.add(this);
        setCreativeTab(NotSoGoodStuffMain.NotSoGoodStuffTab);
    }

    @Override
    public void registerModels(){
        NotSoGoodStuffMain.proxy.registerItemRenderer(this, 0, "inventory");
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn){
        super.addInformation(stack, worldIn, tooltip, flagIn);
        NBTTagCompound containerData = stack.getOrCreateSubCompound(NBTNames.SOUL_VIAL);
        if (!containerData.getBoolean(NBTNames.SOUL_VIAL_CONTAINER)) {
            tooltip.add(I18n.translateToLocal("tooltip.no_container"));
        } else if (!containerData.getString(NBTNames.SOUL_VIAL_MOB).isEmpty()) {
            tooltip.add(I18n.translateToLocal("tooltip.mob") + ": " + containerData.getString(NBTNames.SOUL_VIAL_MOB));
            tooltip.add(I18n.translateToLocal("tooltip.mob_health") + ": " + (int)(containerData.getFloat(NBTNames.SOUL_VIAL_HEALTH) * 20));
        } else {
            tooltip.add(I18n.translateToLocal("tooltip.no_mob"));
        }
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        if (!target.isEntityAlive() || !target.isNonBoss() || !target.world.isRemote || target instanceof EntityPlayer){ return false; }
        NBTTagCompound containerData = stack.getOrCreateSubCompound(NBTNames.SOUL_VIAL);
        if (!containerData.getBoolean(NBTNames.SOUL_VIAL_CONTAINER) || !containerData.getString(NBTNames.SOUL_VIAL_MOB).isEmpty()){ return false; }
        containerData.setString(NBTNames.SOUL_VIAL_MOB, target.getName());
        containerData.setFloat(NBTNames.SOUL_VIAL_HEALTH, target.getHealth());
        //TODO: finish this method
        return true;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        //TODO: make this method
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
