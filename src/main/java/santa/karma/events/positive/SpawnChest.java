package santa.karma.events.positive;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import santa.karma.ChaoticKarma;
import santa.karma.api.event.KarmaEventPositive;
import santa.karma.util.MathHelper;

public class SpawnChest extends KarmaEventPositive {
    private static final ResourceLocation[] CHEST_TYPES = {
      LootTableList.CHESTS_ABANDONED_MINESHAFT,
      LootTableList.CHESTS_DESERT_PYRAMID,
      LootTableList.CHESTS_END_CITY_TREASURE,
      LootTableList.CHESTS_IGLOO_CHEST,
      LootTableList.CHESTS_JUNGLE_TEMPLE,
      LootTableList.CHESTS_JUNGLE_TEMPLE_DISPENSER,
      LootTableList.CHESTS_NETHER_BRIDGE,
      LootTableList.CHESTS_SIMPLE_DUNGEON,
      LootTableList.CHESTS_SPAWN_BONUS_CHEST,
      LootTableList.CHESTS_STRONGHOLD_CORRIDOR,
      LootTableList.CHESTS_STRONGHOLD_CROSSING,
      LootTableList.CHESTS_STRONGHOLD_LIBRARY,
      LootTableList.CHESTS_VILLAGE_BLACKSMITH
    };

    public SpawnChest() {
        setRequiredKarmaLevel(ChaoticKarma.MAX_KARMA);
        setKarmaChance(1900);
    }

    @Override
    public void doEvent(EntityPlayer player, World world) {
        int x = (int) player.posX + MathHelper.randomNegOrPos(10, RANDOM);
        int y = (int) player.posY;
        int z = (int) player.posZ + MathHelper.randomNegOrPos(10, RANDOM);
        BlockPos pos = new BlockPos(x, y, z);
        if (world.isAirBlock(pos) && world.getTileEntity(pos) == null) {
            world.setBlockState(pos, Blocks.CHEST.getDefaultState());
            ResourceLocation type = CHEST_TYPES[RANDOM.nextInt(CHEST_TYPES.length)];
            TileEntity tile = world.getTileEntity(pos);
            if (tile != null && tile instanceof TileEntityChest) {
                ((TileEntityChest) tile).setLootTable(type, RANDOM.nextLong());
            }
        }
    }
}
