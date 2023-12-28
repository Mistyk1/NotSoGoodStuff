package fr.mistyk.notsogoodstuff.items;

import fr.mistyk.notsogoodstuff.NotSoGoodStuffMain;
import fr.mistyk.notsogoodstuff.util.IHasModel;
import fr.mistyk.notsogoodstuff.util.ItemMeshDefinitionHandler;
import fr.mistyk.notsogoodstuff.util.References;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TacticalShovel extends ItemSpade implements IHasModel{
    public final NBTTagCompound nbt;

    public TacticalShovel() {
        super(ToolMaterial.IRON);
        setUnlocalizedName("tactical_shovel");
        setRegistryName("tactical_shovel");
        this.nbt = new NBTTagCompound();
        nbt.setBoolean("isOpen", true);
        ItemInit.ITEMS.add(this);
        setCreativeTab(NotSoGoodStuffMain.NotSoGoodStuffTab);
    }

    @Override
    public void registerModels(){
        NotSoGoodStuffMain.proxy.registerItemVariantRenderer(
                this,
                new ItemMeshDefinitionHandler(),
                new ModelResourceLocation(References.MODID + ":tactical_shovel0", "inventory"),
                new ModelResourceLocation(References.MODID + ":tactical_shovel1", "inventory")
        );
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand){
        if (player.isSneaking() && player.getCooldownTracker().getCooldown(this, 0) == 0 && !world.isRemote){
            player.getCooldownTracker().setCooldown(this, 5);
            boolean open = !nbt.getBoolean("isOpen");
            nbt.setBoolean("isOpen", open);
        }
        return super.onItemRightClick(world, player, hand);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        if (!nbt.getBoolean("isOpen")){
            return 0.1F;
        }
        return super.getDestroySpeed(stack, state);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (nbt.getBoolean("isOpen") && !player.isSneaking()){
            ItemStack itemstack = player.getHeldItem(hand);

            if (!player.canPlayerEdit(pos.offset(facing), facing, itemstack))
            {
                return EnumActionResult.FAIL;
            }
            else
            {
                IBlockState iblockstate = worldIn.getBlockState(pos);
                Block block = iblockstate.getBlock();

                if (facing != EnumFacing.DOWN && worldIn.getBlockState(pos.up()).getMaterial() == Material.AIR && block == Blocks.GRASS)
                {
                    IBlockState iblockstate1 = Blocks.GRASS_PATH.getDefaultState();
                    worldIn.playSound(player, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);

                    if (!worldIn.isRemote)
                    {
                        worldIn.setBlockState(pos, iblockstate1, 11);
                    }

                    return EnumActionResult.SUCCESS;
                }
                else
                {
                    return EnumActionResult.PASS;
                }
            }
        }
        return EnumActionResult.PASS;
    }

    @Override
    public boolean hitEntity(ItemStack item, EntityLivingBase entity, EntityLivingBase player){
        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
        return true;
    }
}