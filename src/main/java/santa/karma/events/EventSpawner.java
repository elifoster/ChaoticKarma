package santa.karma.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import santa.karma.ChaoticKarma;
import santa.karma.api.event.KarmaEventNegative;
import santa.karma.api.KarmaRegistry;
import santa.karma.api.event.KarmaEventPositive;
import santa.karma.player.ExtendedPlayer;

import java.util.Random;

/**
 * Handles spawning the events, both positive and negative.
 */
public class EventSpawner {
    @SubscribeEvent
    public void tryPerformEvent(LivingEvent.LivingUpdateEvent event) {
        Random random = new Random();
        if (event.entityLiving instanceof EntityPlayer) {
            ExtendedPlayer nbt = (ExtendedPlayer) event.entityLiving.getExtendedProperties
              (ChaoticKarma.EXTENDEDPLAYER);
            if (nbt.karma < ChaoticKarma.DEFAULT_KARMA) {
                KarmaEventNegative karmaEvent = KarmaRegistry.eventNegatives.get
                  (random.nextInt(KarmaRegistry.eventNegatives.size()));
                if (random.nextInt(karmaEvent.getKarmaChance()) == 1) {
                    karmaEvent.doEvent((EntityPlayer) event.entityLiving, event.entityLiving.worldObj);
                }
            } else {
                KarmaEventPositive karmaEvent = KarmaRegistry.eventPositives.get(random.nextInt
                  (KarmaRegistry.eventPositives.size()));
                if (random.nextInt(karmaEvent.getKarmaChance()) == 1) {
                    karmaEvent.doEvent((EntityPlayer) event.entityLiving, event.entityLiving
                      .worldObj);
                }
            }
        }
    }
}
