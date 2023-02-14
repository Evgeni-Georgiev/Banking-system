package eu.deltasource.internship.abankingsystem.service.ownerService;

import eu.deltasource.internship.abankingsystem.model.Owner;
import eu.deltasource.internship.abankingsystem.repository.bankInstitutionRepository.BankInstitutionRepository;
import eu.deltasource.internship.abankingsystem.repository.ownerRepository.OwnerRepository;

/**
 * Processing business logic for Owner.
 */
public class OwnerServiceImpl implements OwnerService{

    private final OwnerRepository ownerRepository;

    private final BankInstitutionRepository bankInstitutionRepository;

    public OwnerServiceImpl(OwnerRepository ownerRepository, BankInstitutionRepository bankInstitutionRepository) {
        this.ownerRepository = ownerRepository;
        this.bankInstitutionRepository = bankInstitutionRepository;
    }

    @Override
    public void createOwner(String name) {
        Owner newOwner = new Owner(name);
        ownerRepository.addOwnerToMap(newOwner);
        bankInstitutionRepository.addOwner(newOwner);
    }

    @Override
    public Owner getOwnerById(int id) {
        return ownerRepository.getOwnerById(id);
    }
}
