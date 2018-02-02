package ftc.shift.springbootsample.models;

public class Exchange {

    private User firstUser;
    private User secondUser;
    private Ware firstWare;
    private Ware secondWare;

    public User getFirstUser() {
        return firstUser;
    }

    public Ware getFirstWare() {
        return firstWare;
    }

    public User getSecondUser() {
        return secondUser;
    }

    public Ware getSecondWare() {
        return secondWare;
    }

    public void setFirstUser(User firstUser) {
        this.firstUser = firstUser;
    }

    public void setFirstWare(Ware firstWare) {
        this.firstWare = firstWare;
    }

    public void setSecondUser(User secondUser) {
        this.secondUser = secondUser;
    }

    public void setSecondWare(Ware secondWare) {
        this.secondWare = secondWare;
    }

    public Exchange(){

    }
}
