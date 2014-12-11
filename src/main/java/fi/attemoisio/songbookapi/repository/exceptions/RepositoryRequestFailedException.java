package fi.attemoisio.songbookapi.repository.exceptions;

public class RepositoryRequestFailedException extends Exception{

	private static final long serialVersionUID = -1607307743605403733L;

	public RepositoryRequestFailedException() {}

    public RepositoryRequestFailedException(String message)
    {
       super(message);
    }
	
}
