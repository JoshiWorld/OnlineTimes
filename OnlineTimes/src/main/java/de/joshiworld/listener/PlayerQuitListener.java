package de.joshiworld.listener;

import de.joshiworld.main.OT;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 *
 * @author JoshiWorld
 */
public class PlayerQuitListener implements Listener {
    
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        
        File file = new File("plugins/OnlineTimes/" + p.getName());
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        
        cfg.set("Time", Long.valueOf(OT.timer.get(p.getName())));
        
        try {
            cfg.save(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        if(Bukkit.getOnlinePlayers().size() < 1) {
            Bukkit.getScheduler().cancelTask(PlayerJoinListener.sched);
        }
    }
    
}
