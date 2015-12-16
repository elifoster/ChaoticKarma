package santa.karma.events.positive;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import santa.karma.api.KarmaEventPositive;

import java.util.Random;

public class GiveExperience extends KarmaEventPositive {
    public GiveExperience() {
        this.setRequiredKarmaLevel(1900);
        this.setKarmaChance(1750);
    }

    @Override
    public void doEvent(EntityPlayer player, World world) {
        if (playerHasEnoughKarma(player)) {
            Random random = new Random();
            player.addExperience(random.nextInt(500));
        }
    }
}
