package eu.deltasource.internship.abankingsystem.exception;

public class FileCannotBeReadException extends RuntimeException{
    public FileCannotBeReadException(String message) {
        super(message);
    }
}
