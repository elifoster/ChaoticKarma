package santa.karma.command;

import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import santa.karma.ChaoticKarma;
import santa.karma.player.ExtendedPlayer;

import java.util.Collections;
import java.util.List;

public class KarmaGetCommand implements ICommand {
    @Override
    public String getCommandName() {
        return "karmaget";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "karmaget <player>";
    }

    @Override
    public List<String> getCommandAliases() {
        return Collections.emptyList();
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 1 || args.length > 1) {
            throw new WrongUsageException(this.getCommandUsage(sender));
        } else {
            EntityPlayer target = CommandBase.getPlayer(sender, args[0]);
            ExtendedPlayer nbt = (ExtendedPlayer) target.getExtendedProperties(ChaoticKarma
              .EXTENDEDPLAYER);
            sender.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted
              ("command.get", args[0], nbt.karma)));
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
