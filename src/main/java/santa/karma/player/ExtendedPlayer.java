package santa.karma.player;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.util.Constants;
import santa.karma.ChaoticKarma;
import santa.karma.api.KarmaPerkNegative;
import santa.karma.api.KarmaPerkPositive;
import santa.karma.api.KarmaRegistry;

import java.util.ArrayList;

public class ExtendedPlayer implements IExtendedEntityProperties {
    public World world;
    public EntityPlayer player;
    public int karma;
    public ArrayList<KarmaPerkPositive> positivePerks;
    public ArrayList<KarmaPerkNegative> negativePerks;

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        NBTTagList positivePerkTag = new NBTTagList();
        if (this.positivePerks != null) {
            for (KarmaPerkPositive perk : this.positivePerks) {
                String id = KarmaRegistry.getIDByPositivePerk(perk);
                if (id != null) {
                    positivePerkTag.appendTag(new NBTTagString(id));
                }
            }
        }

        NBTTagList negativePerkTag = new NBTTagList();
        if (this.negativePerks != null) {
            for (KarmaPerkNegative perk : this.negativePerks) {
                String id = KarmaRegistry.getIDByNegativePerk(perk);
                if (id != null) {
                    negativePerkTag.appendTag(new NBTTagString(id));
                }
            }
        }

        compound.setInteger("karma", this.karma);
        compound.setTag("positivePerks", positivePerkTag);
        compound.setTag("negativePerks", negativePerkTag);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        NBTTagList positivePerkIDs = compound.getTagList("positivePerks", Constants.NBT.TAG_STRING);
        for (int i = 0; i < positivePerkIDs.tagCount(); i++) {
            KarmaPerkPositive perk = KarmaRegistry.getPositivePerkByID(positivePerkIDs
              .getStringTagAt(i));
            if (perk != null) {
                this.positivePerks.add(i, perk);
            }
        }

        NBTTagList negativePerkIDs = compound.getTagList("negativePerks", Constants.NBT.TAG_STRING);
        for (int i = 0; i < negativePerkIDs.tagCount(); i++) {
            KarmaPerkNegative perk = KarmaRegistry.getNegativePerkByID(positivePerkIDs
              .getStringTagAt(i));
            if (perk != null) {
                this.negativePerks.add(i, perk);
            }
        }

        this.karma = compound.getInteger("karma");
    }

    @Override
    public void init(Entity entity, World world) {
        this.world = world;
        this.player = (EntityPlayer) entity;
        this.karma = ChaoticKarma.DEFAULT_KARMA;
        this.positivePerks = new ArrayList();
        this.negativePerks = new ArrayList();
    }
}
