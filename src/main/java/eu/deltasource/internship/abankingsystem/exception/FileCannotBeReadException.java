package eu.deltasource.internship.abankingsystem.exception;

/**
 * Case: If the file set to read from does not exist or file path is wrong, this exception will throw.
 */
public class FileCannotBeReadException extends RuntimeException{

    public FileCannotBeReadException(String message) {
        super(message);
    }
}
