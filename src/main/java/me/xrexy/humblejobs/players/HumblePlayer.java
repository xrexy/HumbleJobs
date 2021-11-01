package me.xrexy.humblejobs.players;

import de.leonhard.storage.Json;
import de.leonhard.storage.LightningBuilder;
import de.leonhard.storage.internal.FileData;
import de.leonhard.storage.internal.settings.DataType;
import de.leonhard.storage.internal.settings.ReloadSettings;
import lombok.Getter;
import lombok.ToString;
import me.xrexy.humblejobs.jobs.JobLevel;
import me.xrexy.humblejobs.jobs.Jobs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

@Getter
@ToString
public class HumblePlayer {
    private final String uuid;
    private String job;
    private int level;
    private double xp;
    private final Json json;
    private final FileData fileData;

    public HumblePlayer(Player player) {
        String uuid = player.getUniqueId().toString();
        this.json = LightningBuilder.fromPath(uuid, "plugins/HumbleJobs/playerdata/")
                .setDataType(DataType.UNSORTED)
                .setReloadSettings(ReloadSettings.INTELLIGENT)
                .createJson();
        this.fileData = json.getFileData();

//        Bukkit.getLogger().warning(Bukkit.getServer().getClass().getPackage().getName());

        // loading information from json
        this.uuid = json.getOrSetDefault("uuid", uuid);
        this.job = json.getOrSetDefault("job", "");
        this.xp = json.getOrSetDefault("xp", 0);
        this.level = json.getOrSetDefault("level", 1);
    }

    public boolean isInJob(String job) {
        return this.job.equalsIgnoreCase(job);
    }

    public void assignJob(String jobTitle) {
        this.job = jobTitle;
        json.set("job", job);
    }

    private final HashMap<Integer, JobLevel> levels = Jobs.getLevels();

    public JobLevel getPlayerJobLevel() {
        int level = getLevel();
        if (level > levels.size())
            return levels.get(levels.size());

        return levels.get(level);
    }

    public JobLevel getNextPlayerJobLevel() {
        int level = getLevel();
        if (level >= levels.size())
            return levels.get(levels.size());

        return levels.get(level + 1);
    }

    public double getXp() {
        return json.getDouble("xp");
    }

    public int getLevel() {
        return json.getInt("level");
    }

    public String getJob() {
        return json.getString("job");
    }

    public void addXp(double toAdd) {
        xp = getXp() + toAdd;
        json.set("xp", xp);
    }

    public void reset() {
        this.job = "";
        this.level = 1;
        this.xp = 0;

        json.removeAll("job", "level", "xp");
    }

    public boolean rankUp() {
        Bukkit.getLogger().warning(level + "");
        int nextJobNeeded = getNextPlayerJobLevel().getXpNeeded();

        Bukkit.getLogger().warning(nextJobNeeded + "");

        if (level >= levels.size() || xp < nextJobNeeded)
            return false;

        level += 1;
        json.set("level", level);

        if (xp > nextJobNeeded)
            rankUp();

        return true;
    }
}
