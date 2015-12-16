package santa.karma.api.perk;

import net.minecraft.entity.player.EntityPlayer;
import santa.karma.ChaoticKarma;
import santa.karma.player.ExtendedPlayer;

public class KarmaPerkPositive extends KarmaPerk {
    @Override
    public void applyPerk(EntityPlayer player) {
        ExtendedPlayer nbt = (ExtendedPlayer) player.getExtendedProperties(ChaoticKarma
          .EXTENDEDPLAYER);
        if (!hasPerk(player)) {
            nbt.positivePerks.add(this);
        }
    }

    @Override
    public void removePerk(EntityPlayer player) {
        ExtendedPlayer nbt = (ExtendedPlayer) player.getExtendedProperties(ChaoticKarma
          .EXTENDEDPLAYER);
        if (hasPerk(player)) {
            nbt.positivePerks.remove(this);
        }
    }
}
