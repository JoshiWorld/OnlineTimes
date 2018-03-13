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
import org.bukkit.event.player.PlayerJoinEvent;

/**
 *
 * @author JoshiWorld
 */
public class PlayerJoinListener implements Listener {
    
    public static int sched;
    
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        
        File file = new File("plugins/OnlineTimes/" + p.getName());
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            cfg.set("Time", 0);
            
            try {
                cfg.save(file);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        if(Bukkit.getOnlinePlayers().size() > 0) {
            sched = Bukkit.getScheduler().scheduleAsyncRepeatingTask(OT.getInstance(), new Runnable() {
            @Override
            public void run() {
                for(Player player : Bukkit.getOnlinePlayers()) {
                    long l = OT.timer.get(player.getName());
                    
                    OT.timer.put(player.getName(), l++);
                }
            }
        }, 0, 20*1);
        }
        
    }
    
}
