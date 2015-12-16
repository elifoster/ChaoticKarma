package santa.karma.player;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent;
import santa.karma.ChaoticKarma;

public class ExtendedPlayerInitializer {
    @SubscribeEvent
    public void initializePlayer(EntityEvent.EntityConstructing event) {
        Entity entity = event.entity;
        if (entity instanceof EntityPlayer) {
            entity.registerExtendedProperties(ChaoticKarma.EXTENDEDPLAYER, new ExtendedPlayer());
        }
    }
}
