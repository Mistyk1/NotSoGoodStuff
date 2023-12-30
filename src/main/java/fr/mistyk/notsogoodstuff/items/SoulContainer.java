package fr.mistyk.notsogoodstuff.items;

import fr.mistyk.notsogoodstuff.NotSoGoodStuffMain;
import fr.mistyk.notsogoodstuff.util.IHasModel;
import fr.mistyk.notsogoodstuff.util.NBTNames;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

import java.util.List;

public class SoulContainer extends ItemFood implements IHasModel{

    public SoulContainer(){
        super(0, 0f, false);
        setUnlocalizedName("soul_container");
        setRegistryName("soul_container");
        setMaxStackSize(1);
        ItemInit.ITEMS.add(this);
        setCreativeTab(NotSoGoodStuffMain.NotSoGoodStuffTab);
    }

    @Override
    public void registerModels(){
        NotSoGoodStuffMain.proxy.registerItemRenderer(this, 0, "inventory");
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving){
        NBTTagCompound healthData = stack.getOrCreateSubCompound(NBTNames.SOUL_CONTAINER);
        if (entityLiving instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer)entityLiving;
            entityplayer.getFoodStats().addStats(healthData.getInteger(NBTNames.SOUL_CONTAINER_HEALTH), (float)(healthData.getInteger(NBTNames.SOUL_CONTAINER_HEALTH) / 4));
            worldIn.playSound((EntityPlayer)entityLiving, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5f, worldIn.rand.nextFloat() * 0.1f + 0.9f);
            entityplayer.addStat(StatList.getObjectUseStats(this));
            if (entityplayer instanceof EntityPlayerMP) { CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP)entityplayer, stack); }
        }
        healthData.setInteger(NBTNames.SOUL_CONTAINER_HEALTH, 0);
        return stack;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn){
        NBTTagCompound healthData = playerIn.getHeldItem(handIn).getOrCreateSubCompound(NBTNames.SOUL_CONTAINER);
        if (healthData.getInteger(NBTNames.SOUL_CONTAINER_HEALTH) > 0){
            return super.onItemRightClick(worldIn, playerIn, handIn);
        }
        return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn){
        super.addInformation(stack, worldIn, tooltip, flagIn);
        NBTTagCompound healthData = stack.getOrCreateSubCompound(NBTNames.SOUL_CONTAINER);
        int hungerValue = healthData.getInteger(NBTNames.SOUL_CONTAINER_HEALTH);
        tooltip.add(I18n.translateToLocal("item.soul_container.hunger_value") + ": " + hungerValue);
    }
}