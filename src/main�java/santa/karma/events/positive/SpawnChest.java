package santa.karma.events.positive;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;
import santa.karma.ChaoticKarma;
import santa.karma.api.KarmaEventPositive;

import java.util.Random;

public class SpawnChest extends KarmaEventPositive {
    private String[] chestTypes = {
      ChestGenHooks.BONUS_CHEST,
      ChestGenHooks.DUNGEON_CHEST,
      ChestGenHooks.MINESHAFT_CORRIDOR,
      ChestGenHooks.PYRAMID_DESERT_CHEST,
      ChestGenHooks.PYRAMID_JUNGLE_CHEST,
      ChestGenHooks.PYRAMID_JUNGLE_DISPENSER,
      ChestGenHooks.STRONGHOLD_CORRIDOR,
      ChestGenHooks.STRONGHOLD_CROSSING,
      ChestGenHooks.STRONGHOLD_LIBRARY,
      ChestGenHooks.VILLAGE_BLACKSMITH
    };

    public SpawnChest() {
        this.setRequiredKarmaLevel(ChaoticKarma.MAX_KARMA);
        this.setKarmaChance(1900);
    }

    @Override
    public void doEvent(EntityPlayer player, World world) {
        System.out.println("doing event");
        if (playerHasEnoughKarma(player)) {
            System.out.println("has karma");
            Random random = new Random();
            int x = (int) player.posX + random.nextInt(10 - 5);
            int y = (int) player.posY;
            int z = (int) player.posZ + random.nextInt(10 - 5);
            Block block = world.getBlock(x, y, z);
            if ((block == null || block.isAir(world, x, y, z)) &&
              world.getTileEntity(x, y, z) == null) {
                System.out.println("block location is free");
                world.setBlock(x, y, z, Blocks.chest);
                String type = chestTypes[random.nextInt(chestTypes.length)];
                WeightedRandomChestContent[] items = ChestGenHooks.getItems(type, random);
                TileEntity tile = world.getTileEntity(x, y, z);
                if (tile != null && tile instanceof TileEntityChest) {
                    System.out.println("block location is chest tile");
                    int slot = 0;
                    for (WeightedRandomChestContent content : items) {
                        System.out.println("looping");
                        ItemStack stack = content.theItemId;
                        int max = content.theMaximumChanceToGenerateItem;
                        int min = content.theMinimumChanceToGenerateItem;
                        if (random.nextInt(max) == min) {
                            System.out.println("setting slot contents " + stack.getDisplayName());
                            ((TileEntityChest) tile).setInventorySlotContents(slot, stack);
                            slot++;
                        }
                    }
                }
            }
        }
    }
}
