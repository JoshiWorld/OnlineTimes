package de.joshiworld.mysql;

import com.mysql.jdbc.Statement;
import de.joshiworld.main.OT;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.bukkit.Bukkit;

/**
 *
 * @author JoshiWorld
 */
public class MySQL {
    
    private String Host;
	  private String Database;
	  private String Username;
	  private String Password;
	  static Connection con;
	  
	  public MySQL(String host, String database, String user, String password) {
	    this.Host = "localhost";
	    this.Database = "armyofmc";
	    this.Username = "armyofmc";
	    this.Password = "123armyofmc123";
	    this.Host = host;
	    this.Database = database;
	    this.Username = user;
	    this.Password = password;
	    connect();
	  }
	  
	  public void connect() {
	    try {
	      con = (Connection) DriverManager.getConnection("jdbc:mysql://" + this.Host + ":3306/" + this.Database + "?autoReconnect=true", this.Username, this.Password);
	      Bukkit.getConsoleSender().sendMessage(OT.Prefix + " §aThe connection to the MySQL was established.");
	    }
	    catch (SQLException e) {
	      Bukkit.getConsoleSender().sendMessage(OT.Prefix + " §cThe connection to MySQL failed, Error: §7" + e.getMessage());
	    }
	  }
	  
	  public void close() {
	    try {
	      if (con != null) {
	        con.close();
	        Bukkit.getConsoleSender().sendMessage(OT.Prefix + " §aThe connection to the MySQL was terminated successfully.");
	      }
	    }
	    catch (SQLException e) {
	      Bukkit.getConsoleSender().sendMessage(OT.Prefix + " §cA disconnection error to MySQL failed, Error: §7" + e.getMessage());
	    }
	  }
	  
	  public void update(String sql) {
	    try {
	      con.createStatement().executeUpdate(sql);
	    }
	    catch (SQLException e) {
	      Bukkit.getConsoleSender().sendMessage(OT.Prefix + " §cCould not run the update (§7" + sql + "§c)!");
	    }
	  }
	  
	  public ResultSet query(String qry) {
	    ResultSet rs = null;
	    try {
	      Statement st = (Statement) con.createStatement();
	      rs = st.executeQuery(qry);
	    }
	    catch (SQLException e) {
	      connect();
	      System.err.println(e);
	    }
	    return rs;
	  }
    
}
