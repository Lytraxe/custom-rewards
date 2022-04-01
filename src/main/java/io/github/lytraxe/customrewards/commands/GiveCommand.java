package io.github.lytraxe.customrewards.commands;

import io.github.lytraxe.customrewards.data.Reward;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import io.github.lytraxe.customrewards.CustomRewards;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class GiveCommand extends Subcommand {

    private final CustomRewards plugin;

    protected GiveCommand(CustomRewards plugin) {
        super(plugin, "give");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length != 3) {
            sender.sendMessage(Component.text("Correct usage: /crewards give <playername> <reward>", NamedTextColor.RED));
            return;
        }

        Player player = plugin.getServer().getPlayer(args[1]);
        if(player == null) {
            sender.sendMessage(Component.text("Could not find player with name " + args[1], NamedTextColor.RED));
            return;
        }
        Reward reward = plugin.getReward(args[2]);
        if(reward == null) {
            sender.sendMessage(Component.text("Reward does not exist"));
            return;
        }

        if(player.getInventory().addItem(reward.getItem()).size() != 0) {
            player.sendMessage(Component.text("No empty slots found for reward.", NamedTextColor.RED));
            plugin.getLogger().log(Level.WARNING,"Could not reward player: " + player.getName() + ", reward: " + reward.getName());
            return;
        }
        player.sendMessage(Component.text("Added reward to your inventory", NamedTextColor.AQUA));
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length == 2)
            return plugin.getServer().getOnlinePlayers().stream().map(Player::getName).filter(text->text.startsWith(args[1])).collect(Collectors.toList());
        else if (args.length == 3)
            return plugin.getRewards().stream().map(Reward::getName).filter(text->text.startsWith(args[2])).collect(Collectors.toList());
        return null;
    }
}
