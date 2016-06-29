package santa.karma.events.negative;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import santa.karma.api.event.KarmaEventNegative;

public class SetOnFire extends KarmaEventNegative {
    public SetOnFire() {
        setRequiredKarmaLevel(150);
        setKarmaChance(1100);
    }

    @Override
    public void doEvent(EntityPlayer player, World world) {
        if (canSetOnFire(player, world)) {
            player.setFire(2);
        }
    }

    /**
     * Checks whether the player is in a location in which they would logically be afraid of
     * being set on fire, such as being near lava or in the nether.
     * @param player The player to check.
     * @param world The world.
     * @return Whether the player might be afraid of being set on fire.
     */
    private boolean canSetOnFire(EntityPlayer player, World world) {
        if (player.dimension == -1) {
            return true;
        }

        int x = (int) player.posX;
        int y = (int) player.posY;
        int z = (int) player.posZ;

        for (int i = -5; i <= 5; i++) {
            for (int j = -5; j <= 5; j++) {
                for (int k = -5; k <= 5; k++) {
                    BlockPos pos = new BlockPos(x + i, y + j, z + k);
                    if (world.getBlockState(pos).getMaterial() == Material.LAVA) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
