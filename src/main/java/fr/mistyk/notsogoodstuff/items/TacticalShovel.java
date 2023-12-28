package fr.mistyk.notsogoodstuff.items;

import fr.mistyk.notsogoodstuff.NotSoGoodStuffMain;
import fr.mistyk.notsogoodstuff.util.IHasModel;
import fr.mistyk.notsogoodstuff.util.ItemMeshDefinitionHandler;
import fr.mistyk.notsogoodstuff.util.References;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TacticalShovel extends ItemSpade implements IHasModel{
    public final NBTTagCompound nbt;

    public TacticalShovel() {
        super(ToolMaterial.IRON);
        setUnlocalizedName("tactical_shovel");
        setRegistryName("tactical_shovel");
        setMaxDamage(5000);
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
    public boolean canHarvestBlock(IBlockState state) {
        if (nbt.getBoolean("isOpen")){
            return super.canHarvestBlock(state);
        }
        return false;
    }

    @Override
    public boolean canHarvestBlock(IBlockState state, ItemStack stack) {
        if (nbt.getBoolean("isOpen")){
            return super.canHarvestBlock(state, stack);
        }
        return false;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (nbt.getBoolean("isOpen")){
            return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
        }
        return EnumActionResult.PASS;
    }
}