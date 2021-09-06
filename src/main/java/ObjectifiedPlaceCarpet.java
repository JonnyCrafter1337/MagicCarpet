import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class ObjectifiedPlaceCarpet {

World world;
ArrayList<Location> locationPlaced = new ArrayList<>();

    public void placeBlocks(Player p, Location to) {
        int coordX = to.getBlockX();
        int coordZ = to.getBlockZ();
        int coordY = to.getBlockY();
        World world = to.getWorld();

        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                Location here = new Location(world, coordX + x, coordY, coordZ + z);
                if (here.getBlock().getType() == Material.AIR) {
                    here.getBlock().setType(Material.CYAN_CARPET, false);
                    locationPlaced.add(here);
                }



            }
        }

    }

    public void removeBlocks(Player player, Location from) {

        int coordX = from.getBlockX();
        int coordZ = from.getBlockZ();
        int coordY = from.getBlockY();
        World world = from.getWorld();
        for (int y = -1; y <= 1; y++) {
            for (int x = -2; x <= 2; x++) {

                for (int z = -2; z <= 2; z++) {
                    if (x == -2 || x == 2 || y == -1 || player.isSneaking() || y == 1){
                        player.setFallDistance(-10);
                        Location here = new Location(world, coordX + x, coordY + y, coordZ + z);
                        if (locationPlaced.contains(here) && here.getBlock().getType() == Material.CYAN_CARPET) {
                            here.getBlock().setType(Material.AIR, false);
                            locationPlaced.remove(here);

                        }


                    } else if (abs(z) <= 1) {
                        continue;
                    } else {


                        Location here = new Location(world, coordX + x, coordY +y, coordZ + z);
                        if (locationPlaced.contains(here) && here.getBlock().getType() == Material.CYAN_CARPET) {
                            here.getBlock().setType(Material.AIR, false);
                            locationPlaced.remove(here);

                        }
                    }
                }
            }
        }
    }

    public void stopCarpeting(Player sender) {
        CommandMc.delIsCarpeting(sender);
        Location here = sender.getLocation();
        removeBlocksEvent(sender, here);
    }

    public void removeBlocksEvent(Player player, Location l){

        World world = l.getWorld();
        int coordX = l.getBlockX();
        int coordZ = l.getBlockZ();
        int coordY = l.getBlockY();

        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                for (int y = -2; y <= 2; y++) {
                    Location there = new Location(world, coordX + x, coordY +y, coordZ + z);

                    if (locationPlaced.contains(there) && there.getBlock().getType() == Material.CYAN_CARPET) {
                        there.getBlock().setType(Material.AIR, false);
                        locationPlaced.remove(there);


                    }
                }
            }
        }


    }

    public void debug(Player sender) {
        Bukkit.getLogger().info("Size placedHere:" + locationPlaced.size());
        sender.sendMessage("Size placedHere: " + locationPlaced.size());
        if (CommandMc.isCarpetingSize() > locationPlaced.size() * 9 ){
            sender.sendMessage("Memory is leaking... But where?");

        }
    }

    public boolean getLocationPlaced(Location location) {
        return locationPlaced.contains(location);
    }
}


