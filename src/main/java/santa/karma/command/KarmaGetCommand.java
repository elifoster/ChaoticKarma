package santa.karma.command;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import santa.karma.ChaoticKarma;
import santa.karma.player.ExtendedPlayer;

import java.util.List;

public class KarmaGetCommand implements ICommand {
    @Override
    public int compareTo(Object object) {
        return 0;
    }

    @Override
    public String getCommandName() {
        return "karmaget";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "karmaget <player>";
    }

    @Override
    public List getCommandAliases() {
        return null;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length < 1 || args.length > 1) {
            throw new WrongUsageException(this.getCommandUsage(sender));
        } else {
            EntityPlayer player = (EntityPlayer) sender;
            ExtendedPlayer nbt = (ExtendedPlayer) player.getExtendedProperties(ChaoticKarma
              .EXTENDEDPLAYER);
            sender.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted
              ("command.get", args[0], nbt.karma)));
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
}
