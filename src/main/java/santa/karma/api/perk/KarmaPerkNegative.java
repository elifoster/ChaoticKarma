package santa.karma.api.perk;

import net.minecraft.entity.player.EntityPlayer;
import santa.karma.ChaoticKarma;
import santa.karma.player.ExtendedPlayer;

public class KarmaPerkNegative extends KarmaPerk {
    @Override
    public void applyPerk(EntityPlayer player) {
        ExtendedPlayer nbt = (ExtendedPlayer) player.getExtendedProperties(ChaoticKarma
          .EXTENDEDPLAYER);
        if (!hasPerk(player)) {
            nbt.negativePerks.add(this);
        }
    }

    @Override
    public void removePerk(EntityPlayer player) {
        ExtendedPlayer nbt = (ExtendedPlayer) player.getExtendedProperties(ChaoticKarma
          .EXTENDEDPLAYER);
        if (hasPerk(player)) {
            nbt.negativePerks.remove(this);
        }
    }

    @Override
    public boolean hasPerk(EntityPlayer player) {
        ExtendedPlayer nbt = (ExtendedPlayer) player.getExtendedProperties(ChaoticKarma
          .EXTENDEDPLAYER);
        return (nbt.negativePerks.contains(this));
    }
}
