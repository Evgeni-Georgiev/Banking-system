package eu.deltasource.internship.abankingsystem.model;

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

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("%nOwner Details: %s", getName());
    }

}
