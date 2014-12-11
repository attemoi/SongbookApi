package fi.attemoisio.songbookapi.repository.exceptions;

public class RepositoryTimeoutException extends Exception {

	private static final long serialVersionUID = 8184313370873051466L;

	public RepositoryTimeoutException() {}

    public RepositoryTimeoutException(String message)
    {
       super(message);
    }

}
