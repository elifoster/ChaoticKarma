package santa.karma.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLLog;
import santa.karma.ChaoticKarma;
import santa.karma.api.event.KarmaEventPositive;
import santa.karma.api.perk.KarmaPerkNegative;
import santa.karma.api.perk.KarmaPerkPositive;
import santa.karma.gameevents.KarmaUpdateEvent;
import santa.karma.api.event.KarmaEventNegative;
import santa.karma.player.IPlayerData;
import santa.karma.util.EntityUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class KarmaRegistry {
    /**
     * All of the registered negative events.
     */
    public static ArrayList<KarmaEventNegative> eventNegatives = new ArrayList<>();

    /**
     * All of the registered positive events.
     */
    public static ArrayList<KarmaEventPositive> eventPositives = new ArrayList<>();

    /**
     * All of the registered positive perks.
     */
    public static HashMap<String, KarmaPerkPositive> perkPositives = new HashMap<>();

    /**
     * All of the registered negative perks.
     */
    public static HashMap<String, KarmaPerkNegative> perkNegatives = new HashMap<>();

    /**
     * The resources this mod considers valuable. Includes default vanilla minecraft values.
     */
    public static ArrayList<ItemStack> valuablesGiveGoods = new ArrayList<ItemStack>() {{
        add(new ItemStack(Items.DIAMOND));
        add(new ItemStack(Items.EMERALD));
        add(new ItemStack(Items.IRON_INGOT));
        add(new ItemStack(Items.GOLD_INGOT));
        add(new ItemStack(Items.GOLD_NUGGET));
        add(new ItemStack(Items.SKULL));
        add(new ItemStack(Items.REDSTONE));
        add(new ItemStack(Items.FLINT));
        add(new ItemStack(Items.QUARTZ));
        add(new ItemStack(Items.COAL));
        add(new ItemStack(Blocks.WEB));
        add(new ItemStack(Items.BLAZE_ROD));
    }};

    /**
     * The entity classes that the SpawnMobHerd event can spawn.
     */
    public static ArrayList<Class> herdMobs = new ArrayList<>();

    /**
     * Adds karma to the player.
     * @param player The player to give karma.
     * @param amount The amount to add.
     * @return The new amount of karma.
     */
    public static int addKarma(EntityPlayer player, int amount) {
        IPlayerData data = EntityUtil.getPlayerData(player);
        int oldKarma = data.getKarma();
        if (oldKarma < ChaoticKarma.MAX_KARMA) {
            data.setKarma(oldKarma + amount);
            MinecraftForge.EVENT_BUS.post(new KarmaUpdateEvent(oldKarma, amount, player, data.getKarma()));
        }
        return data.getKarma();
    }

    /**
     * Removes karma from the player.
     * @param player The player to remove karma from.
     * @param amount The amount to take.
     * @return The new amount of karma.
     */
    public static int removeKarma(EntityPlayer player, int amount) {
        IPlayerData data = EntityUtil.getPlayerData(player);
        int oldKarma = data.getKarma();
        if (oldKarma > ChaoticKarma.MIN_KARMA) {
            data.setKarma(oldKarma - amount);
            MinecraftForge.EVENT_BUS.post(new KarmaUpdateEvent(oldKarma, amount, player, data.getKarma()));
        }
        return data.getKarma();
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
     * @param isEventHandler Whether to register the perk in the MCF EVENT_BUS.
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

    /**
     * Registers a class as a herd mob for the SpawnMobHerd event.
     * @param cls The entity class.
     */
    public static void registerHerdMob(Class cls) {
        herdMobs.add(cls);
    }
}
