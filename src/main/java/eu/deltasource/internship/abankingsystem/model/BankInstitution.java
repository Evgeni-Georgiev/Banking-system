package eu.deltasource.internship.abankingsystem.model;

public class BankInstitution {

    private final String name;

    private final String address;

    private int dayCountTime = 1;

    public BankInstitution(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getDayCountTime() {
        return dayCountTime;
    }

    public void setDayCountTime(int dayCountTime) {
        this.dayCountTime = dayCountTime;
    }

    @Override
    public String toString() {
        return String.format(
                """
                    Bank Details:
                    Name: %s
                    Address: %s""",
            getName(),
            getAddress()
        );
    }
}
