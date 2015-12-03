package santa.karma.perks;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import santa.karma.ChaoticKarma;
import santa.karma.api.KarmaPerkNegative;
import santa.karma.api.KarmaPerkPositive;
import santa.karma.api.KarmaRegistry;
import santa.karma.gameevents.KarmaUpdateEvent;
import santa.karma.player.ExtendedPlayer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Gives the player their new perk when their karma updates, if possible.
 */
public class PerkApplier {
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
        for (KarmaPerkPositive perk : positive) {
            if (perk.getRequiredKarmaLevel() > nbt.karma) {
                perk.removePerk(event.player);
            }
        }
        for (KarmaPerkNegative perk : negative) {
            if (perk.getRequiredKarmaLevel() < nbt.karma) {
                perk.removePerk(event.player);
            }
        }
    }
}