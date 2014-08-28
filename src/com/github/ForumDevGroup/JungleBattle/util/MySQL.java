package com.github.ForumDevGroup.JungleBattle.util;

import com.github.ForumDevGroup.JungleBattle.Main;
import org.bukkit.Bukkit;

import java.sql.*;

public class MySQL {

	private Connection c;
	private final String url;

    // Konstruktor - Selbsterklärend
	public MySQL(String host, String database, String user, String password) throws SQLException {
		this.url = ("jdbc:mysql://" + host + "/" + database + "?user=" + user + "&password=" + password + "&autoReconnect=true");
		this.c = DriverManager.getConnection(this.url);
		initDriver("com.mysql.jdbc.Driver");
        startReconnecting();
	}

    // Query Statement
	public ResultSet query(final String query) throws SQLException {
		PreparedStatement ps = c.prepareStatement(query);
		return ps.execute() ? ps.getResultSet() : null;
	}

    // Update Statement
	public void update(String query) throws SQLException {
		Statement s = c.createStatement();
		s.executeUpdate(query);
		s.close();
	}

    // Lädt den Driver
	public void initDriver(String driver) {
		try {
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    // Gibt die Verbindung zur Datenbank aus
	public Connection getConnection() {
		return this.c;
	}

    // Öffnet die Verbindung zur Datenbank
    public void openConnection() {
        try {
            c = DriverManager.getConnection(this.url);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // Trennt die Verbindung zur Datenbank
	public void closeConnection() {
		try {
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    // Funktion zum wiederkehrenden reconnecten der Datenbank
    public void startReconnecting() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance(), new Runnable() {
            @Override
            public void run() {
                closeConnection();
                openConnection();
            }
        },0L,20L*60*5);
    }

}
