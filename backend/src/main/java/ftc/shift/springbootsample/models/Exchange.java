package ftc.shift.springbootsample.models;

public class Exchange {

    private String firstUserId;
    private String secondUserId;
    private String firstWareId;
    private String secondWareId;

    public String getFirstUserId() {
        return firstUserId;
    }

    public String getFirstWareId() {
        return firstWareId;
    }

    public String getSecondUserId() {
        return secondUserId;
    }

    public String getSecondWareId() {
        return secondWareId;
    }

    public void setFirstUserId(String firstUserId) {
        this.firstUserId = firstUserId;
    }

    public void setFirstWareId(String firstWareId) {
        this.firstWareId = firstWareId;
    }

    public void setSecondUserId(String secondUserId) {
        this.secondUserId = secondUserId;
    }

    public void setSecondWareId(String secondWareId) {
        this.secondWareId = secondWareId;
    }


}
