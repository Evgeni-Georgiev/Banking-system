package eu.deltasource.internship.abankingsystem.exception;

import eu.deltasource.internship.abankingsystem.enums.MessageTemplate;
import eu.deltasource.internship.abankingsystem.model.BankInstitution;

import java.util.Optional;

/**
 * Case: If the BankInstitution does not exist return Exception for not found BankInstitution.
 */
public class BankInstitutionNotFoundException extends RuntimeException{

    public BankInstitutionNotFoundException(Optional<BankInstitution> message) {
        super(MessageTemplate.BANK_INSTITUTION_DOES_NOT_EXIST.getMessageTemplate() + message);
    }
}
