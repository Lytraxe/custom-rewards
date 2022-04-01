package io.github.lytraxe.customrewards.commands;

import org.bukkit.command.CommandSender;
import io.github.lytraxe.customrewards.CustomRewards;

import java.util.List;

public abstract class Subcommand {

    protected final CustomRewards plugin;
    protected final String name;

    protected Subcommand(CustomRewards plugin, String name) {
        this.plugin = plugin;
        this.name = name;
    }

    public abstract void execute(CommandSender sender, String[] args);

    public abstract List<String> tabComplete(CommandSender sender, String[] args);

    public String getName() {
        return name;
    }
}
