package fi.attemoisio.songbookapi.postgres;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

import fi.attemoisio.songbookapi.repository.exceptions.RepositoryConnectionFailedException;
import fi.attemoisio.songbookapi.repository.exceptions.RepositoryRequestFailedException;
import fi.attemoisio.songbookapi.repository.exceptions.RepositoryTimeoutException;

public class PostgresDriverManager implements SQLDriverManager {

	private String dbUrl;
	private String username;
	private String password;
	
	public PostgresDriverManager() {
		
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
	

	public Connection getConnection() throws RepositoryConnectionFailedException {
		
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RepositoryConnectionFailedException(e.getMessage());
		}
		
		try {
			return DriverManager.getConnection(dbUrl, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RepositoryConnectionFailedException(e.getMessage());
		}
		
	}

	public CachedRowSet execQuery(String sql) throws RepositoryConnectionFailedException, RepositoryRequestFailedException, RepositoryTimeoutException {
		
		try {
			Connection conn = getConnection();
			try {
				Statement statement = conn.createStatement();
				try {
					ResultSet result = statement.executeQuery(sql);
					try {
						RowSetFactory rowSetFactory = RowSetProvider.newFactory();
						CachedRowSet rowSet = rowSetFactory.createCachedRowSet();
						rowSet.populate(result);
						return rowSet;
					} catch (SQLTimeoutException e) {
						throw new RepositoryTimeoutException(e.getMessage());
					} finally {
						result.close();
					}
				} finally {
					statement.close();
				}
			} finally {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RepositoryRequestFailedException(e.getMessage());
		} 
		
	}
	
	public int execUpdate(String sql) throws RepositoryConnectionFailedException, RepositoryRequestFailedException, RepositoryTimeoutException {
		
		try {
			Connection conn = getConnection();
			try {
				Statement statement = conn.createStatement();
				try {
					int result = statement.executeUpdate(sql);
					return result;
				} catch (SQLTimeoutException e) {
					throw new RepositoryTimeoutException(e.getMessage());
				} finally {
					statement.close();
				}
			} finally {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RepositoryRequestFailedException(e.getMessage());
		}
		
	}

}
