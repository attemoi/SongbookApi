package fi.attemoisio.songbookapi.postgres;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostgresHelper {

	private Connection conn;
	private String dbUrl;
	private String username;
	private String password;
	
	public PostgresHelper() {
		
		URI dbUri;
		try {
			dbUri = new URI(System.getenv("DATABASE_URL"));
			username = dbUri.getUserInfo().split(":")[0];
		    password = dbUri.getUserInfo().split(":")[1];
		    
		    dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

	}
	

	public boolean connect() throws SQLException, ClassNotFoundException {
		Class.forName("org.postgresql.Driver");
	    this.conn = DriverManager.getConnection(dbUrl, username, password);
		return true;
	}

	public ResultSet execQuery(String query) throws SQLException {
		return this.conn.createStatement().executeQuery(query);
	}

}
