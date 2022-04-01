package io.github.lytraxe.customrewards;

import io.github.lytraxe.customrewards.commands.CustomRewardsCommand;
import io.github.lytraxe.customrewards.data.Reward;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

public class CustomRewards extends JavaPlugin {

    List<Reward> rewards = new ArrayList<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadRewards();
        Objects.requireNonNull(getCommand("customrewards")).setExecutor(new CustomRewardsCommand(this));
    }

    @Override
    public void onDisable() {}

    public Reward getReward(String name) {
        for(Reward reward : rewards) {
            if(reward.getName().equalsIgnoreCase(name))
                return reward;
        }
        return null;
    }

    public List<Reward> getRewards() { return rewards; }

    public void loadRewards() {
        rewards.clear();
        reloadConfig();
        ConfigurationSection rewardConfig = getConfig().getConfigurationSection("rewards");
        if(rewardConfig == null) {
            getLogger().log(Level.WARNING, "Could not load rewards.");
            return;
        }
        for(String rewardName : rewardConfig.getKeys(false)) {
            Reward reward = new Reward(Objects.requireNonNull(rewardConfig.getString(rewardName + ".name")),
                    Objects.requireNonNull(rewardConfig.getItemStack(rewardName + ".item")));
            rewards.add(reward);
        }
        getLogger().log(Level.INFO, "Loaded " + rewards.size() + " rewards");
    }

    public boolean saveReward(Reward reward) {
        ConfigurationSection rewardConfig = getConfig().getConfigurationSection("rewards");
        if(rewardConfig == null) {
            rewardConfig = getConfig().createSection("rewards");
        }
        ConfigurationSection rConfig = rewardConfig.createSection(reward.getName());
        rConfig.set("name", reward.getName());
        rConfig.set("item", reward.getItem());

	    saveConfig();
        loadRewards();
        return true;
    }

    public void removeReward(String name) {
        ConfigurationSection rewardConfig = getConfig().getConfigurationSection("rewards");
        if(rewardConfig == null) {
            return;
        }

        rewardConfig.set(name, null);
        saveConfig();
        loadRewards();
    }
}