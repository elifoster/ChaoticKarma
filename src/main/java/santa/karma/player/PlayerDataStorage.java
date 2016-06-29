package santa.karma.player;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import santa.karma.api.KarmaRegistry;
import santa.karma.api.perk.KarmaPerkNegative;
import santa.karma.api.perk.KarmaPerkPositive;

import java.util.ArrayList;

public class PlayerDataStorage implements Capability.IStorage<IPlayerData> {
    @Override
    public NBTBase writeNBT(Capability<IPlayerData> capability, IPlayerData instance, EnumFacing side) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("Karma", instance.getKarma());

        NBTTagList positivePerkTag = new NBTTagList();
        ArrayList<KarmaPerkPositive> positivePerks = instance.getPositivePerks();
        for (KarmaPerkPositive perk : positivePerks) {
            String id = KarmaRegistry.getIDByPositivePerk(perk);
            if (id != null) {
                positivePerkTag.appendTag(new NBTTagString(id));
            }
        }

        NBTTagList negativePerkTag = new NBTTagList();
        ArrayList<KarmaPerkNegative> negativePerks = instance.getNegativePerks();
        for (KarmaPerkNegative perk : negativePerks) {
            String id = KarmaRegistry.getIDByNegativePerk(perk);
            if (id != null) {
                negativePerkTag.appendTag(new NBTTagString(id));
            }
        }

        nbt.setTag("PositivePerks", positivePerkTag);
        nbt.setTag("NegativePerks", negativePerkTag);

        return nbt;
    }

    @Override
    public void readNBT(Capability<IPlayerData> capability, IPlayerData instance, EnumFacing side, NBTBase nbtBase) {
        NBTTagCompound nbt = (NBTTagCompound) nbtBase;
        instance.setKarma(nbt.getInteger("Karma"));

        NBTTagList positivePerkIDs = nbt.getTagList("PositivePerks", Constants.NBT.TAG_STRING);
        ArrayList<KarmaPerkPositive> positivePerks = new ArrayList<>();
        for (int i = 0; i < positivePerkIDs.tagCount(); i++) {
            KarmaPerkPositive perk = KarmaRegistry.getPositivePerkByID(positivePerkIDs.getStringTagAt(i));
            if (perk != null) {
                positivePerks.add(perk);
            }
        }

        NBTTagList negativePerkIDs = nbt.getTagList("NegativePerks", Constants.NBT.TAG_STRING);
        ArrayList<KarmaPerkNegative> negativePerks = new ArrayList<>();
        for (int i = 0; i < negativePerkIDs.tagCount(); i++) {
            KarmaPerkNegative perk = KarmaRegistry.getNegativePerkByID(negativePerkIDs.getStringTagAt(i));
            if (perk != null) {
                negativePerks.add(perk);
            }
        }

        instance.setPositivePerks(positivePerks);
        instance.setNegativePerks(negativePerks);
    }
}
