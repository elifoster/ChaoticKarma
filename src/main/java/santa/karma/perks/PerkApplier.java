package santa.karma.perks;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import santa.karma.ChaoticKarma;
import santa.karma.api.KarmaRegistry;
import santa.karma.api.perk.KarmaPerkNegative;
import santa.karma.api.perk.KarmaPerkPositive;
import santa.karma.gameevents.KarmaUpdateEvent;
import santa.karma.player.ExtendedPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Gives the player their new perk when their karma updates, if possible.
 */
public class PerkApplier {
    @SubscribeEvent
    public void initializePerks(EntityJoinWorldEvent event) {
        if (event.entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entity;
            ExtendedPlayer nbt = (ExtendedPlayer) player.getExtendedProperties(ChaoticKarma
              .EXTENDEDPLAYER);
            MinecraftForge.EVENT_BUS.post(new KarmaUpdateEvent(ChaoticKarma.DEFAULT_KARMA,
              player, nbt.karma));
        }
    }

    @SubscribeEvent
    public void applyPerk(KarmaUpdateEvent event) {
        HashMap<String, KarmaPerkPositive> positive = KarmaRegistry.perkPositives;
        HashMap<String, KarmaPerkNegative> negative = KarmaRegistry.perkNegatives;
        if (event.newAmount >= ChaoticKarma.DEFAULT_KARMA) {
            for (KarmaPerkPositive perk : positive.values()) {
                if (perk.getRequiredKarmaLevel() <= event.newAmount) {
                    perk.applyPerk(event.player);
                }
            }
        } else {
            for (KarmaPerkNegative perk : negative.values()) {
                if (perk.getRequiredKarmaLevel() >= event.newAmount) {
                    perk.applyPerk(event.player);
                }
            }
        }
    }

    @SubscribeEvent
    public void removePerk(KarmaUpdateEvent event) {
        ExtendedPlayer nbt = (ExtendedPlayer) event.player.getExtendedProperties(ChaoticKarma
          .EXTENDEDPLAYER);
        ArrayList<KarmaPerkPositive> positive = nbt.positivePerks;
        ArrayList<KarmaPerkNegative> negative = nbt.negativePerks;
        Iterator<KarmaPerkPositive> positiveIterator = positive.iterator();
        Iterator<KarmaPerkNegative> negativeIterator = negative.iterator();
        while (positiveIterator.hasNext()) {
            KarmaPerkPositive perk = positiveIterator.next();
            if (perk.getRequiredKarmaLevel() > nbt.karma) {
                positiveIterator.remove();
            }
        }
        while (negativeIterator.hasNext()) {
            KarmaPerkNegative perk = negativeIterator.next();
            if (perk.getRequiredKarmaLevel() < nbt.karma) {
                negativeIterator.remove();
            }
        }
    }
}
