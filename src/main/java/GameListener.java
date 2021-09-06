import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GameListener implements Listener {




    public GameListener(MagicCarpetMain plugin) {
        this.plugin = plugin;

    }

    public final Map<Player, ObjectifiedPlaceCarpet> hasCarpet = new HashMap<Player, ObjectifiedPlaceCarpet>();
    private final MagicCarpetMain plugin;



    @EventHandler
    private void onMovement(PlayerMoveEvent e) {
        if (CommandMc.getIsCarpeting(e.getPlayer()) != null) {
            if(!hasCarpet.containsKey(e.getPlayer())){
                ObjectifiedPlaceCarpet objectifiedPlaceCarpet = new ObjectifiedPlaceCarpet();
                hasCarpet.put(e.getPlayer(), objectifiedPlaceCarpet);
            }
            hasCarpet.get(e.getPlayer()).placeBlocks(e.getPlayer(),e.getTo());
            hasCarpet.get(e.getPlayer()).removeBlocks(e.getPlayer(),e.getTo());

        }
    }

    @EventHandler
    private void onBreakBlock(BlockBreakEvent e) {
        for (Entity entity: e.getPlayer().getNearbyEntities(5.0,5.0,5.0)){
            if(entity instanceof Player && hasCarpet.containsKey(entity)){

                            if (hasCarpet.get(entity).getLocationPlaced(e.getBlock().getLocation())) {
                                e.setCancelled(true);
                }

            }
        }
        if (hasCarpet.containsKey(e.getPlayer())) {


                        if (hasCarpet.get(e.getPlayer()).getLocationPlaced(e.getBlock().getLocation())) {
                            e.setCancelled(true);

                        }
            Location loc = e.getPlayer().getLocation();
            e.getPlayer().setGravity(false);
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    e.getPlayer().setGravity(true);
                    hasCarpet.get(e.getPlayer()).placeBlocks(e.getPlayer(),e.getPlayer().getLocation());
                }
            }, 1L);
        }

    }
    @EventHandler
    private  void  onBlockUpdate(BlockPhysicsEvent e) {

        for (Entity entity : e.getSourceBlock().getWorld().getNearbyEntities(e.getBlock().getLocation(), 5, 5, 5)) {
            if (entity instanceof Player && hasCarpet.containsKey(entity)) {
                for (int y = -1; y <= 1; y++) {
                    for (int x = -1; x <= 1; x++) {
                        for (int z = -1; z <= 1; z++) {
                            if (hasCarpet.get(entity).getLocationPlaced(e.getBlock().getLocation().add(x, y, z))) {
                                e.setCancelled(true);
                            }
                        }
                    }
                }
            }

        }
    }

        @EventHandler
    private void onPlaceBlock(BlockPlaceEvent e) {
        for (Entity entity: e.getPlayer().getNearbyEntities(5.0,5.0,5.0)){
            if(entity instanceof Player && hasCarpet.containsKey(entity)){
                            if (hasCarpet.get(entity).getLocationPlaced(e.getBlock().getLocation())) {
                                e.setCancelled(true);
                            }

            }
        }
        if (hasCarpet.containsKey(e.getPlayer())) {

                        if (hasCarpet.get(e.getPlayer()).getLocationPlaced(e.getBlock().getLocation())) {
                            e.setCancelled(true);
            }
        }

    }

    @EventHandler
    private void onGamemodeChange(PlayerGameModeChangeEvent e) {
        if (CommandMc.getIsCarpeting(e.getPlayer()) && e.getNewGameMode() == GameMode.CREATIVE) {
            CommandMc.delIsCarpeting(e.getPlayer());
            hasCarpet.get(e.getPlayer()).stopCarpeting(e.getPlayer());
        }
    }

    @EventHandler
    private void onTeleport(PlayerTeleportEvent e) {
        Player player = e.getPlayer();
        if (CommandMc.getIsCarpeting(player)) {
            Location from = e.getFrom();
            hasCarpet.get(e.getPlayer()).removeBlocksEvent(player, from);
        }

    }

    @EventHandler
    private void onDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        if (CommandMc.getIsCarpeting(player)) {
            Location here = player.getLocation();
            hasCarpet.get(player).removeBlocksEvent(player, here);
        }
    }

    @EventHandler
    private void onDisconnect(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        if (CommandMc.getIsCarpeting(player)) {
            hasCarpet.get(player).stopCarpeting(player);
        }

    }
}

