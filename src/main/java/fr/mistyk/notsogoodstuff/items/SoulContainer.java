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
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

import java.util.List;

public class SoulContainer extends ItemFood implements IHasModel{

    public SoulContainer(){
        super(0, 0f, false);
        setUnlocalizedName("soul_container");
        setRegistryName("soul_container");
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
            entityplayer.getFoodStats().addStats((int)(healthData.getFloat(NBTNames.SOUL_CONTAINER_HEALTH)), healthData.getFloat(NBTNames.SOUL_CONTAINER_HEALTH) * 5);
            worldIn.playSound((EntityPlayer)entityLiving, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.9F);
            entityplayer.addStat(StatList.getObjectUseStats(this));
            if (entityplayer instanceof EntityPlayerMP) { CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP)entityplayer, stack); }
        }
        healthData.setFloat(NBTNames.SOUL_CONTAINER_HEALTH, 0f);
        return stack;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn){
        NBTTagCompound healthData = playerIn.getHeldItem(handIn).getOrCreateSubCompound(NBTNames.SOUL_CONTAINER);
        if (healthData.getFloat(NBTNames.SOUL_CONTAINER_HEALTH) > 0f){
            return super.onItemRightClick(worldIn, playerIn, handIn);
        }
        return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn){
        super.addInformation(stack, worldIn, tooltip, flagIn);
        NBTTagCompound healthData = stack.getOrCreateSubCompound(NBTNames.SOUL_CONTAINER);
        float hungerValue = healthData.getFloat(NBTNames.SOUL_CONTAINER_HEALTH);
        tooltip.add(I18n.translateToLocal("tooltip.hunger_value") + ": " + hungerValue);
    }
}