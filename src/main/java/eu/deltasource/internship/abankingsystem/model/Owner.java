package eu.deltasource.internship.abankingsystem.model;

public class Owner {

    private int id;

    private final String name;

//    private final List<BankAccount> ownerAccountCount = new ArrayList<>();

//    private final List<BankAccount> bankAccounts = new ArrayList<>();

    public Owner(String name) {
        this.name = name;
    }

    public Owner(int id, String name) {
        this.id = id;
        this.name = name;
    }

//    public List<BankAccount> getOwnerAccountCount() {
//        return unmodifiableList(ownerAccountCount);
//    }


//    public void addAccountToOwner(final BankAccount bankAccount) {
//        bankAccounts.add(bankAccount);
//    }
//
//    public List<BankAccount> getBankAccounts() {
//        return unmodifiableList(bankAccounts);
//    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("%nOwner Details: %s", getName());
    }

}
