package santa.karma.api.perk;

import net.minecraft.entity.player.EntityPlayer;
import santa.karma.ChaoticKarma;
import santa.karma.player.IPlayerData;
import santa.karma.util.EntityUtil;

import java.util.ArrayList;

public class KarmaPerkNegative extends KarmaPerk {
    @Override
    public void applyPerk(EntityPlayer player) {
        IPlayerData data = EntityUtil.getPlayerData(player);
        if (!hasPerk(player)) {
            ArrayList<KarmaPerkNegative> perks = data.getNegativePerks();
            perks.add(this);
            data.setNegativePerks(perks);
        }
    }

    @Override
    public void removePerk(EntityPlayer player) {
        IPlayerData data = EntityUtil.getPlayerData(player);
        if (hasPerk(player)) {
            ArrayList<KarmaPerkNegative> perks = data.getNegativePerks();
            perks.remove(this);
            data.setNegativePerks(perks);
        }
    }

    @Override
    public boolean hasPerk(EntityPlayer player) {
        IPlayerData data = EntityUtil.getPlayerData(player);
        return data.getNegativePerks().contains(this);
    }
}