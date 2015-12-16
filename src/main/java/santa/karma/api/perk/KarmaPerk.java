package santa.karma.api.perk;

import net.minecraft.entity.player.EntityPlayer;
import santa.karma.ChaoticKarma;
import santa.karma.api.perk.IKarmaPerk;
import santa.karma.player.ExtendedPlayer;

/**
 * The KarmaPerk class contains default implementations for all perk-related methods EXCEPT
 * hasPerk and applyPerk, because they rely on the type of perk that it is.
 */
public class KarmaPerk implements IKarmaPerk {
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

        this.levelNeeded = level;
    }

    @Override
    public int getRequiredKarmaLevel() {
        return this.levelNeeded;
    }

    /**
     * This method should never be used. Instead, use the implementation of it at
     * KarmaPerkPositive and KarmaPerkNegative.
     * @param player The player to check.
     * @return
     */
    @Override
    public boolean hasPerk(EntityPlayer player) {
        return false;
    }

    /**
     * This method should never be used. Instead, use the implementation of it at
     * KarmaPerkPositive and KarmaPerkNegative.
     * @param player The player.
     */
    @Override
    public void applyPerk(EntityPlayer player) {}

    @Override
    public void removePerk(EntityPlayer player) {
        ExtendedPlayer nbt = (ExtendedPlayer) player.getExtendedProperties(ChaoticKarma
          .EXTENDEDPLAYER);
        if (hasPerk(player)) {
            nbt.negativePerks.remove(this);
        }
    }
}
