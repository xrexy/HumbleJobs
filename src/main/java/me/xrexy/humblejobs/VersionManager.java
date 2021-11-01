package me.xrexy.humblejobs;

import lombok.Getter;
import me.xrexy.humblejobs.utils.nms.actionbar.Actionbar;
import me.xrexy.humblejobs.utils.nms.actionbar.v1_12_2.Actionbar_v1_12_2;
import me.xrexy.humblejobs.utils.nms.actionbar.v1_8.Actionbar_v1_8;
import me.xrexy.humblejobs.utils.nms.bossbar.Bossbar;
import me.xrexy.humblejobs.utils.nms.bossbar.v1_12_2.Bossbar_v1_12_2;
import me.xrexy.humblejobs.utils.nms.bossbar.v1_8.Bossbar_v1_8;
import org.bukkit.Bukkit;

@Getter
class VersionManager {
    private Actionbar actionbar = null;
    private Bossbar bossbar = null;
    private boolean isSetup = false;

    public VersionManager(String version) {
        switch (version) {
            case "v1_8_R3":
                actionbar = new Actionbar_v1_8();
                bossbar = new Bossbar_v1_8();
                isSetup = true;
                break;
            case "v1_12_R1":
                actionbar = new Actionbar_v1_12_2();
                bossbar = new Bossbar_v1_12_2();
                isSetup = true;
                break;
        }

        if (!isSetup)
            Bukkit.getLogger().warning("Your version(" + version + ") isn't supported. Disabled features: ActionBar, Bossbar");
    }
}
