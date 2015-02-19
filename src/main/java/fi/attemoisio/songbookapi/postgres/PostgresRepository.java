/* 
 * The MIT License
 *
 * Copyright 2015 Atte Moisio.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
