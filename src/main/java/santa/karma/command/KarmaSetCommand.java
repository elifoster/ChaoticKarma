package santa.karma.command;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.MinecraftForge;
import santa.karma.ChaoticKarma;
import santa.karma.gameevents.KarmaUpdateEvent;
import santa.karma.player.ExtendedPlayer;

import java.util.List;

public class KarmaSetCommand implements ICommand {
    String playerName;

    @Override
    public String getCommandName() {
        return "karmaset";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "karmaset <player> <amount>";
    }

    @Override
    public List getCommandAliases() {
        return null;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length < 2 || args.length > 2) {
            throw new WrongUsageException(this.getCommandUsage(sender));
        } else {
            EntityPlayer player = (EntityPlayer) sender;
            this.playerName = args[0];
            ExtendedPlayer nbt = (ExtendedPlayer) player.getExtendedProperties(ChaoticKarma
              .EXTENDEDPLAYER);
            int amount = Integer.parseInt(args[1]);
            if (amount < ChaoticKarma.MIN_KARMA) {
                sender.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted
                  ("command.set.fail.low", amount, ChaoticKarma.MIN_KARMA)));
            } else if (amount > ChaoticKarma.MAX_KARMA) {
                sender.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted
                  ("command.set.fail.high", amount, ChaoticKarma.MAX_KARMA)));
            } else {
                int old = nbt.karma;
                nbt.karma = amount;
                MinecraftForge.EVENT_BUS.post(new KarmaUpdateEvent(old, player, amount));
                sender.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted
                  ("command.set.success", player.getDisplayName(), amount)));
            }
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        EntityPlayer player = (EntityPlayer) sender;
        return MinecraftServer.getServer().getConfigurationManager().func_152596_g(
          player.getGameProfile());
    }

    @Override
    public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_) {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) {
        return false;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
