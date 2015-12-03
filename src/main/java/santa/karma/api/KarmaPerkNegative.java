package santa.karma.api;

import net.minecraft.entity.player.EntityPlayer;
import santa.karma.ChaoticKarma;
import santa.karma.player.ExtendedPlayer;

public class KarmaPerkNegative {
    private int levelNeeded;

    public void setRequiredKarmaLevel(int level) {
        if (level < ChaoticKarma.MIN_KARMA || level > ChaoticKarma.MAX_KARMA) {
            throw new IllegalArgumentException("The set karma level, " + level + ", is too high " +
              "or low. Be sure that it is in between " + ChaoticKarma.MIN_KARMA + " and " +
              ChaoticKarma.MAX_KARMA);
        }

        this.levelNeeded = level;
    }

    public int getRequiredKarmaLevel() {
        return this.levelNeeded;
    }

    public boolean hasPerk(EntityPlayer player) {
        ExtendedPlayer nbt = (ExtendedPlayer) player.getExtendedProperties(ChaoticKarma
          .EXTENDEDPLAYER);
        return (nbt.negativePerks.contains(this));
    }

    public void applyPerk(EntityPlayer player) {
        ExtendedPlayer nbt = (ExtendedPlayer) player.getExtendedProperties(ChaoticKarma
          .EXTENDEDPLAYER);
        if (!hasPerk(player)) {
            nbt.negativePerks.add(this);
        }
    }

    public void removePerk(EntityPlayer player) {
        ExtendedPlayer nbt = (ExtendedPlayer) player.getExtendedProperties(ChaoticKarma
          .EXTENDEDPLAYER);
        if (hasPerk(player)) {
            nbt.negativePerks.remove(this);
        }
    }
}
