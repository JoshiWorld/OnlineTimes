package de.joshiworld.commands;

import de.joshiworld.main.OT;
import de.joshiworld.mysql.GetTime;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

/**
 *
 * @author JoshiWorld
 */
public class OnlineTimes_Command implements CommandExecutor {
    
    String times = "";
    int i = 0;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            System.out.println(OT.Prefix + " §cDu musst ein Spieler sein");
        } else {
            Player p = (Player) sender;
            
            if(cmd.getName().equalsIgnoreCase("onlinetimes")) {
                if(args.length == 0) {
                    if(!p.hasPermission("onlinetimes.help")) {
                        p.sendMessage(OT.Prefix + " §aInsgesamte Spielzeit§7: " + time(GetTime.getTime(p.getName())));
                        p.sendMessage(OT.Prefix + " §aAktuelle Spielzeit§7: " + time(OT.timer.get(p.getName())));
                    } else {
                        p.sendMessage(OT.Prefix + " §a/onlinetimes §8» §7Onlinetimes Befehle");
                    p.sendMessage(OT.Prefix + " §a/onlinetimes list §8» §7Listet alle Spieler auf");
                    p.sendMessage(OT.Prefix + " §a/onlinetimes reset §8» §7Resetet alle Onlinezeiten");
                    p.sendMessage(OT.Prefix + " §a/onlinetimes reload §8» §7Reloadet alle Onlinezeiten");
                    }
                    return true;
                } else if(args.length == 1) {
                    if(!p.hasPermission("onlinetimes.*")) {
                        return false;
                    } else {
                        if(args[0].equalsIgnoreCase("list")) {
                        File ordner = new File("plugins/OnlineTimes/");
                        
                        String[] file = ordner.list();
                        
                        if(file.length > 0) {
                            for(i = 0; i < file.length; i++) {
                            String jtjt = OT.Prefix + " " + file[i];
                            times = times + jtjt + "§7: §a" + time(GetTime.getTime(file[i])) + "\n";
                        }
                        
                        p.sendMessage(times);
                        
                        times = "";
                        i = 0;
                        } else {
                            p.sendMessage(OT.Prefix + " §cEs wurden bis jetzt keine Daten aufgezeichnet");
                        }
                    } else if(args[0].equalsIgnoreCase("reset")) {
                        File ordner = new File("plugins/OnlineTimes/");
                        
                        String[] file = ordner.list();
                        
                        for(int m = 0; m < file.length; m++) {
                            File ff = new File("plugins/OnlineTimes/" + file[m]);
                            ff.delete();
                            GetTime.deletePlayer(file[m]);
                        }
                        
                        p.sendMessage(OT.Prefix + " §aDu hast die Online-Zeiten resetet");
                    } else if(args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {
                        File file = new File("plugins/OnlineTimes/" + p.getName());
                        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
                        
                        for(Player ppp : Bukkit.getOnlinePlayers()) {
                            int a = OT.timer.get(p.getName());
                            int b = cfg.getInt("Time");
                            int c = a + b;
                            
                            int d = GetTime.getTime(p.getName());
                            int f = a + d;
        
                            cfg.set("Time", c);
                            GetTime.setTime(p.getName(), f);
                        }
        
                    try {
                        cfg.save(file);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    p.sendMessage(OT.Prefix + " §aDu hast die Liste reloadet");
                    } else {
                        p.sendMessage(OT.Prefix + " §cFalscher Syntax!");
                    }
                    return true;
                    }
                } else {
                    p.sendMessage(OT.Prefix + " §cFalscher Syntax!");
                    return true;
                }
            }
        }
        return false;
    }
    
    public static String time(long time) {
		
	long days = TimeUnit.SECONDS.toDays(time);
	long hours = TimeUnit.SECONDS.toHours(time) - (TimeUnit.MILLISECONDS.toDays(time) * 24);
	long min = TimeUnit.SECONDS.toMinutes(time) - (TimeUnit.MILLISECONDS.toHours(time) * 60);
	long sec = TimeUnit.SECONDS.toSeconds(time) - (TimeUnit.MILLISECONDS.toMinutes(time) * 60);
	
	if((int)days == 0 && (int)hours == 0 && (int)min == 0) {
            if(!(sec == 1)) {
		return "§7[§f" + sec + "§7] §3Sekunden";
            } return "§7[§f" + sec + "§7] §3Sekunde";
	}
	if((int)days == 0 && (int)hours == 0) {
            if(!(min == 1)) {
		return "§7[§f" + min + "§7] §3Minuten";
            } return "§7[§f" + min + "§7] §3Minute";
	}
	if((int)days == 0) {
            if(!(hours == 1)) {
		return "§7[§f" + hours + "§7] §3Stunden";
            } else {
		return "§7[§f" + hours + "§7] §3Stunde";
            }
	}
	
	return "§c" + days + " §cTagen";
    }
    
}
