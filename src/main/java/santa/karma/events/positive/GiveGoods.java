package santa.karma.events.positive;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import santa.karma.api.KarmaEventPositive;
import santa.karma.api.KarmaRegistry;

import java.util.ArrayList;
import java.util.Random;

public class GiveGoods extends KarmaEventPositive {
    public GiveGoods() {
        this.setRequiredKarmaLevel(1800);
        this.setKarmaChance(1200);
    }

    @Override
    public void doEvent(EntityPlayer player, World world) {
        if (playerHasEnoughKarma(player)) {
            Random random = new Random();
            ArrayList<ItemStack> valuables = KarmaRegistry.valuablesGiveGoods;
            ItemStack stack = valuables.get(random.nextInt(valuables.size()));
            stack.stackSize = random.nextInt(4);
            player.inventory.addItemStackToInventory(stack);
        }
    }
}
