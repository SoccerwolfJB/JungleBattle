package com.github.ForumDevGroup.JungleBattle.util;

public class MySQL {

	private final Connection c;
	private final String url;

	public MySQL(String host, String database, String user, String password) throws SQLException {
		this.url = ("jdbc:mysql://" + host + "/" + database + "?user=" + user + "&password=" + password + "&autoReconnect=true");
		this.c = DriverManager.getConnection(this.url);
		initDriver("com.mysql.jdbc.Driver");
	}

	public ResultSet query(final String query) throws SQLException {
		PreparedStatement ps = c.prepareStatement(query);
		return ps.execute() ? ps.getResultSet() : null;
	}


	public void update(String query) throws SQLException {
		Statement s = c.createStatement();
		s.executeUpdate(query);
		s.close();
	}

	public void initDriver(String driver) {
		try {
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		return this.c;
	}

	public void closeConnection() {
		try {
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
