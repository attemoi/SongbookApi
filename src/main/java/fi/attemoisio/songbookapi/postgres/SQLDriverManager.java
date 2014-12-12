package fi.attemoisio.songbookapi.postgres;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;

public interface SQLDriverManager {
	
	public Connection getConnection() throws SQLException, SQLTimeoutException;

}
