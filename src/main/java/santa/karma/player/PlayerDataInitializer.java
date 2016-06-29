package santa.karma.player;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import santa.karma.ChaoticKarma;

public class PlayerDataInitializer {
    @SubscribeEvent
    public void initialize(AttachCapabilitiesEvent.Entity event) {
        Entity entity = event.getEntity();
        if (entity instanceof EntityPlayer) {
            event.addCapability(new ResourceLocation(ChaoticKarma.MOD_ID, "IPlayerData"), new PlayerDataSerializer());
        }
    }
}
