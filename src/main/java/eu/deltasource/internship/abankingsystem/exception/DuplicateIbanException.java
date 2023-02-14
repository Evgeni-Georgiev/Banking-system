package eu.deltasource.internship.abankingsystem.exception;

import eu.deltasource.internship.abankingsystem.enums.MessageTemplate;

import java.util.Optional;

public class DuplicateIbanException extends RuntimeException {

    public DuplicateIbanException(Optional<String> message) {
        super(MessageTemplate.DUPLICATE_IBAN.getMessageTemplate() + message);
    }
}
