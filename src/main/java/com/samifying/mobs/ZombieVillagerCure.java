package com.samifying.mobs;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class ZombieVillagerCure implements Listener {

    private final SamiMobs plugin;
    private final ArrayList<LivingEntity> list;

    public ZombieVillagerCure(SamiMobs plugin) {
        this.plugin = plugin;
        this.list = new ArrayList<>();
    }

    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        if (entity.getType() == EntityType.VILLAGER) {
            LivingEntity villager = (LivingEntity) entity;
            EntityType type = event.getDamager().getType();
            if (type == EntityType.ZOMBIE || type == EntityType.ZOMBIE_VILLAGER || type == EntityType.HUSK) {
                if (villager.getHealth() <= event.getDamage()) {
                    Location location = entity.getLocation();
                    World world = location.getWorld();
                    if (world != null) {
                        LivingEntity zombie = (LivingEntity) location.getWorld().spawnEntity(location, EntityType.ZOMBIE_VILLAGER);
                        list.add(zombie);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEntitySpawnEvent(EntitySpawnEvent event) {
        Entity entity = event.getEntity();
        if (entity.getType() == EntityType.ZOMBIE_VILLAGER) {
            LivingEntity zombie = (LivingEntity) entity;
            runLater(zombie);
        }
    }

    private void runLater(LivingEntity entity) {
        (new BukkitRunnable() {
            @Override
            public void run() {
                if (list.contains(entity)) {
                    list.remove(entity);
                } else {
                    entity.remove();
                }
                cancel();
            }
        }).runTaskLater(plugin, 1L);
    }
}
