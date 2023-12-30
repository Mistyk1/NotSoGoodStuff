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
import net.minecraft.util.text.TextComponentString;
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
            tooltip.add(I18n.translateToLocal("tooltip.mob_health") + ": " + containerData.getFloat(NBTNames.SOUL_VIAL_HEALTH));
        } else {
            tooltip.add(I18n.translateToLocal("tooltip.no_mob"));
        }
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        if (!target.world.isRemote || !target.isEntityAlive() || !target.isNonBoss() || target instanceof EntityPlayer || playerIn.isSneaking()){ return false; }
        NBTTagCompound vialData = stack.getOrCreateSubCompound(NBTNames.SOUL_VIAL);
        if (!vialData.getBoolean(NBTNames.SOUL_VIAL_CONTAINER) || !vialData.getString(NBTNames.SOUL_VIAL_MOB).isEmpty()){ return false; }
        playerIn.sendMessage(new TextComponentString(target.getName() + ": " + target.getHealth()));
        //TODO: finish this method (the following code doesn't want to work)
        vialData.setString(NBTNames.SOUL_VIAL_MOB, target.getName());
        vialData.setFloat(NBTNames.SOUL_VIAL_HEALTH, target.getHealth());
        return true;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        NBTTagCompound vialData = playerIn.getHeldItem(handIn).getOrCreateSubCompound(NBTNames.SOUL_VIAL);
        if (playerIn.isSneaking() && playerIn.getCooldownTracker().getCooldown(this, 0) == 0 && !worldIn.isRemote){
            playerIn.getCooldownTracker().setCooldown(this, 5);
            if (!vialData.getBoolean(NBTNames.SOUL_VIAL_CONTAINER)){ //Container insertion
                if (playerIn.getHeldItem(EnumHand.OFF_HAND).isItemEqual(new ItemStack(ItemInit.SOUL_CONTAINER))) {
                    vialData.setBoolean(NBTNames.SOUL_VIAL_CONTAINER, true);
                    playerIn.setHeldItem(EnumHand.OFF_HAND, ItemStack.EMPTY);
                }
            } else { //Container extraction
                ItemStack container = new ItemStack(ItemInit.SOUL_CONTAINER);
                NBTTagCompound containerData = container.getOrCreateSubCompound(NBTNames.SOUL_CONTAINER);
                containerData.setFloat(NBTNames.SOUL_CONTAINER_HEALTH, vialData.getFloat(NBTNames.SOUL_VIAL_HEALTH));
                if (playerIn.getHeldItem(EnumHand.OFF_HAND).isEmpty()) {
                    playerIn.setHeldItem(EnumHand.OFF_HAND, container);
                } else if (playerIn.inventory.getFirstEmptyStack() == -1){
                    playerIn.dropItem(container.getItem(), 1);
                } else {
                    playerIn.addItemStackToInventory(container);
                }
                vialData.setBoolean(NBTNames.SOUL_VIAL_CONTAINER, false);
                vialData.setString(NBTNames.SOUL_VIAL_MOB, "");
                vialData.setFloat(NBTNames.SOUL_VIAL_HEALTH, 0f);
            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
