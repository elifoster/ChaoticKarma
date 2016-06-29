package santa.karma.api.perk;

import net.minecraft.entity.player.EntityPlayer;

public interface IKarmaPerk {
    /**
     * Sets the required karma level for the perk.
     * @param level The required level.
     * @throws IllegalArgumentException if the level is invalid.
     */
    void setRequiredKarmaLevel(int level) throws IllegalArgumentException;

    /**
     * Gets the required karma level for the perk.
     * @return The required karma level
     */
    int getRequiredKarmaLevel();

    /**
     * Gets whether the player has the perk.
     * @param player The player to check.
     * @return Whether they have the perk
     */
    boolean hasPerk(EntityPlayer player);

    /**
     * Applies or gives the perk to the player.
     * @param player The player.
     */
    void applyPerk(EntityPlayer player);

    /**
     * Removes the perk from the player's extended player properties. This method is not actually
     * in use, due to some ConcurrentModificationExceptions and the replacement code involving
     * Iterators, but it is planned to change. For now, just use an iterator.
     * @param player The player
     */
    void removePerk(EntityPlayer player);
}
