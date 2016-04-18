package santa.karma.events.positive;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;
import santa.karma.ChaoticKarma;
import santa.karma.api.event.KarmaEventPositive;
import santa.karma.util.MathHelper;

import java.util.List;
import java.util.Random;

public class SpawnChest extends KarmaEventPositive {
    private String[] chestTypes = {
      ChestGenHooks.BONUS_CHEST,
      ChestGenHooks.DUNGEON_CHEST,
      ChestGenHooks.MINESHAFT_CORRIDOR,
      ChestGenHooks.NETHER_FORTRESS,
      ChestGenHooks.PYRAMID_DESERT_CHEST,
      ChestGenHooks.PYRAMID_JUNGLE_CHEST,
      ChestGenHooks.PYRAMID_JUNGLE_DISPENSER,
      ChestGenHooks.STRONGHOLD_CORRIDOR,
      ChestGenHooks.STRONGHOLD_CROSSING,
      ChestGenHooks.STRONGHOLD_LIBRARY,
      ChestGenHooks.VILLAGE_BLACKSMITH,
    };

    public SpawnChest() {
        this.setRequiredKarmaLevel(ChaoticKarma.MAX_KARMA);
        this.setKarmaChance(1900);
    }

    @Override
    public void doEvent(EntityPlayer player, World world) {
        if (playerHasEnoughKarma(player)) {
            Random random = new Random();
            int x = (int) player.posX + MathHelper.randomNegOrPos(10, random);
            int y = (int) player.posY;
            int z = (int) player.posZ + MathHelper.randomNegOrPos(10, random);
            BlockPos pos = new BlockPos(x, y, z);
            Block block = world.getBlockState(pos).getBlock();
            if ((block == null || world.isAirBlock(pos)) &&
              world.getTileEntity(pos) == null) {
                world.setBlockState(pos, Blocks.chest.getDefaultState());
                String type = chestTypes[random.nextInt(chestTypes.length)];
                List<WeightedRandomChestContent> items = ChestGenHooks.getItems(type, random);
                TileEntity tile = world.getTileEntity(pos);
                if (tile != null && tile instanceof TileEntityChest) {
                    WeightedRandomChestContent.generateChestContents(random, items,
                      (TileEntityChest) tile, random.nextInt(3));
                }
            }
        }
    }
}
