package de.joshiworld.mysql;

import de.joshiworld.main.OT;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author JoshiWorld
 */
public class GetTime {
    
    public static boolean playerExists(String name) {
	    try {
	      ResultSet rs = OT.mysql.query("SELECT * FROM  `TIMES` WHERE  `PLAYER` = '" + name + "'");
	      return (rs.next()) && (rs.getString("PLAYER") != null);
	    } catch (SQLException e) {
	      e.printStackTrace();
	    }
	    return false;
	  }
    
    public static void createPlayer(String name) {
        if (!playerExists(name)) {
            OT.mysql.update("INSERT INTO `TIMES`(`PLAYER`, `TIME`) VALUES ('" + name + "', '0');");
        }
    }
    
    public static void deletePlayer(String name) {
        if(playerExists(name)) {
            OT.mysql.update("DELETE FROM `TIMES` WHERE `PLAYER` = '" + name + "'");
        }
    }
    
    public static Integer getTime(String name) {
        Integer i = 0;
        
        if(playerExists(name)) {
            try {
                ResultSet rs = OT.mysql.query("SELECT * FROM `TIMES` WHERE `PLAYER` = '" + name + "'");
                    if((!rs.next()) || (Integer.valueOf(rs.getInt("TIME")) == null));
				  
                    i = rs.getInt("TIME");
            } catch (SQLException e) {
		e.printStackTrace();
            }
        } else {
            createPlayer(name);
            getTime(name);
        }
		  
        return i;
    }
    
    public static void setTime(String name, Integer time) {
        if(playerExists(name)) {
            OT.mysql.update("UPDATE `TIMES` SET `TIME`= '" + time + "' WHERE `PLAYER`= '" + name + "';");
        } else {
            createPlayer(name);
            setTime(name, time);
        }
    }
    
    public static void addTime(String name, Integer time) {
	if(playerExists(name)) {
            setTime(name, Integer.valueOf(getTime(name).intValue() + time.intValue()));
        } else {
            createPlayer(name);
            addTime(name, time);
        }
    }
    
    public static void removeTime(String name, Integer time) {
	if(playerExists(name)) {
            setTime(name, Integer.valueOf(getTime(name).intValue() - time.intValue()));
	} else {
            createPlayer(name);
            removeTime(name, time);
        }
    }
    
}
