package santa.karma.perks;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import santa.karma.ChaoticKarma;
import santa.karma.api.KarmaRegistry;
import santa.karma.api.perk.KarmaPerkNegative;
import santa.karma.api.perk.KarmaPerkPositive;
import santa.karma.gameevents.KarmaUpdateEvent;
import santa.karma.player.IPlayerData;
import santa.karma.util.EntityUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Gives the player their new perk when their karma updates, if possible.
 */
public class PerkApplier {
    @SubscribeEvent
    public void initializePerks(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof EntityPlayer)) {
            return;
        }

        EntityPlayer player = (EntityPlayer) entity;
        IPlayerData data = EntityUtil.getPlayerData(player);

        MinecraftForge.EVENT_BUS.post(new KarmaUpdateEvent(ChaoticKarma.DEFAULT_KARMA, player, data.getKarma()));
    }

    @SubscribeEvent
    public void applyPerk(KarmaUpdateEvent event) {
        HashMap<String, KarmaPerkPositive> positive = KarmaRegistry.perkPositives;
        HashMap<String, KarmaPerkNegative> negative = KarmaRegistry.perkNegatives;
        int newAmount = event.getNewAmount();
        EntityPlayer player = event.getPlayer();
        if (newAmount >= ChaoticKarma.DEFAULT_KARMA) {
            for (KarmaPerkPositive perk : positive.values()) {
                if (perk.getRequiredKarmaLevel() <= newAmount) {
                    perk.applyPerk(player);
                }
            }
        } else {
            for (KarmaPerkNegative perk : negative.values()) {
                if (perk.getRequiredKarmaLevel() >= newAmount) {
                    perk.applyPerk(player);
                }
            }
        }
    }

    @SubscribeEvent
    public void removePerk(KarmaUpdateEvent event) {
        IPlayerData data = EntityUtil.getPlayerData(event.getPlayer());
        ArrayList<KarmaPerkPositive> positive = data.getPositivePerks();
        ArrayList<KarmaPerkNegative> negative = data.getNegativePerks();
        Iterator<KarmaPerkPositive> positiveIterator = positive.iterator();
        Iterator<KarmaPerkNegative> negativeIterator = negative.iterator();
        int karma = data.getKarma();
        while (positiveIterator.hasNext()) {
            KarmaPerkPositive perk = positiveIterator.next();
            if (perk.getRequiredKarmaLevel() > karma) {
                positiveIterator.remove();
            }
        }
        while (negativeIterator.hasNext()) {
            KarmaPerkNegative perk = negativeIterator.next();
            if (perk.getRequiredKarmaLevel() < karma) {
                negativeIterator.remove();
            }
        }
    }
}
