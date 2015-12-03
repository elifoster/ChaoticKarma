package santa.karma.perks.negative.mobignorance;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import santa.karma.ChaoticKarma;
import santa.karma.api.KarmaPerkNegative;
import santa.karma.api.MobIgnoranceRegistry;
import santa.karma.player.ExtendedPlayer;

public class LevelOneMobIgnorance extends KarmaPerkNegative {
    public LevelOneMobIgnorance() {
        this.setRequiredKarmaLevel((ChaoticKarma.DEFAULT_KARMA / 4) * 3);
    }

    @SubscribeEvent
    public void ignoreMobs(LivingSetAttackTargetEvent event) {
        if (event.target instanceof EntityPlayer) {
            ExtendedPlayer nbt = (ExtendedPlayer) event.target.getExtendedProperties(ChaoticKarma
              .EXTENDEDPLAYER);
            if (nbt.negativePerks.contains(this)) {
                for (Class entityClass : MobIgnoranceRegistry.levelOneMobs) {
                    if (entityClass.isInstance(event.entityLiving)) {
                        event.entityLiving.setRevengeTarget(null);
                    }
                }
            }
        }
    }
}
