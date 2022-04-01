package io.github.lytraxe.customrewards.commands;

import io.github.lytraxe.customrewards.CustomRewards;

import io.github.lytraxe.customrewards.data.Reward;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomRewardsCommand implements TabExecutor {

    private final ArrayList<Subcommand> subcommands;
    private final CustomRewards plugin;

    public CustomRewardsCommand(CustomRewards plugin) {
        this.plugin = plugin;
        subcommands = new ArrayList<>();
        subcommands.add(new GiveCommand(plugin));
        subcommands.add(new SaveCommand(plugin));
        subcommands.add(new ReloadCommand(plugin));
        subcommands.add(new RemoveCommand(plugin));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Subcommand subcommand = getSubcommand(args.length > 0 ? args[0] : "a");
        if (subcommand != null) {
            subcommand.execute(sender, args);
            return true;
        }
        sender.sendMessage(Component.text(" ---" + plugin.getDescription().getName() + " v" + plugin.getDescription().getVersion() + "---", NamedTextColor.DARK_AQUA));
        sender.sendMessage(Component.text("Loaded " + plugin.getRewards().size() + " rewards: ", NamedTextColor.GRAY)
                .append(Component.text(plugin.getRewards().stream().map(Reward::getName).collect(Collectors.joining(", ")), NamedTextColor.WHITE)));
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias,
                                                @NotNull String[] args) {
        if (args.length == 1)
            return subcommands.stream().map(Subcommand::getName).filter(text -> text.startsWith(args[0])).collect(Collectors.toList());
        else if (args.length > 1) {
            Subcommand subcommand = getSubcommand(args[0]);
            if (subcommand != null)
                return subcommand.tabComplete(sender, args);
        }
        return null;
    }

    public Subcommand getSubcommand(String text) {
        for (Subcommand subcommand: subcommands) {
            if (subcommand.getName().equalsIgnoreCase(text))
                return subcommand;
        }
        return null;
    }
}
