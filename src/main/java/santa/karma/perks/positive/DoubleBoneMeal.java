package santa.karma.perks.positive;

import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import santa.karma.api.perk.KarmaPerkPositive;

import java.util.Random;

public class DoubleBoneMeal extends KarmaPerkPositive {
    public DoubleBoneMeal() {
        this.setRequiredKarmaLevel(1400);
    }

    @SubscribeEvent
    public void useBoneMeal(BonemealEvent event) {
        Random random = new Random();
        if (hasPerk(event.entityPlayer) && random.nextInt(3) != 1) {
            event.setResult(Event.Result.ALLOW);
        }
    }
}
