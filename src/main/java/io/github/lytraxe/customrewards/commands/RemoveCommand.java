package io.github.lytraxe.customrewards.commands;

import io.github.lytraxe.customrewards.CustomRewards;
import io.github.lytraxe.customrewards.data.Reward;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.stream.Collectors;

public class RemoveCommand extends Subcommand {

    protected RemoveCommand(CustomRewards plugin) { super(plugin, "remove"); }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("customrewards.remove")) {
            sender.sendMessage(Component.text("You do not have enough permission to run this command", NamedTextColor.RED));
            return;
        }
        if (args.length != 2) {
            sender.sendMessage(Component.text("Correct usage: /crewards remove <reward name>"));
            return;
        }
        if(plugin.getReward(args[1]) == null) {
            sender.sendMessage(Component.text("Could not find reward with that name"));
            return;
        }

        plugin.removeReward(args[1]);
        sender.sendMessage(Component.text("Successfully removed reward " + args[1], NamedTextColor.GRAY));
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length == 2)
            return plugin.getRewards().stream().map(Reward::getName).filter(text->text.startsWith(args[1])).collect(Collectors.toList());
        return null;
    }
}
