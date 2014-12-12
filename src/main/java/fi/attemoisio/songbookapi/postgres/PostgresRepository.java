package fi.attemoisio.songbookapi.postgres;


public class PostgresRepository {
	
	SQLDriverManager driver;

	public PostgresRepository(SQLDriverManager driver) {
		this.driver = driver;
	}
	
}
