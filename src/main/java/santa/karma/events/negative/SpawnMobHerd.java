package santa.karma.events.negative;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLLog;
import santa.karma.ChaoticKarma;
import santa.karma.api.event.KarmaEventNegative;
import santa.karma.api.KarmaRegistry;
import santa.karma.util.MathHelper;

import java.lang.reflect.InvocationTargetException;

public class SpawnMobHerd extends KarmaEventNegative {
    public SpawnMobHerd() {
        setRequiredKarmaLevel(ChaoticKarma.MIN_KARMA);
        setKarmaChance(1800);
    }

    @Override
    public void doEvent(EntityPlayer player, World world) {
        for (int i = 0; i < 30; i++) {
            if (RANDOM.nextInt(10) == 5) {
                int size = KarmaRegistry.herdMobs.size();
                Class entityClass = KarmaRegistry.herdMobs.get(RANDOM.nextInt(size));
                try {
                    Entity entity = (Entity) entityClass.getConstructor(World.class).newInstance(world);
                    entity.setWorld(world);
                    double x = player.posX + MathHelper.randomNegOrPos(8, RANDOM);
                    double y = player.posY;
                    double z = player.posZ + MathHelper.randomNegOrPos(8, RANDOM);
                    entity.setLocationAndAngles(x, y, z, 0.0F, 0.0F);
                    world.spawnEntityInWorld(entity);
                } catch (NoSuchMethodException e) {
                    FMLLog.warning("The entity class \"%s\" does not have a constructor that"
                      + " takes a single World argument, which is the expected behavior by " +
                      "ChaoticKarma's SpawnMobHerd event. Please report this.", entityClass.getName());
                    e.printStackTrace();
                } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
