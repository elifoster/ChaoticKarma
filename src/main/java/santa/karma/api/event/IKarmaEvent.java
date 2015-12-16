package santa.karma.api.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface IKarmaEvent {
    /**
     * Sets the required karma level for the event.
     * @param level The level to require for this event.
     * @throws IllegalArgumentException when the level is too high or low.
     */
    void setRequiredKarmaLevel(int level) throws IllegalArgumentException;

    /**
     * Gets the required karma level.
     * @return the level.
     */
    int getRequiredKarmaLevel();

    /**
     * Sets the chance in which this event may happen.
     * @param chance The chance. You will probably want to use a very large number for this, by
     *               the way. There will be a 1 in <chance> chance of the event happening.
     * @throws IllegalArgumentException if the chance is less than or equal to (<=) 0.
     */
    void setKarmaChance(int chance) throws IllegalArgumentException;

    /**
     * Gets the chance in which this event may happen.
     * @return The chance.
     */
    int getKarmaChance();

    /**
     * Gets whether the player has enough karma for the event.
     * @param player The player
     * @return Whether the player has enoughg karma.
     */
    boolean playerHasEnoughKarma(EntityPlayer player);

    /**
     * Performs the event. This must be called on a class that extends KarmaEvent or implements
     * IKarmaEvent and overrides the doEvent method. You should always check if the
     * playerHasEnoughKarma before you do anything. This method has no default implementation.
     * @param player The player
     * @param world The world.
     */
    void doEvent(EntityPlayer player, World world);
}
