package eu.deltasource.internship.abankingsystem.service.OwnerService;

import eu.deltasource.internship.abankingsystem.model.Owner;

public interface OwnerService {

    /**
     * Create owner from model instance
     */
    void createOwner(String name);

    Owner getOwnerById(int id);

}
