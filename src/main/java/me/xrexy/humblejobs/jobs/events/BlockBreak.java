package me.xrexy.humblejobs.jobs.events;

import me.xrexy.humblejobs.jobs.JobEventTypes;
import me.xrexy.humblejobs.jobs.JobMaterial;
import me.xrexy.humblejobs.jobs.Jobs;
import me.xrexy.humblejobs.jobs.JobLevel;
import me.xrexy.humblejobs.players.HumblePlayer;
import me.xrexy.humblejobs.players.PlayerManager;
import me.xrexy.humblejobs.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;

public class BlockBreak implements Listener {
    @EventHandler
    void blockBreak(BlockBreakEvent event) {
        final Player player = event.getPlayer();
        final HumblePlayer humblePlayer = PlayerManager.getPlayer(player);

        humblePlayer.assignJob("Miner");

        final JobEventTypes type = JobEventTypes.BLOCK_BREAK;

        event.setCancelled(true);

        for (String jobTitle : type.getJobTitles())
            if (humblePlayer.isInJob(jobTitle)) {
                final Block block = event.getBlock();
                final Material blockMaterial = block.getType();
                final ArrayList<String> validBlocks = type.getValidBlocks();

                if (validBlocks.contains(blockMaterial.toString())) {
                    ArrayList<JobMaterial> jobMaterials = Jobs.getJobMaterials(type, jobTitle, blockMaterial);
                    if (jobMaterials != null && !jobMaterials.isEmpty())
                        for (JobMaterial material : jobMaterials) {
                            if (material.getData() == block.getData() && material.getMaterial() == blockMaterial) {
                                Utils.pay(player, material.getMoney());

                                humblePlayer.addXp(material.getXp());
                                JobLevel nextPlayerJobLevel = humblePlayer.getNextPlayerJobLevel();
                                if (humblePlayer.rankUp())
                                    Utils.sendMessage(player, "messages.rank-up");
                            }
                        }
                }
            }
        event.getBlock().setType(Material.AIR);
    }
}
