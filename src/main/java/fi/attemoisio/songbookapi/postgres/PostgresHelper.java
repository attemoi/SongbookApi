package fi.attemoisio.songbookapi.postgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostgresHelper {

	private Connection conn;

	public boolean connect() throws SQLException, ClassNotFoundException {
		Class.forName("org.postgresql.Driver");
		this.conn = DriverManager.getConnection(System.getenv("DATABASE_URL"));
		return true;
	}

	public ResultSet execQuery(String query) throws SQLException {
		return this.conn.createStatement().executeQuery(query);
	}

}
