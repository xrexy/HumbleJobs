package me.xrexy.humblejobs.commands;

import me.xrexy.humblejobs.commands.utils.CommandInterface;
import me.xrexy.humblejobs.players.HumblePlayer;
import me.xrexy.humblejobs.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArgReset implements CommandInterface {
    @Override
    public String getCommand() {
        return "reset";
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

                new HumblePlayer(target).reset();
                Bukkit.broadcastMessage(new HumblePlayer(target).getXp() + "");
                Utils.sendMessage(player, "messages.reset");
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
