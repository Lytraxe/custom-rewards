package io.github.lytraxe.customrewards.commands;

import io.github.lytraxe.customrewards.CustomRewards;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ReloadCommand extends Subcommand{

    protected ReloadCommand(CustomRewards plugin) { super(plugin, "reload"); }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("customrewards.reload")) {
            sender.sendMessage(Component.text("You do not have enough permission to run this command", NamedTextColor.RED));
            return;
        }

        plugin.loadRewards();
        sender.sendMessage(Component.text("Reloaded rewards", NamedTextColor.GRAY));
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) { return null; }
}
