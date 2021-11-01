package me.xrexy.humblejobs.jobs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.bukkit.Material;

@Getter
@AllArgsConstructor
@ToString
public class JobMaterial {
    private final Material material;
    private final int data;
    private final double money;
    private final double xp;
}