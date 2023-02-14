package eu.deltasource.internship.abankingsystem.exception;

public class BankInstitutionNotFoundException extends RuntimeException{
    public BankInstitutionNotFoundException(String message) {
        super(message);
    }
}
