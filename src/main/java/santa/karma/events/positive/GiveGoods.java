package santa.karma.events.positive;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import santa.karma.api.event.KarmaEventPositive;
import santa.karma.api.KarmaRegistry;

import java.util.ArrayList;

public class GiveGoods extends KarmaEventPositive {
    public GiveGoods() {
        setRequiredKarmaLevel(1800);
        setKarmaChance(1500);
    }

    @Override
    public void doEvent(EntityPlayer player, World world) {
        ArrayList<ItemStack> valuables = KarmaRegistry.valuablesGiveGoods;
        ItemStack stack = valuables.get(RANDOM.nextInt(valuables.size()));
        stack.stackSize = RANDOM.nextInt(4);
        player.inventory.addItemStackToInventory(stack);
    }
}
