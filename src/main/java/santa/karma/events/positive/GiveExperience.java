package santa.karma.events.positive;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import santa.karma.api.event.KarmaEventPositive;

public class GiveExperience extends KarmaEventPositive {
    public GiveExperience() {
        setRequiredKarmaLevel(1900);
        setKarmaChance(1750);
    }

    @Override
    public void doEvent(EntityPlayer player, World world) {
        player.addExperience(RANDOM.nextInt(500));
    }
}
