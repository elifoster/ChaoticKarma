package santa.karma.command;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
import santa.karma.player.IPlayerData;
import santa.karma.util.EntityUtil;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class KarmaGetCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "karmaget";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "karmaget <player>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 1 || args.length > 1) {
            throw new WrongUsageException(this.getCommandUsage(sender));
        } else {
            EntityPlayer target = CommandBase.getPlayer(server, sender, args[0]);
            IPlayerData data = EntityUtil.getPlayerData(target);
            sender.addChatMessage(new TextComponentTranslation("command.get", args[0], data.getKarma()));
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return index == 0;
    }
}
