package santa.karma.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import santa.karma.ChaoticKarma;
import santa.karma.player.ExtendedPlayer;

public class KarmaEventPositive {
    /**
     * The amount of karma needed to do the event.
     */
    private int levelNeeded;

    /**
     * The chance of the event happening.
     */
    private int karmaChance;

    /**
     * Sets the required karma level for the event.
     * @param level The level to require for this event.
     * @throws IllegalArgumentException when the level is too high or low.
     */
    public void setRequiredKarmaLevel(int level) throws IllegalArgumentException {
        if (level < ChaoticKarma.MIN_KARMA || level > ChaoticKarma.MAX_KARMA) {
            throw new IllegalArgumentException("The set karma level, " + level + ", is too high " +
              "or low. Be sure that it is in between " + ChaoticKarma.MIN_KARMA + " and " +
              ChaoticKarma.MAX_KARMA);
        }

        this.levelNeeded = level;
    }

    /**
     * Gets the required karma level.
     * @return the level.
     */
    protected int getRequiredKarmaLevel() {
        return this.levelNeeded;
    }

    /**
     * Sets the chance in which this event may happen.
     * @param chance The chance. You will probably want to use a very large number for this, by
     *               the way. There will be a 1 in <chance> chance of the event happening.
     * @throws IllegalArgumentException if the chance is less than or equal to (<=) 0.
     */
    protected void setKarmaChance(int chance) {
        if (chance > 0) {
            this.karmaChance = chance;
        } else {
            throw new IllegalArgumentException("The karma chance, " + chance + ", must be greater" +
              " than 0.");
        }
    }

    /**
     * Gets the chance in which this event may happen.
     * @return The chance.
     */
    public int getKarmaChance() {
        return this.karmaChance;
    }

    /**
     * Gets whether the player has enough karma for the event.
     * @param player The player
     * @return Whether the player has enoughg karma.
     */
    protected boolean playerHasEnoughKarma(EntityPlayer player) {
        ExtendedPlayer nbt = (ExtendedPlayer) player.getExtendedProperties(ChaoticKarma
          .EXTENDEDPLAYER);
        return (nbt.karma >= this.levelNeeded);
    }

    /**
     * Performs the event. This must be called on a class that extends KarmaEventNegative, and
     * overrides the doEvent method. You should always check if the playerHasEnoughKarma before you
     * do anything.
     * @param player The player
     * @param world The world.
     */
    public void doEvent(EntityPlayer player, World world) {}
}
