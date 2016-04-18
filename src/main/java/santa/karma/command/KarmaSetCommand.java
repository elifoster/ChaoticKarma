package santa.karma.command;

import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.MinecraftForge;
import santa.karma.ChaoticKarma;
import santa.karma.gameevents.KarmaUpdateEvent;
import santa.karma.player.ExtendedPlayer;

import java.util.Collections;
import java.util.List;

public class KarmaSetCommand implements ICommand {
    @Override
    public String getCommandName() {
        return "karmaset";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "karmaset <player> <amount>";
    }

    @Override
    public List<String> getCommandAliases() {
        return Collections.emptyList();
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 2 || args.length > 2) {
            throw new WrongUsageException(this.getCommandUsage(sender));
        } else {
            EntityPlayer target = CommandBase.getPlayer(sender, args[0]);
            ExtendedPlayer nbt = (ExtendedPlayer) target.getExtendedProperties(ChaoticKarma
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
                MinecraftForge.EVENT_BUS.post(new KarmaUpdateEvent(old, target, amount));
                sender.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted
                  ("command.set.success", target.getDisplayNameString(), amount)));
            }
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        EntityPlayer player = (EntityPlayer) sender;
        return MinecraftServer.getServer().getConfigurationManager().canSendCommands(
          player.getGameProfile());
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) {
        return false;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public int compareTo(ICommand o) {
        return 0;
    }
}
