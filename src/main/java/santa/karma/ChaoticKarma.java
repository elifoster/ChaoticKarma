package santa.karma;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import santa.karma.api.KarmaRegistry;
import santa.karma.events.EventSpawner;
import santa.karma.events.negative.LightningStrike;
import santa.karma.events.positive.GiveGoods;
import santa.karma.player.ExtendedPlayerInitializer;

@Mod(name = "ChaoticKarma", modid = "chaotickarma", version = "1.0.0")
public class ChaoticKarma {
    public static final String EXTENDEDPLAYER = "ChaoticKarmaPlayer";
    public static final int MAX_KARMA = 2000;
    public static final int DEFAULT_KARMA = 1000;
    public static final int MIN_KARMA = 0;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {}

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        registerNegativeDefaultEvents();
        registerPositiveDefaultEvents();
        MinecraftForge.EVENT_BUS.register(new ExtendedPlayerInitializer());
        MinecraftForge.EVENT_BUS.register(new EventSpawner());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {}

    /**
     * Registers all of the default negative karma events included in the mod.
     */
    private void registerNegativeDefaultEvents() {
        KarmaRegistry.registerNegativeEvent(new LightningStrike());
    }

    /**
     * Registers all of the default positive karma events included in the mod.
     */
    private void registerPositiveDefaultEvents() {
        KarmaRegistry.registerPositiveEvent(new GiveGoods());
    }
}
