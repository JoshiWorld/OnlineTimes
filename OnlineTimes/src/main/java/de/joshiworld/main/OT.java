package de.joshiworld.main;

import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author JoshiWorld
 */
public class OT extends JavaPlugin {
    
    private static OT instance;
    
    @Override
    public void onEnable() {
        instance = this;
    }
    
    @Override
    public void onDisable() {
        
    }
    
    public static OT getInstance() {
        return instance;
    }
    
}
