package santa.karma.api.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import santa.karma.ChaoticKarma;

/**
 * The KarmaEvent class contains default implementations for all event-related methods EXCEPT
 * playerHasEnoughKarma, because that relies on the type of event that it is, and doEvent,
 * because there is no default implementation needed for it.
 */
public class KarmaEvent implements IKarmaEvent {
    /**
     * The amount of karma needed to do the event.
     */
    private int levelNeeded;

    /**
     * The chance of the event happening.
     */
    private int chance;

    @Override
    public void setRequiredKarmaLevel(int level) throws IllegalArgumentException {
        if (level < ChaoticKarma.MIN_KARMA || level > ChaoticKarma.MAX_KARMA) {
            throw new IllegalArgumentException("The set karma level, " + level + ", is too high " +
              "or low. Be sure that it is in between " + ChaoticKarma.MIN_KARMA + " and " +
              ChaoticKarma.MAX_KARMA);
        }

        this.levelNeeded = level;
    }

    @Override
    public int getRequiredKarmaLevel() {
        return this.levelNeeded;
    }

    @Override
    public void setKarmaChance(int chance) throws IllegalArgumentException {
        if (chance > 0) {
            this.chance = chance;
        } else {
            throw new IllegalArgumentException("The karma chance must be greater than 0");
        }
    }

    @Override
    public int getKarmaChance() {
        return this.chance;
    }

    /**
     * This method should never be used. Instead, use the implementation of it at
     * KarmaEventNegative or KarmaEventPositive.
     * @param player The player
     * @return
     */
    @Override
    public boolean playerHasEnoughKarma(EntityPlayer player) {
        return false;
        // Default implementation, do not use this.
    }

    /**
     * This method should never be used. Instead, use the implementation of it at
     * KarmaEventPositive or KarmaEventNegative.
     * @param player The player
     * @param world The world.
     */
    @Override
    public void doEvent(EntityPlayer player, World world) {}
}
