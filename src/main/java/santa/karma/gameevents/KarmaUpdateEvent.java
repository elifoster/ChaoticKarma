package santa.karma.gameevents;

import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.entity.player.EntityPlayer;

public class KarmaUpdateEvent extends Event {
    /**
     * The previous karma level that the player had. In other words, the amount BEFORE the event.
     */
    public final int previousAmount;

    /**
     * The amount of karma being added to the player.
     */
    public final int updateAmount;

    public final int newAmount;

    /**
     * The player that the karma is being applied to.
     */
    public final EntityPlayer player;

    public KarmaUpdateEvent(int previousAmount, int updateAmount, EntityPlayer player, int newAmount) {
        this.previousAmount = previousAmount;
        this.updateAmount = updateAmount;
        this.player = player;
        this.newAmount = newAmount;
    }
}
