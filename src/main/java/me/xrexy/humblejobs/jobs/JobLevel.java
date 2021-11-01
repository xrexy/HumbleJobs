package me.xrexy.humblejobs.jobs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;

@Getter
@AllArgsConstructor
@ToString
public class JobLevel {
    private final int xpNeeded;
    private final String display;
    private final String message;
    private final ArrayList<String> commands;
}
