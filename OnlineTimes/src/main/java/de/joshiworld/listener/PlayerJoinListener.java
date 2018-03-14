package de.joshiworld.listener;

import de.joshiworld.main.OT;
import de.joshiworld.mysql.GetTime;
import java.io.File;
import java.io.IOException;
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
            GetTime.createPlayer(p.getName());
            
            try {
                cfg.save(file);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        scheduler();
        
    }
    
    private void scheduler() {
        if(Bukkit.getOnlinePlayers().size() <= 1) {
            if(!Bukkit.getScheduler().isCurrentlyRunning(sched)) {
            sched = Bukkit.getScheduler().scheduleSyncRepeatingTask(OT.getInstance(), new Runnable() {
                @Override
                public void run() {
                    for(Player player : Bukkit.getOnlinePlayers()) {
                        if(OT.timer.get(player.getName()) != null) {
                            int m = OT.timer.get(player.getName());
                            m++;
                            OT.timer.put(player.getName(), m);
                        } else {
                            OT.timer.put(player.getName(), 0);
                            int m = OT.timer.get(player.getName());
                            m++;
                            OT.timer.put(player.getName(), m);
                        }
                    }
                }
            }, 0, 20);
        }
        }
    }
    
}
