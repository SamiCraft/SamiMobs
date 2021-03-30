package com.samifying.mobs;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class SamiMobs extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Loading data");
        PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(new EventListener(), this);
        manager.registerEvents(new ZombieVillagerCure(this), this);
    }
}
