package santa.karma.api.event;

import net.minecraft.entity.player.EntityPlayer;
import santa.karma.ChaoticKarma;
import santa.karma.player.IPlayerData;
import santa.karma.util.EntityUtil;

import java.util.Random;

public abstract class KarmaEventPositive extends KarmaEvent {
    @Override
    public boolean playerHasEnoughKarma(EntityPlayer player) {
        IPlayerData data = EntityUtil.getPlayerData(player);
        return (data.getKarma() >= getRequiredKarmaLevel());
    }
}
