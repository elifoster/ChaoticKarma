package santa.karma.perks.negative.mobignorance;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import santa.karma.ChaoticKarma;
import santa.karma.api.KarmaPerkNegative;
import santa.karma.api.MobIgnoranceRegistry;
import santa.karma.player.ExtendedPlayer;

public class LevelFourMobIgnorance extends KarmaPerkNegative {
    public LevelFourMobIgnorance() {
        this.setRequiredKarmaLevel(ChaoticKarma.MIN_KARMA);
    }

    @SubscribeEvent
    public void ignoreMobs(LivingSetAttackTargetEvent event) {
        if (event.target instanceof EntityPlayer) {
            ExtendedPlayer nbt = (ExtendedPlayer) event.target.getExtendedProperties(ChaoticKarma
              .EXTENDEDPLAYER);
            if (nbt.negativePerks.contains(this)) {
                for (Class entityClass : MobIgnoranceRegistry.levelFourMobs) {
                    if (entityClass.isInstance(event.entityLiving)) {
                        event.entityLiving.setRevengeTarget(null);
                    }
                }
            }
        }
    }
}
