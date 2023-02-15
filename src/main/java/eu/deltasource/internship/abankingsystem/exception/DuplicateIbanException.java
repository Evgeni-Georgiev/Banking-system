package eu.deltasource.internship.abankingsystem.exception;

import eu.deltasource.internship.abankingsystem.enums.MessageTemplate;

import java.util.Optional;

/**
 * Case: IBANs must be unique. If two accounts have equivalent IBANs, this exception will throw.
 */
public class DuplicateIbanException extends RuntimeException {

    public DuplicateIbanException(Optional<String> message) {
        super(MessageTemplate.DUPLICATE_IBAN.getMessageTemplate() + message);
    }
}
