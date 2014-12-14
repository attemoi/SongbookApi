package fi.attemoisio.songbookapi.postgres;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;

import fi.attemoisio.songbookapi.errorhandling.ApiError;
import fi.attemoisio.songbookapi.exceptions.ApiException;


public abstract class PostgresRepository {
	
	SQLDriverManager driver;
	
	final String ERROR_CODE_UNIQUE_VIOLATION = "23505";
	
	public PostgresRepository(SQLDriverManager driver) {
		this.driver = driver;
	}
	
	public interface ConnectionHandler<T> {
		public T handleConnection(Connection conn) throws SQLException, SQLTimeoutException;
	}
	
	public <T> T handleConnection(ConnectionHandler<T> handler, ApiError requestError, ApiError requestTimeout) {
		try {
			Connection conn = driver.getConnection();
			try {
				return handler.handleConnection(conn);
			} catch (SQLTimeoutException e) {
				throw new ApiException(requestTimeout, e);
			} catch (SQLException e) {
				throw new ApiException(requestError, e);
			} finally {
				conn.close();
			}
		} catch (SQLTimeoutException e) {
			throw new ApiException(getRepositoryConnectionTimeoutApiError(), e);
		} catch (SQLException e) {
			throw new ApiException(getRepositoryConnectionFailApiError(), e);
		}
	}
	
	public abstract ApiError getRepositoryConnectionFailApiError();
	public abstract ApiError getRepositoryConnectionTimeoutApiError();
	
}
