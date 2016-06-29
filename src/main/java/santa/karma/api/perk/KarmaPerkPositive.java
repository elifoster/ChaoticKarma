package santa.karma.api.perk;

import net.minecraft.entity.player.EntityPlayer;
import santa.karma.ChaoticKarma;
import santa.karma.player.IPlayerData;
import santa.karma.util.EntityUtil;

import java.util.ArrayList;

public class KarmaPerkPositive extends KarmaPerk {
    @Override
    public void applyPerk(EntityPlayer player) {
        IPlayerData data = EntityUtil.getPlayerData(player);
        if (!hasPerk(player)) {
            ArrayList<KarmaPerkPositive> perks = data.getPositivePerks();
            perks.add(this);
            data.setPositivePerks(perks);
        }
    }

    @Override
    public void removePerk(EntityPlayer player) {
        IPlayerData data = EntityUtil.getPlayerData(player);
        if (hasPerk(player)) {
            ArrayList<KarmaPerkPositive> perks = data.getPositivePerks();
            perks.remove(this);
            data.setPositivePerks(perks);
        }
    }

    @Override
    public boolean hasPerk(EntityPlayer player) {
        IPlayerData data = EntityUtil.getPlayerData(player);
        return data.getPositivePerks().contains(this);
    }
}
