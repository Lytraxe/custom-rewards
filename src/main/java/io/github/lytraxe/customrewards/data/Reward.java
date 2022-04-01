package io.github.lytraxe.customrewards.data;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Reward {

    private String name;
    private ItemStack item;

    public Reward(@NotNull String name, @NotNull ItemStack item) {
        this.name = name;
        this.item = item;
    }

    public String getName() { return name; }
    public ItemStack getItem() { return item; }

    public void setName(String name) { this.name = name; }
    public void setItem(ItemStack item) { this.item = item; }
}
