package santa.karma.events;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import santa.karma.ChaoticKarma;
import santa.karma.api.event.KarmaEventNegative;
import santa.karma.api.KarmaRegistry;
import santa.karma.api.event.KarmaEventPositive;
import santa.karma.player.IPlayerData;
import santa.karma.util.EntityUtil;

import java.util.Random;

/**
 * Handles spawning the events, both positive and negative.
 */
public class EventSpawner {
    public static final Random RANDOM = new Random();
    @SubscribeEvent
    public void tryPerformEvent(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entityLiving = event.getEntityLiving();
        if (entityLiving instanceof EntityPlayer) {
            IPlayerData data = EntityUtil.getPlayerData((EntityPlayer) entityLiving);
            if (data.getKarma() < ChaoticKarma.DEFAULT_KARMA) {
                KarmaEventNegative karmaEvent = KarmaRegistry.eventNegatives.get
                  (RANDOM.nextInt(KarmaRegistry.eventNegatives.size()));
                if (RANDOM.nextInt(karmaEvent.getKarmaChance()) == 1 &&
                  karmaEvent.playerHasEnoughKarma((EntityPlayer) entityLiving)) {
                    karmaEvent.doEvent((EntityPlayer) entityLiving, entityLiving.worldObj);
                }
            } else {
                KarmaEventPositive karmaEvent = KarmaRegistry.eventPositives.get(RANDOM.nextInt
                  (KarmaRegistry.eventPositives.size()));
                if (RANDOM.nextInt(karmaEvent.getKarmaChance()) == 1 &&
                  karmaEvent.playerHasEnoughKarma((EntityPlayer) entityLiving)) {
                    karmaEvent.doEvent((EntityPlayer) entityLiving, entityLiving.worldObj);
                }
            }
        }
    }
}
