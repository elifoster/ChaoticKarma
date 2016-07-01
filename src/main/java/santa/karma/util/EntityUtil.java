package santa.karma.util;

import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

    /**
     * Gets whether the item is used to tame the entity and the entity is not tamed. Well, not really.
     * It does if the entity is a wolf or ocelot, but because of the lack of this API in EntityTameable, there is no
     * reliable way to check it aside from this. This will check if it is the *breeding* item for the entity if it is
     * not a wolf or ocelot.
     * @param entity The entity to check.
     * @param check The item to check.
     * @return Whether the entity is a wolf and the item is a bone, the entity is a ocelot and the item is a fish, or
     *         the item is used to breed the entity.
     */
    public static boolean canTame(EntityTameable entity, ItemStack check) {
        if (check == null || entity.isTamed()) {
            return false;
        }

        Item item = check.getItem();

        if (entity instanceof EntityWolf) {
            return item == Items.BONE;
        }

        if (entity instanceof EntityOcelot) {
            return item == Items.FISH;
        }

        return entity.isBreedingItem(check);
    }
}
