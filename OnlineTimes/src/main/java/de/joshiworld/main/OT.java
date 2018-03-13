package de.joshiworld.main;

import de.joshiworld.commands.OnlineTimes_Command;
import de.joshiworld.listener.PlayerJoinListener;
import de.joshiworld.listener.PlayerQuitListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author JoshiWorld
 */
public class OT extends JavaPlugin {
    private static OT instance;
    
    public static String Prefix = "§7[§aOT§7]";
    
    public static Map<String, Long> timer = new HashMap<String, Long>();
    
    @Override
    public void onEnable() {
        instance = this;
        
        //create OnlineTimes folder
        File ordner = new File("plugins/OnlineTimes/");
        if(!ordner.exists()) {
            ordner.mkdir();
        }
        
        //PluginManager
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new PlayerQuitListener(), this);
        
        //Commands
        this.getCommand("onlinetimes").setExecutor(new OnlineTimes_Command());
        
    }
    
    @Override
    public void onDisable() {
        
    }
    
    public static OT getInstance() {
        return instance;
    }
    
}
