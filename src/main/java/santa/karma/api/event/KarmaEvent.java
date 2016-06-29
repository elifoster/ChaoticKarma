package santa.karma.api.event;

import santa.karma.ChaoticKarma;

import java.util.Random;

/**
 * The KarmaEvent class contains default implementations for all event-related methods EXCEPT
 * playerHasEnoughKarma, because that relies on the type of event that it is, and doEvent,
 * because there is no default implementation needed for it.
 *
 * When you are creating new events, inherit KarmaEventNegative or KarmaEventPositive, NOT KarmaEvent.
 *
 * You must implement doEvent(EntityPlayer, World) when creating new events. All of the other methods have default
 * implementations here, and in the according KarmaEventNegative/KarmaEventPositive classes.
 */
public abstract class KarmaEvent implements IKarmaEvent {
    /**
     * For all your RNG needs.
     */
    public static final Random RANDOM = new Random();

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
}
