package santa.karma.events.negative;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import santa.karma.api.KarmaEventNegative;

public class LightningStrike extends KarmaEventNegative {
    public LightningStrike() {
        this.setRequiredKarmaLevel(200);
        this.setKarmaChance(1500);
    }

    @Override
    public void doEvent(EntityPlayer player, World world) {
        if (playerHasEnoughKarma(player)) {
            double x = player.posX;
            double y = player.posY;
            double z = player.posZ;
            world.spawnEntityInWorld(new EntityLightningBolt(world, x, y, z));
        }
    }
}
