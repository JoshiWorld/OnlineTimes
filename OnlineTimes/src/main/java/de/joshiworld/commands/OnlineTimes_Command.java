package de.joshiworld.commands;

import de.joshiworld.main.OT;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
                    p.sendMessage(OT.Prefix + " §a/onlinetimes §8» §7Onlinetimes Befehle");
                    p.sendMessage(OT.Prefix + " §a/onlinetimes list §8» §7Listet alle Spieler auf");
                    p.sendMessage(OT.Prefix + " §a/onlinetimes reset §8» §7Resetet alle Onlinezeiten");
                    return true;
                } else if(args.length == 1) {
                    if(args[0].equalsIgnoreCase("list")) {
                        File ordner = new File("plugins/OnlineTimes/");
                        
                        String[] file = ordner.list();
                        
                        for(i = 0; i < file.length; i++) {
                            File ff = new File("plugins/OnlineTimes/" + file[i]);
                            FileConfiguration cfg = YamlConfiguration.loadConfiguration(ff);
                            String jtjt = file[i].substring(4);
                            times = times + jtjt + "§7: §a" + time(cfg.getLong("Time")) + "\n";
                        }
                        
                        p.sendMessage(OT.Prefix + " " + times);
                        
                        times = "";
                        i = 0;
                    } else if(args[0].equalsIgnoreCase("reset")) {
                        File ordner = new File("plugins/OnlineTimes/");
                        
                        String[] file = ordner.list();
                        
                        for(int t = 0; t < file.length; t++) {
                            File ff = new File("plugins/OnlineTimes/" + file[i]);
                            ff.delete();
                        }
                        
                        p.sendMessage(OT.Prefix + " §aDu hast die Online-Zeiten resetet");
                    } else {
                        p.sendMessage(OT.Prefix + " §cFalscher Syntax!");
                    }
                    return true;
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
