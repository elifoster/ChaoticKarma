package santa.karma.gameevents;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * KarmaUpdateEvent is fired when a player's karma is updated.
 * The event can not be canceled. This behavior may change, but I'm unsure if it would actually
 * be useful.
 */
public class KarmaUpdateEvent extends Event {
    /**
     * The previous karma level that the player had. In other words, the amount BEFORE the event.
     */
    public final int previousAmount;

    /**
     * The amount of karma being added to the player. Is null if updated through the command.
     * Code expecting null.
     */
    public final Integer updateAmount;

    /**
     * The new amount of karma, or previousAmount + updateAmount.
     */
    public final int newAmount;

    /**
     * The player that the karma is being applied to.
     */
    public final EntityPlayer player;

    /**
     * This constructor is for basic karma updates, typically through KarmaRegistry#addKarma and
     * KarmaRegistry#removeKarma.
     * @param previousAmount The amount of karma before the update.
     * @param updateAmount The amount of karma being added/removed from the player.
     * @param player The player whose karma is being updated.
     * @param newAmount The new amount of karma.
     */
    public KarmaUpdateEvent(int previousAmount, int updateAmount, EntityPlayer player, int newAmount) {
        this.previousAmount = previousAmount;
        this.updateAmount = updateAmount;
        this.player = player;
        this.newAmount = newAmount;
    }

    /**
     * This constructor is for when the update does not necessarily have an amount being
     * added/removed, for example when the karma is being explicitly set to a specific value. In
     * this case, updateAmount will be null.
     * @param previousAmount The previous amount of karma, before the update.
     * @param player The player whose karma is being updated.
     * @param newAmount The new amount of karma.
     */
    public KarmaUpdateEvent(int previousAmount, EntityPlayer player, int newAmount) {
        this.previousAmount = previousAmount;
        this.updateAmount = null;
        this.player = player;
        this.newAmount = newAmount;
    }
}
