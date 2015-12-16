package santa.karma.events.negative;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import santa.karma.api.KarmaEventNegative;

public class SetOnFire extends KarmaEventNegative {
    public SetOnFire() {
        this.setRequiredKarmaLevel(150);
        this.setKarmaChance(1100);
    }

    @Override
    public void doEvent(EntityPlayer player, World world) {
        if (playerHasEnoughKarma(player) && canSetOnFire(player, world)) {
            player.setFire(2);
        }
    }

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
                    if (world.getBlock(x + i, y + j, z + k).getMaterial() == Material.lava) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
