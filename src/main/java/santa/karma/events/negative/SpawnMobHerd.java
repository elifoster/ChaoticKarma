package santa.karma.events.negative;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import santa.karma.ChaoticKarma;
import santa.karma.api.event.KarmaEventNegative;
import santa.karma.api.KarmaRegistry;
import santa.karma.util.MathHelper;

import java.util.Random;

public class SpawnMobHerd extends KarmaEventNegative {
    public SpawnMobHerd() {
        this.setRequiredKarmaLevel(ChaoticKarma.MIN_KARMA);
        this.setKarmaChance(1800);
    }

    @Override
    public void doEvent(EntityPlayer player, World world) {
        if (playerHasEnoughKarma(player)) {
            Random random = new Random();
            for (int i = 0; i < 30; i++) {
                if (random.nextInt(10) == 5) {
                    int size = KarmaRegistry.herdMobs.size();
                    Class entityClass = KarmaRegistry.herdMobs.get(random.nextInt(size));
                    try {
                        Entity entity = (Entity) entityClass.getConstructor(World.class)
                          .newInstance(world);
                        entity.setWorld(world);
                        double x = player.posX + MathHelper.randomNegOrPos(8, random);
                        double y = player.posY;
                        double z = player.posZ + MathHelper.randomNegOrPos(8, random);
                        entity.setLocationAndAngles(x, y, z, 0.0F, 0.0F);
                        world.spawnEntityInWorld(entity);
                    } catch (NoSuchMethodException e) {
                        FMLLog.warning("The entity class \"%s\" does not have a constructor that"
                          + " takes a single World argument, which is the expected behavior by " +
                          "ChaoticKarma's SpawnMobHerd event. Please report this.", entityClass.getName());
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
