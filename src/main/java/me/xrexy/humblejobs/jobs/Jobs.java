package me.xrexy.humblejobs.jobs;

import lombok.Getter;
import lombok.ToString;
import me.xrexy.humblejobs.HumbleJobs;
import me.xrexy.humblejobs.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

@Getter
@ToString
public class Jobs {
    @Getter
    private static final HashMap<Integer, JobLevel> levels = new HashMap<>();
    private final HashMap<String, Jobs> jobs = new HashMap<>();
    public static final ArrayList<String> jobNames = new ArrayList<>();
    private String displayName;
    private final ArrayList<String> validBlocks = new ArrayList<>();
    private final ArrayList<String> availableJobs = new ArrayList<>();
    private final HumbleJobs humbleJobs = HumbleJobs.getInstance();
    private final ConfigurationSection section;

    public Jobs(ConfigurationSection section) {
        this.section = section;
        for (String key : section.getKeys(false)) {
            if (jobNames.contains(key)) {
                Bukkit.getLogger().log(Level.WARNING, "There's already a job with the name: " + key);
                continue;
            }

            try {
                jobNames.add(key);
                this.displayName = Utils.colorize(section.getString(key + ".display-name"));
                ConfigurationSection validSection = section.getConfigurationSection(key + ".valid-blocks");

                if (validSection != null)
                    validBlocks.addAll(validSection.getKeys(false));

                ConfigurationSection levelsSection = section.getConfigurationSection(key + ".levels");
                if (levelsSection != null) {
                    for (String posKey : levelsSection.getKeys(false)) {
                        levels.put(Integer.parseInt(posKey), new JobLevel(
                                levelsSection.getInt(posKey + ".needed_xp"),
                                levelsSection.getString(posKey + ".display"),
                                levelsSection.getString(posKey + ".message"),
                                new ArrayList<>(levelsSection.getStringList(posKey + ".commands"))
                        ));
                    }
                }

                jobs.put(key, this);
            } catch (Exception exc) {
                if (HumbleJobs.getInstance().getConfig().getBoolean("debug"))
                    exc.printStackTrace();
                Bukkit.getLogger().log(Level.WARNING, "Couldn't parse information for job " + key);
            }
        }
    }


    public static ArrayList<JobMaterial> getJobMaterials(JobEventTypes type, String job, Material material) {
        ConfigurationSection section = type.getConfigurationSection().getConfigurationSection(job + ".valid-blocks." + material.toString().toUpperCase());
        if (section == null)
            return null;

        ArrayList<JobMaterial> jobMaterials = new ArrayList<>();
        for (String key : section.getKeys(false))
            jobMaterials.add(new JobMaterial(
                    material,
                    Integer.parseInt(key),
                    section.getDouble(key + ".money"),
                    section.getDouble(key + ".xp")
            ));

        return jobMaterials;
    }
}
