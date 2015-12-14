package santa.karma.perks.negative;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import santa.karma.api.KarmaPerkNegative;

import java.util.ArrayList;

public class MobIgnorance extends KarmaPerkNegative {
    public ArrayList<Class> ignoredMobs;

    /**
     * Creates a new Mob Ignorance perk.
     * @param level The required level
     * @param mobs The array of mob classes that this particular mob ignorance perk will ignore.
     */
    public MobIgnorance(int level, ArrayList<Class> mobs) {
        this.setRequiredKarmaLevel(level);
        this.ignoredMobs = mobs;
    }

    @SubscribeEvent
    public void preventTargetSet(LivingSetAttackTargetEvent event) {
        if (shouldBeIgnored(event.target, event.entity)) {
            ((EntityLiving) event.entity).setAttackTarget(null);
            event.entityLiving.setRevengeTarget(null);
        }
    }

    @SubscribeEvent
    public void preventAttack(LivingAttackEvent event) {
        if (shouldBeIgnored(event.entity, event.source.getSourceOfDamage())) {
            event.setCanceled(true);
        }
    }

    /**
     * Gets whether the player should be ignored by the source.
     * @param player The player
     * @param source The entity trying to attack the player.
     * @return Whether the player is actually a player, has the perk, and the source is a valid
     * mob that can be ignored.
     */
    private boolean shouldBeIgnored(Entity player, Entity source) {
        if (player instanceof EntityPlayer && this.hasPerk((EntityPlayer) player) &&
          source instanceof EntityLiving) {
            if (this.hasPerk((EntityPlayer) player)) {
                for (Class entityClass : this.ignoredMobs) {
                    if (entityClass.isInstance(source)) {
                        System.out.println("is instance");
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
