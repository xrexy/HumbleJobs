package me.xrexy.humblejobs.players;

import lombok.Getter;
import me.xrexy.humblejobs.HumbleJobs;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;

@Getter
public class PlayerManager implements Listener {
    private static final Map<String, HumblePlayer> activePlayers = new HashMap<>();
    private final HumbleJobs humbleJobs = HumbleJobs.getInstance();

    @EventHandler
    void join(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        activePlayers.put(player.getUniqueId().toString(), new HumblePlayer(player));
//        humbleJobs.versionManager.getActionbar().sendActionbar(player, "POGGERS MATE");
    }

    @EventHandler
    void quit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        activePlayers.remove(player.getUniqueId().toString());
    }

    public static void addPlayer(Player player) {
        String uuid = player.getUniqueId().toString();
        if (activePlayers.containsKey(uuid))
            return;

        activePlayers.put(uuid, new HumblePlayer(player));
    }

    public static HumblePlayer getPlayer(Player player) {
        return activePlayers.get(player.getUniqueId().toString());
    }
}
