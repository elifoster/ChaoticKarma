package santa.karma.command;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.MinecraftForge;
import santa.karma.ChaoticKarma;
import santa.karma.gameevents.KarmaUpdateEvent;
import santa.karma.player.IPlayerData;
import santa.karma.util.EntityUtil;

import javax.annotation.ParametersAreNonnullByDefault;

import static santa.karma.ChaoticKarma.MAX_KARMA;
import static santa.karma.ChaoticKarma.MIN_KARMA;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class KarmaSetCommand extends CommandPlayerFirst {
    @Override
    public String getCommandName() {
        return "karmaset";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "karmaset <player> <amount>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 2 || args.length > 2) {
            throw new WrongUsageException(this.getCommandUsage(sender));
        } else {
            int amount = Integer.parseInt(args[1]);
            TextComponentTranslation message;
            if (amount < ChaoticKarma.MIN_KARMA) {
                message = new TextComponentTranslation("command.set.fail.low", amount, MIN_KARMA);
            } else if (amount > ChaoticKarma.MAX_KARMA) {
                message = new TextComponentTranslation("command.set.fail.high", amount, MAX_KARMA);
            } else {
                EntityPlayer target = CommandBase.getPlayer(server, sender, args[0]);
                IPlayerData data = EntityUtil.getPlayerData(target);
                int old = data.getKarma();
                data.setKarma(amount);
                MinecraftForge.EVENT_BUS.post(new KarmaUpdateEvent(old, target, amount));
                message = new TextComponentTranslation("command.set.success", target.getDisplayNameString(), amount);
            }
            sender.addChatMessage(message);
        }
    }
}
