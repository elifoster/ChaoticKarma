package santa.karma.player;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import santa.karma.ChaoticKarma;

public class ExtendedPlayer implements IExtendedEntityProperties {
    public World world;
    public EntityPlayer player;
    public int karma;

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        compound.setInteger("karma", this.karma);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        this.karma = compound.getInteger("karma");
    }

    @Override
    public void init(Entity entity, World world) {
        this.world = world;
        this.player = (EntityPlayer) entity;
        this.karma = ChaoticKarma.DEFAULT_KARMA;
    }
}
