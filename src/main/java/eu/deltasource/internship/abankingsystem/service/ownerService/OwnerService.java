package eu.deltasource.internship.abankingsystem.service.ownerService;

import eu.deltasource.internship.abankingsystem.model.Owner;

public interface OwnerService {

    /**
     * Create owner from model instance
     */
    void createOwner(String name);

    /**
     * Get created Owner by id
     */
    Owner getOwnerById(int id);

}
