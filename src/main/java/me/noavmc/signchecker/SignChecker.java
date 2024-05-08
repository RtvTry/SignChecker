package me.noavmc.signchecker;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;

public class SignChecker extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("SignChecker включен!");
    }

    @Override
    public void onDisable() {
        getLogger().info("SignChecker выключен!");
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        getLogger().info("Проверка установленной таблички...");
        Block block = event.getBlockPlaced();
        Material type = block.getType();
        if (type.name().endsWith("_SIGN")) {
            BlockState state = block.getState();
            if (state instanceof Sign) {
                Sign sign = (Sign) state;
                ItemStack item = event.getItemInHand();
                NBTItem nbtItem = new NBTItem(item);
                NBTCompound blockEntityTag = nbtItem.getCompound("BlockEntityTag");
                if (blockEntityTag != null) {
                    event.setCancelled(true);
                    event.getPlayer().getInventory().remove(item);
                    getLogger().info("Табличка с NBT тегом 'BlockEntityTag' была заблокирована и удалена из инвентаря игрока.");
                }
            }
        }
    }
}







