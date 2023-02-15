package eu.deltasource.internship.abankingsystem.model;

/**
 * Client/Customer having at least one BankAccount in at least one BankInstitution.
 */
public class Owner {

    private int id;

    private final String name;

    public Owner(String name) {
        this.name = name;
    }

    public Owner(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format(
            """
                
                Owner Details: %s
                """, getName());
    }

}
