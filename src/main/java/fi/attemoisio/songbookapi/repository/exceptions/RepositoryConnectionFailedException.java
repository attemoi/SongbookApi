package fi.attemoisio.songbookapi.repository.exceptions;

public class RepositoryConnectionFailedException extends Exception {

	private static final long serialVersionUID = -9007118831981894024L;
	
    public RepositoryConnectionFailedException() {}

    public RepositoryConnectionFailedException(String message)
    {
       super(message);
    }

}
