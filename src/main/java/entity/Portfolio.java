package entity;

/**
 * Entity representing a Portfolio.
 */
public class Portfolio {
    private final int id;
    private final String name;
    private final int userId;
    private double balance;

    public Portfolio(int id, String name, int userId, double balance) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getUserId() {
        return userId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
