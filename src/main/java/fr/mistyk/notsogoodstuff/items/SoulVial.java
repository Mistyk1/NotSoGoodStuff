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
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

import java.util.List;

public class SoulVial extends Item implements IHasModel {
    public SoulVial(){
        super();
        setUnlocalizedName("soul_vial");
        setRegistryName("soul_vial");
        setMaxStackSize(1);
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
            tooltip.add(I18n.translateToLocal("item.soul_vial.no_container"));
        } else if (!containerData.getString(NBTNames.SOUL_VIAL_MOB).isEmpty()) {
            tooltip.add(I18n.translateToLocal("item.soul_vial.mob") + ": " + containerData.getString(NBTNames.SOUL_VIAL_MOB));
            tooltip.add(I18n.translateToLocal("item.soul_vial.mob_health") + ": " + containerData.getInteger(NBTNames.SOUL_VIAL_HEALTH));
        } else {
            tooltip.add(I18n.translateToLocal("item.soul_vial.no_mob"));
        }
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        if (!target.world.isRemote || !target.isEntityAlive() || playerIn.isSneaking()){ return false; }
        NBTTagCompound vialData = playerIn.getHeldItem(hand).getOrCreateSubCompound(NBTNames.SOUL_VIAL);
        if (!vialData.getBoolean(NBTNames.SOUL_VIAL_CONTAINER)){
            playerIn.sendMessage(new TextComponentTranslation(I18n.translateToLocal("item.soul_vial.no_container")));
            return false;
        }
        if (!vialData.getString(NBTNames.SOUL_VIAL_MOB).isEmpty()){
            playerIn.sendMessage(new TextComponentTranslation(I18n.translateToLocal("item.soul_vial.not_empty")));
            return false;
        }
        if (target instanceof EntityPlayer){
            playerIn.sendMessage(new TextComponentTranslation(I18n.translateToLocal("item.soul_vial.player")));
            return false;
        }
        String targetName = target.getName();
        int targetHealth = (int)target.getHealth();
        if (!target.isNonBoss() || targetHealth > 50){
            playerIn.sendMessage(new TextComponentTranslation(I18n.translateToLocal("item.soul_vial.boss")));
            return false;
        }
        vialData.setString(NBTNames.SOUL_VIAL_MOB, targetName);
        vialData.setInteger(NBTNames.SOUL_VIAL_HEALTH, targetHealth);
        target.setDead();
        return true;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        NBTTagCompound vialData = playerIn.getHeldItem(handIn).getOrCreateSubCompound(NBTNames.SOUL_VIAL);
        if (playerIn.isSneaking() && playerIn.getCooldownTracker().getCooldown(this, 0) == 0 && !worldIn.isRemote){
            playerIn.getCooldownTracker().setCooldown(this, 5);
            if (!vialData.getBoolean(NBTNames.SOUL_VIAL_CONTAINER)){ //Container insertion
                if (playerIn.getHeldItem(EnumHand.OFF_HAND).isItemEqual(new ItemStack(ItemInit.SOUL_CONTAINER)) &&
                        playerIn.getHeldItem(EnumHand.OFF_HAND).getOrCreateSubCompound(NBTNames.SOUL_CONTAINER).getInteger(NBTNames.SOUL_CONTAINER_HEALTH) == 0) {
                    vialData.setBoolean(NBTNames.SOUL_VIAL_CONTAINER, true);
                    playerIn.setHeldItem(EnumHand.OFF_HAND, ItemStack.EMPTY);
                }
            } else { //Container extraction
                ItemStack container = new ItemStack(ItemInit.SOUL_CONTAINER);
                NBTTagCompound containerData = container.getOrCreateSubCompound(NBTNames.SOUL_CONTAINER);
                containerData.setInteger(NBTNames.SOUL_CONTAINER_HEALTH, ((vialData.getInteger(NBTNames.SOUL_VIAL_HEALTH) / 50) * 20));
                if (playerIn.getHeldItem(EnumHand.OFF_HAND).isEmpty()) {
                    playerIn.setHeldItem(EnumHand.OFF_HAND, container);
                } else if (playerIn.inventory.getFirstEmptyStack() == -1){
                    playerIn.dropItem(container.getItem(), 1);
                } else {
                    playerIn.addItemStackToInventory(container);
                }
                vialData.setBoolean(NBTNames.SOUL_VIAL_CONTAINER, false);
                vialData.setString(NBTNames.SOUL_VIAL_MOB, "");
                vialData.setInteger(NBTNames.SOUL_VIAL_HEALTH, 0);
            }
            return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
        }
        return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
    }
}
