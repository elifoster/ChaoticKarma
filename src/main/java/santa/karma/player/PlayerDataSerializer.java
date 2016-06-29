package santa.karma.player;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import santa.karma.ChaoticKarma;

@SuppressWarnings("ConstantConditions")
public class PlayerDataSerializer implements ICapabilitySerializable<NBTTagCompound> {
    IPlayerData instance = ChaoticKarma.PLAYER_DATA.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == ChaoticKarma.PLAYER_DATA;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return hasCapability(capability, facing) ? ChaoticKarma.PLAYER_DATA.<T>cast(instance) : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return (NBTTagCompound) ChaoticKarma.PLAYER_DATA.getStorage().writeNBT(ChaoticKarma.PLAYER_DATA, instance, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        ChaoticKarma.PLAYER_DATA.getStorage().readNBT(ChaoticKarma.PLAYER_DATA, instance, null, nbt);
    }
}
