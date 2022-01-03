
package BookStoreApp;

public class Customer {
    private String username, password, status;
    private double points;

    public Customer(String username, String password, double points) {
        this.username = username;
        this.password = password;
        this.points = points;
        this.status = points < 1000 ? "Silver" : "Gold";   //less than 1000p=silver else gold   
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
        this.status = points < 1000 ? "Silver" : "Gold";    //less than 1000p=silver else gold  
    }
}
