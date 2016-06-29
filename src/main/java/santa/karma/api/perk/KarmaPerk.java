package santa.karma.api.perk;

import santa.karma.ChaoticKarma;

import java.util.Random;

/**
 * The KarmaPerk class contains default implementations for all perk-related methods EXCEPT
 * hasPerk and applyPerk, because they rely on the type of perk that it is.
 *
 * When creating your own perks, do not inherit this class. Instead, inherit KarmaPerkNegative or KarmaPerkPositive.
 * Those classes contain all of the hasPerk, removePerk, and applyPerk logic, so you won't need to implement your own.
 */
public abstract class KarmaPerk implements IKarmaPerk {
    /**
     * For all your RNG needs.
     */
    public static final Random RANDOM = new Random();

    /**
     * The level needed for the player to get this perk.
     */
    private int levelNeeded;

    @Override
    public void setRequiredKarmaLevel(int level) throws IllegalArgumentException {
        if (level < ChaoticKarma.MIN_KARMA || level > ChaoticKarma.MAX_KARMA) {
            throw new IllegalArgumentException("The set karma level, " + level + ", is too high " +
              "or low. Be sure that it is in between " + ChaoticKarma.MIN_KARMA + " and " +
              ChaoticKarma.MAX_KARMA);
        }

        levelNeeded = level;
    }

    @Override
    public int getRequiredKarmaLevel() {
        return levelNeeded;
    }
}
