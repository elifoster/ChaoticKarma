package santa.karma.perks.negative;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import santa.karma.api.perk.KarmaPerkNegative;

import java.util.ArrayList;

public class MobIgnorance extends KarmaPerkNegative {
    public ArrayList<Class> ignoredMobs;

    /**
     * Creates a new Mob Ignorance perk.
     * @param level The required level
     * @param mobs The array of mob classes that this particular mob ignorance perk will ignore.
     */
    public MobIgnorance(int level, ArrayList<Class> mobs) {
        setRequiredKarmaLevel(level);
        ignoredMobs = mobs;
    }

    @SubscribeEvent
    public void preventTargetSet(LivingSetAttackTargetEvent event) {
        Entity entity = event.getEntity();
        if (shouldBeIgnored(event.getTarget(), entity)) {
            ((EntityLiving) entity).setAttackTarget(null);
            event.getEntityLiving().setRevengeTarget(null);
        }
    }

    @SubscribeEvent
    public void preventAttack(LivingAttackEvent event) {
        if (shouldBeIgnored(event.getEntity(), event.getSource().getSourceOfDamage())) {
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
        if (player instanceof EntityPlayer && hasPerk((EntityPlayer) player)) {
            for (Class entityClass : this.ignoredMobs) {
                if (entityClass.isInstance(source)) {
                    return true;
                }
            }
        }
        return false;
    }
}
