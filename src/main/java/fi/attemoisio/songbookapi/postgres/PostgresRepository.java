package fi.attemoisio.songbookapi.postgres;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;


public class PostgresRepository {
	
	SQLDriverManager driver;
	
	final String ERROR_CODE_UNIQUE_VIOLATION = "23505";
	
	public PostgresRepository(SQLDriverManager driver) {
		this.driver = driver;
	}
	
	public interface ConnectionHandler<T> {
		public T handleConnection(Connection conn) throws SQLException, SQLTimeoutException;
	}
	
}
