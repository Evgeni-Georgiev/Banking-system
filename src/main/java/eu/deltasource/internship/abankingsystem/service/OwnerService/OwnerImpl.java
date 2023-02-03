package eu.deltasource.internship.abankingsystem.service.OwnerService;

import eu.deltasource.internship.abankingsystem.model.Owner;
import eu.deltasource.internship.abankingsystem.repository.ownerRepository.OwnerRepository;

public class OwnerImpl implements OwnerService{

    private final OwnerRepository ownerRepository;

    public OwnerImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public void createOwner(String name) {
        Owner newOwner = new Owner(name);
        ownerRepository.addOwnerToMap(newOwner);
    }

    @Override
    public Owner getOwnerById(int id) {
        return ownerRepository.getOwnerById(id);
    }
}
