package santa.karma.perks.positive;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import santa.karma.ChaoticKarma;
import santa.karma.api.perk.KarmaPerkPositive;
import santa.karma.player.ExtendedPlayer;

import java.util.Random;

public class DoubleBoneMeal extends KarmaPerkPositive {
    public DoubleBoneMeal() {
        this.setRequiredKarmaLevel(1400);
    }

    @SubscribeEvent
    public void useBoneMeal(BonemealEvent event) {
        Random random = new Random();
        ExtendedPlayer nbt = (ExtendedPlayer) event.entityPlayer.getExtendedProperties
          (ChaoticKarma.EXTENDEDPLAYER);
        if (nbt.positivePerks.contains(this)) {
            if (random.nextInt(3) != 1) {
                event.setResult(Event.Result.ALLOW);
            }
        }
    }
}
