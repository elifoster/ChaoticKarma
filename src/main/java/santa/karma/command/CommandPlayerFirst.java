package santa.karma.command;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collections;
import java.util.List;

/**
 * A simple CommandBase class that takes a username for the first argument, and requires permission level 2 to use.
 */
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public abstract class CommandPlayerFirst extends CommandBase {
    @Override public abstract String getCommandName();
    @Override public abstract String getCommandUsage(ICommandSender sender);
    @Override public abstract void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException;

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
        if (args.length != 1) {
            return Collections.emptyList();
        }
        return getListOfStringsMatchingLastWord(args, server.getAllUsernames());
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return index == 0;
    }
}
