package santa.karma.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import santa.karma.ChaoticKarma;
import santa.karma.player.ExtendedPlayer;

import java.util.ArrayList;

public class KarmaRegistry {
    /**
     * All of the registered negative events.
     */
    public static ArrayList<KarmaEventNegative> eventNegatives = new ArrayList();

    /**
     * All of the registered positive events.
     */
    public static ArrayList<KarmaEventPositive> eventPositives = new ArrayList();

    /**
     * The resources this mod considers valuable. Includes default values.
     */
    public static ArrayList<ItemStack> valuablesGiveGoods = new ArrayList() {{
        add(new ItemStack(Items.diamond));
        add(new ItemStack(Items.emerald));
        add(new ItemStack(Items.iron_ingot));
        add(new ItemStack(Items.gold_ingot));
        add(new ItemStack(Items.gold_nugget));
        add(new ItemStack(Items.skull));
        add(new ItemStack(Items.redstone));
        add(new ItemStack(Items.flint));
        add(new ItemStack(Items.quartz));
        add(new ItemStack(Items.coal));
        add(new ItemStack(Blocks.web));
        add(new ItemStack(Items.blaze_rod));
    }};

    /**
     * Adds karma to the player.
     * @param player The player to give karma.
     * @param amount The amount to add.
     * @return The new amount of karma.
     */
    public static int addKarma(EntityPlayer player, int amount) {
        ExtendedPlayer nbt = (ExtendedPlayer) player.getExtendedProperties(ChaoticKarma
          .EXTENDEDPLAYER);
        if (nbt.karma < ChaoticKarma.MAX_KARMA) {
            nbt.karma += amount;
        }
        return nbt.karma;
    }

    /**
     * Removes karma from the player.
     * @param player The player to remove karma from.
     * @param amount The amount to take.
     * @return The new amount of karma.
     */
    public static int removeKarma(EntityPlayer player, int amount) {
        ExtendedPlayer nbt = (ExtendedPlayer) player.getExtendedProperties(ChaoticKarma
          .EXTENDEDPLAYER);
        if (nbt.karma > ChaoticKarma.MIN_KARMA) {
            nbt.karma -= amount;
        }
        return nbt.karma;
    }

    /**
     * Registers the negative karma event. Must be done in the initialization phase.
     * @param event The event to register.
     */
    public static void registerNegativeEvent(KarmaEventNegative event) {
        eventNegatives.add(event);
    }

    /**
     * Registers the positive karma event. Must be done in the initialization phase.
     * @param event The event to register.
     */
    public static void registerPositiveEvent(KarmaEventPositive event) {
        eventPositives.add(event);
    }

    /**
     * Adds an ItemStack to the list of valuable resources for the GiveGoods event.
     * @param newItemStack The new ItemStack to add.
     */
    public static void addItemStackToValuables(ItemStack newItemStack) {
        valuablesGiveGoods.add(newItemStack);
    }

    /**
     * Removes the ItemStack from the list of valuable resources, if it exists.
     * @param itemstack The ItemStack to remove.
     */
    public static void removeItemStackFromValuables(ItemStack itemstack) {
        if (valuablesGiveGoods.contains(itemstack)) {
            valuablesGiveGoods.remove(itemstack);
        }
    }
}
