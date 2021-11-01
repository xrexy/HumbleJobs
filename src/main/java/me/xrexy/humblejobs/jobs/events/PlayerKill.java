package me.xrexy.humblejobs.jobs.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerKill implements Listener {
    @EventHandler
    void playerKill(PlayerDeathEvent event) {
        Player p = event.getEntity();
        if (p.getKiller() != null) {
            Player killer = p.getKiller();

        }
    }

}
