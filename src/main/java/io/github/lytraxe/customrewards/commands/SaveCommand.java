package io.github.lytraxe.customrewards.commands;

import io.github.lytraxe.customrewards.CustomRewards;
import io.github.lytraxe.customrewards.data.Reward;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class SaveCommand extends Subcommand{

    protected SaveCommand(CustomRewards plugin) {super(plugin, "save");}

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("This command can only be run by a player"));
            return;
        }
        if(!sender.hasPermission("customrewards.save")) {
            sender.sendMessage(Component.text("Not enough permission", NamedTextColor.RED));
            return;
        }
        if(args.length != 2) {
            sender.sendMessage(Component.text("Correct usage: /crewards save <name>", NamedTextColor.RED));
            return;
        }
        ItemStack item = player.getInventory().getItemInMainHand();
        if(item.getType() == Material.AIR) {
            player.sendMessage(Component.text("You need to hold an item in your main hand"));
            return;
        }

        Reward reward = new Reward(args[1], item);
        if(!plugin.saveReward(reward)) {
            sender.sendMessage(Component.text("Could not save reward", NamedTextColor.RED));
            return;
        }
        sender.sendMessage(Component.text("Successfully saved reward", NamedTextColor.GRAY));
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
