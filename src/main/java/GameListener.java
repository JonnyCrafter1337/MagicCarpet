import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;

public class GameListener implements Listener {

    public GameListener(MagicCarpetMain plugin) {

    }

    @EventHandler
    private void onMovement(PlayerMoveEvent e) {
        if (CommandMc.getIsCarpeting(e.getPlayer()) != null) {
            PlaceCarpet.placeBlocks(e.getPlayer(), e.getTo());
            PlaceCarpet.removeBlocks(e.getPlayer(), e.getTo());
        }
    }

    @EventHandler
    private void onBreakBlock(BlockBreakEvent e) {
        if (PlaceCarpet.getLocationPlaced(e.getBlock().getLocation()) != null) {
            e.setCancelled(true);
        }

    }

    @EventHandler
    private void onGamemodeChange(PlayerGameModeChangeEvent e) {
        if (CommandMc.getIsCarpeting(e.getPlayer()) && e.getNewGameMode() == GameMode.CREATIVE) {
            CommandMc.delIsCarpeting(e.getPlayer());
            PlaceCarpet.stopCarpeting(e.getPlayer());
        }
    }

    @EventHandler
    private void onTeleport(PlayerTeleportEvent e) {
        Player player = e.getPlayer();
        if (CommandMc.getIsCarpeting(player)) {
            Location from = e.getFrom();
            PlaceCarpet.removeBlocksEvent(player, from);
        }

    }

    @EventHandler
    private void onDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        if (CommandMc.getIsCarpeting(player)) {
            Location here = player.getLocation();
            PlaceCarpet.removeBlocksEvent(player, here);
        }
    }

    @EventHandler
    private void onDisconnect(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        if (CommandMc.getIsCarpeting(player)) {
            PlaceCarpet.stopCarpeting(player);
            //Database needed to store logged out players so they dont fall after a reboot
        }

    }
   /* @EventHandler
    private void onFalldmg(EntityDamageEvent.DamageCause){
    }*/


    @EventHandler
    private void onStartMining(BlockDamageEvent e) {
        if (PlaceCarpet.getLocationPlaced(e.getBlock().getLocation()) != null) {
            e.setCancelled(true);
        }
    }
}