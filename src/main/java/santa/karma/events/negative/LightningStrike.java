package santa.karma.events.negative;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import santa.karma.api.event.KarmaEventNegative;

public class LightningStrike extends KarmaEventNegative {
    public LightningStrike() {
        setRequiredKarmaLevel(200);
        setKarmaChance(1500);
    }

    @Override
    public void doEvent(EntityPlayer player, World world) {
        double x = player.posX;
        double y = player.posY;
        double z = player.posZ;
        world.addWeatherEffect(new EntityLightningBolt(world, x, y, z, false));
    }
}
