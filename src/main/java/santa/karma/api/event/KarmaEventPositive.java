package santa.karma.api.event;

import net.minecraft.entity.player.EntityPlayer;
import santa.karma.ChaoticKarma;
import santa.karma.player.ExtendedPlayer;

public class KarmaEventPositive extends KarmaEvent {
    @Override
    public boolean playerHasEnoughKarma(EntityPlayer player) {
        ExtendedPlayer nbt = (ExtendedPlayer) player.getExtendedProperties(ChaoticKarma
          .EXTENDEDPLAYER);
        return (nbt.karma >= this.getRequiredKarmaLevel());
    }
}
