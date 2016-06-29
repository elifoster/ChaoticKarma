package santa.karma.util;

import net.minecraft.entity.player.EntityPlayer;
import santa.karma.ChaoticKarma;
import santa.karma.player.IPlayerData;

public class EntityUtil {
    /**
     * Helper method to get our player data capability. This mostly exists so I don't need to suppress warnings all over the mod.
     * @param player The player
     * @return The capability.
     */
    @SuppressWarnings("ConstantConditions")
    public static IPlayerData getPlayerData(EntityPlayer player) {
        return player.getCapability(ChaoticKarma.PLAYER_DATA, null);
    }
}
