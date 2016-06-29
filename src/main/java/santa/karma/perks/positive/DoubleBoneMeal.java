package santa.karma.perks.positive;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import santa.karma.api.perk.KarmaPerkPositive;

public class DoubleBoneMeal extends KarmaPerkPositive {
    public DoubleBoneMeal() {
        setRequiredKarmaLevel(1400);
    }

    @SubscribeEvent
    public void useBoneMeal(BonemealEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        if (hasPerk(player) && RANDOM.nextInt(3) != 1) {
            event.setResult(Event.Result.ALLOW);
        }
    }
}
