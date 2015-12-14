package santa.karma.api;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import santa.karma.ChaoticKarma;
import santa.karma.gameevents.KarmaUpdateEvent;
import santa.karma.player.ExtendedPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KarmaRegistry {
    /**
     * All of the registered negative events.
     */
    public static ArrayList<KarmaEventNegative> eventNegatives = new ArrayList();

    /**
     * All of the registered positive events.
     */
    public static ArrayList<KarmaEventPositive> eventPositives = new ArrayList();

    public static HashMap<String, KarmaPerkPositive> perkPositives = new HashMap();

    public static HashMap<String, KarmaPerkNegative> perkNegatives = new HashMap();

    /**
     * The resources this mod considers valuable. Includes default vanilla minecraft values.
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
            int old = nbt.karma;
            nbt.karma += amount;
            MinecraftForge.EVENT_BUS.post(new KarmaUpdateEvent(old, amount, player, nbt.karma));
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
            int old = nbt.karma;
            nbt.karma -= amount;
            MinecraftForge.EVENT_BUS.post(new KarmaUpdateEvent(old, amount, player, nbt.karma));
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

    /**
     * Gets the KarmaPerkPositive by its defined ID.
     * @param id The KarmaPerkPositive ID.
     * @return The KarmaPerkPositive.
     */
    public static KarmaPerkPositive getPositivePerkByID(String id) {
        if (perkPositives.containsKey(id)) {
            return perkPositives.get(id);
        } else {
            return null;
        }
    }

    /**
     * Gets the KarmaPerkNegative by its defined ID.
     * @param id The KarmaPerkNegative ID.
     * @return The KarmaPerkNegative.
     */
    public static KarmaPerkNegative getNegativePerkByID(String id) {
        if (perkNegatives.containsKey(id)) {
            return perkNegatives.get(id);
        } else {
            return null;
        }
    }

    /**
     * Gets a given positive perk's string ID.
     * @param perk The perk to get.
     * @return null if it cannot find the value of perk, otherwise the string ID.
     */
    public static String getIDByPositivePerk(KarmaPerkPositive perk) {
        if (perkPositives.containsValue(perk)) {
            for (Map.Entry<String, KarmaPerkPositive> entry : perkPositives.entrySet()) {
                if (entry.getValue() == perk) {
                    return entry.getKey();
                }
            }
        }
        return null;
    }

    /**
     * Gets a given negative perk's string ID.
     * @param perk The perk to get.
     * @return null if it cannot find the value of perk. Otherwise, the string ID.
     */
    public static String getIDByNegativePerk(KarmaPerkNegative perk) {
        if (perkNegatives.containsValue(perk)) {
            for (Map.Entry<String, KarmaPerkNegative> entry : perkNegatives.entrySet()) {
                if (entry.getValue() == perk) {
                    return entry.getKey();
                }
            }
        }
        return null;
    }

    /**
     * Registers a positive perk.
     * @param perk The Perk to register.
     * @param id The unique ID string for the perk.
     * @param isEventHandler whether to register the perk class in the MCF EVENT_BUS.
     */
    public static void registerPositivePerk(KarmaPerkPositive perk, String id, boolean isEventHandler) {
        if (!perkPositives.containsValue(perk)) {
            if (!perkPositives.containsKey(id)) {
                perkPositives.put(id, perk);
                if (isEventHandler) {
                    MinecraftForge.EVENT_BUS.register(perk);
                }
            } else {
                FMLLog.bigWarning("Overlapping Karma Positive Perk IDs! Please report this to the" +
                  " mod developer!");
            }
        } else {
            FMLLog.severe("A mod is trying to register a positive ChaoticKarma perk twice. " +
              "Please report this to that mod's developer.");
        }
    }

    /**
     * Registers a negative perk.
     * @param perk The Perk to register.
     * @param id The unique ID string for the perk.
     */
    public static void registerNegativePerk(KarmaPerkNegative perk, String id, boolean isEventHandler) {
        if (!perkNegatives.containsValue(perk)) {
            if (!perkNegatives.containsKey(id)) {
                perkNegatives.put(id, perk);
                if (isEventHandler) {
                    MinecraftForge.EVENT_BUS.register(perk);
                }
            } else {
                FMLLog.bigWarning("Overlapping Karma Negative Perk IDs! Please report this to the" +
                  " mod developer!");
            }
        } else {
            FMLLog.severe("A mod is trying to register a negative ChaoticKarma perk twice. " +
              "Please report this to that mod's developer.");
        }
    }
}
