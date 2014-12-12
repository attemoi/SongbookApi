package fi.attemoisio.songbookapi.postgres;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;

public class PostgresDriverManager implements SQLDriverManager {

	private String dbUrl;
	private String username;
	private String password;

	public PostgresDriverManager() throws ClassNotFoundException {

		URI dbUri;
		try {
			dbUri = new URI(System.getenv("DATABASE_URL"));
			username = dbUri.getUserInfo().split(":")[0];
			password = dbUri.getUserInfo().split(":")[1];

			dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':'
					+ dbUri.getPort() + dbUri.getPath();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		Class.forName("org.postgresql.Driver");

	}

	public Connection getConnection() throws SQLException, SQLTimeoutException {
		return DriverManager.getConnection(dbUrl, username, password);
	}

}
