package me.xrexy.humblejobs.commands;

import me.xrexy.humblejobs.commands.utils.CommandInterface;
import me.xrexy.humblejobs.players.HumblePlayer;
import me.xrexy.humblejobs.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArgCheck implements CommandInterface {
    @Override
    public String getCommand() {
        return "check";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Player player = (Player) sender;

        if (args.length == 1) {
            Utils.sendMessage(player, "messages.missing-input-player");
            return true;
        }
        if (args.length > 1) {
            try {
                Player target = Bukkit.getPlayerExact(args[1]);

                if (target == null)
                    throw new IllegalArgumentException("Couldn't find a player with that name");

                if (new HumblePlayer(target).getJob().isEmpty()) // players doesn't have a job
                    Utils.sendMessage(player, "messages.player-no-job");
                else
                    Utils.sendMultilineMessage(player, target, "messages.check");
            } catch (IllegalArgumentException illegalArgumentException) {
                Utils.debug(illegalArgumentException);
                Utils.sendMessage(player, "messages.invalid-player");
            } catch (Exception exception) {
                Utils.debug(exception);
                Utils.sendMessage(player, "messages.invalid-player");
                Bukkit.getLogger().warning(Utils.process("%prefix% &cSomething went wrong while parsing information for player " + args[1]));
            }
            return true;
        }
        return false;
    }
}
