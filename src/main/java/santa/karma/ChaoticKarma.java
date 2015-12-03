package santa.karma;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.entity.monster.*;
import net.minecraftforge.common.MinecraftForge;
import santa.karma.api.KarmaRegistry;
import santa.karma.api.MobIgnoranceRegistry;
import santa.karma.events.EventSpawner;
import santa.karma.events.negative.LightningStrike;
import santa.karma.events.positive.GiveGoods;
import santa.karma.perks.PerkApplier;
import santa.karma.perks.negative.mobignorance.LevelFourMobIgnorance;
import santa.karma.perks.negative.mobignorance.LevelOneMobIgnorance;
import santa.karma.perks.negative.mobignorance.LevelThreeMobIgnorance;
import santa.karma.perks.negative.mobignorance.LevelTwoMobIgnorance;
import santa.karma.perks.positive.DoubleBoneMeal;
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
        registerMobsToIgnore();
        registerNegativeDefaultEvents();
        registerPositiveDefaultEvents();
        registerPositiveDefaultPerks();
        registerNegativeDefaultPerks();
        MinecraftForge.EVENT_BUS.register(new ExtendedPlayerInitializer());
        MinecraftForge.EVENT_BUS.register(new EventSpawner());
        MinecraftForge.EVENT_BUS.register(new PerkApplier());
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

    private void registerNegativeDefaultPerks() {
        KarmaRegistry.registerNegativePerk(new LevelOneMobIgnorance(),
          "ChaoticKarma-LevelOneMobIgnorance");
        KarmaRegistry.registerNegativePerk(new LevelTwoMobIgnorance(),
          "ChaoticKarma-LevelTwoMobIgnorance");
        KarmaRegistry.registerNegativePerk(new LevelThreeMobIgnorance(),
          "ChaoticKarma-LevelThreeMobIgnorance");
        KarmaRegistry.registerNegativePerk(new LevelFourMobIgnorance(),
          "ChaoticKarma-LevelFourMobIgnorance");
    }

    /**
     * Registers all of the default positive karma perks included in the mod.
     */
    private void registerPositiveDefaultPerks() {
        KarmaRegistry.registerPositivePerk(new DoubleBoneMeal(), "ChaoticKarma-DoubleBoneMeal",
          true);
    }

    /**
     * Registers all of the mob ignorance levels.
     */
    private void registerMobsToIgnore() {
        MobIgnoranceRegistry.registerLevelOne(EntitySilverfish.class);
        MobIgnoranceRegistry.registerLevelOne(EntitySpider.class);

        MobIgnoranceRegistry.registerLevelTwo(EntitySkeleton.class);
        MobIgnoranceRegistry.registerLevelTwo(EntityZombie.class);
        MobIgnoranceRegistry.registerLevelTwo(EntitySkeleton.class);
        MobIgnoranceRegistry.registerLevelTwo(EntitySlime.class);
        MobIgnoranceRegistry.registerLevelTwo(EntityCaveSpider.class);

        MobIgnoranceRegistry.registerLevelThree(EntityCreeper.class);
        MobIgnoranceRegistry.registerLevelThree(EntityBlaze.class);
        MobIgnoranceRegistry.registerLevelThree(EntityMagmaCube.class);

        MobIgnoranceRegistry.registerLevelFour(EntityGhast.class);
        MobIgnoranceRegistry.registerLevelFour(EntityGiantZombie.class);
        MobIgnoranceRegistry.registerLevelFour(EntityWitch.class);
        MobIgnoranceRegistry.registerLevelFour(EntityEnderman.class);
    }
}
