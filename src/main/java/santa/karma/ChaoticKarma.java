package santa.karma;

import net.minecraft.entity.monster.*;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import santa.karma.api.KarmaRegistry;
import santa.karma.api.MobIgnoranceRegistry;
import santa.karma.command.KarmaGetCommand;
import santa.karma.command.KarmaSetCommand;
import santa.karma.events.EventSpawner;
import santa.karma.events.negative.LightningStrike;
import santa.karma.events.negative.SetOnFire;
import santa.karma.events.negative.SpawnMobHerd;
import santa.karma.events.positive.GiveExperience;
import santa.karma.events.positive.GiveGoods;
import santa.karma.events.positive.SpawnChest;
import santa.karma.handler.GainingKarmaHandler;
import santa.karma.perks.PerkApplier;
import santa.karma.perks.negative.MobIgnorance;
import santa.karma.perks.positive.DoubleBoneMeal;
import santa.karma.player.IPlayerData;
import santa.karma.player.PlayerDataInitializer;
import santa.karma.player.PlayerDataStorage;

@Mod(name = "ChaoticKarma", modid = ChaoticKarma.MOD_ID, version = "2.0.1", acceptedMinecraftVersions = "[1.9,1.10.2]")
public class ChaoticKarma {
    public static final String MOD_ID = "chaotickarma";
    public static final int MAX_KARMA = 2000;
    public static final int DEFAULT_KARMA = 1000;
    public static final int MIN_KARMA = 0;

    @CapabilityInject(IPlayerData.class)
    public static final Capability<IPlayerData> PLAYER_DATA = null;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        CapabilityManager.INSTANCE.register(IPlayerData.class, new PlayerDataStorage(),
          IPlayerData.DefaultImplemenation.class);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        registerMobsToIgnore();
        registerHerdMobs();
        registerNegativeDefaultEvents();
        registerPositiveDefaultEvents();
        registerPositiveDefaultPerks();
        registerNegativeDefaultPerks();
        MinecraftForge.EVENT_BUS.register(new PlayerDataInitializer());
        MinecraftForge.EVENT_BUS.register(new EventSpawner());
        MinecraftForge.EVENT_BUS.register(new PerkApplier());
        MinecraftForge.EVENT_BUS.register(new GainingKarmaHandler());
    }

    @Mod.EventHandler
    public void onServerStart(FMLServerStartingEvent event) {
        event.registerServerCommand(new KarmaGetCommand());
        event.registerServerCommand(new KarmaSetCommand());
    }

    /**
     * Registers all of the default negative karma events included in the mod.
     */
    private void registerNegativeDefaultEvents() {
        KarmaRegistry.registerNegativeEvent(new LightningStrike());
        KarmaRegistry.registerNegativeEvent(new SpawnMobHerd());
        KarmaRegistry.registerNegativeEvent(new SetOnFire());
    }

    /**
     * Registers all of the default positive karma events included in the mod.
     */
    private void registerPositiveDefaultEvents() {
        KarmaRegistry.registerPositiveEvent(new GiveGoods());
        KarmaRegistry.registerPositiveEvent(new SpawnChest());
        KarmaRegistry.registerPositiveEvent(new GiveExperience());
    }

    private void registerNegativeDefaultPerks() {
        KarmaRegistry.registerNegativePerk(new MobIgnorance((DEFAULT_KARMA / 4) * 3,
          MobIgnoranceRegistry.levelOneMobs), "ChaoticKarma-LevelOneMobIgnorance", true);
        KarmaRegistry.registerNegativePerk(new MobIgnorance((DEFAULT_KARMA / 2),
          MobIgnoranceRegistry.levelTwoMobs), "ChaoticKarma-LevelTwoMobIgnorance", true);
        KarmaRegistry.registerNegativePerk(new MobIgnorance((DEFAULT_KARMA / 4),
          MobIgnoranceRegistry.levelThreeMobs), "ChaoticKarma-LevelThreeMobIgnorance", true);
        KarmaRegistry.registerNegativePerk(new MobIgnorance(MIN_KARMA,
          MobIgnoranceRegistry.levelFourMobs), "ChaoticKarma-LevelFourMobIgnorance", true);
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
        MobIgnoranceRegistry.registerLevelTwo(EntityEndermite.class);
        MobIgnoranceRegistry.registerLevelTwo(EntitySlime.class);
        MobIgnoranceRegistry.registerLevelTwo(EntityCaveSpider.class);

        MobIgnoranceRegistry.registerLevelThree(EntityCreeper.class);
        MobIgnoranceRegistry.registerLevelThree(EntityBlaze.class);
        MobIgnoranceRegistry.registerLevelThree(EntitySmallFireball.class);
        MobIgnoranceRegistry.registerLevelThree(EntityMagmaCube.class);
        MobIgnoranceRegistry.registerLevelFour(EntityShulker.class);

        MobIgnoranceRegistry.registerLevelFour(EntityGhast.class);
        MobIgnoranceRegistry.registerLevelFour(EntityLargeFireball.class);
        MobIgnoranceRegistry.registerLevelFour(EntityGiantZombie.class);
        MobIgnoranceRegistry.registerLevelFour(EntityWitch.class);
        MobIgnoranceRegistry.registerLevelFour(EntityEnderman.class);
        MobIgnoranceRegistry.registerLevelFour(EntityGuardian.class);
    }

    /**
     * Registers all of the mobs for the herd spawning event.
     */
    private void registerHerdMobs() {
        KarmaRegistry.registerHerdMob(EntityBlaze.class);
        KarmaRegistry.registerHerdMob(EntityCaveSpider.class);
        KarmaRegistry.registerHerdMob(EntitySilverfish.class);
        KarmaRegistry.registerHerdMob(EntitySkeleton.class);
        KarmaRegistry.registerHerdMob(EntitySlime.class);
        KarmaRegistry.registerHerdMob(EntitySpider.class);
        KarmaRegistry.registerHerdMob(EntityWitch.class);
        KarmaRegistry.registerHerdMob(EntityZombie.class);
    }
}
