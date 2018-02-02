package ftc.shift.springbootsample.models;

public class Wish {

    private String userId;
    private String wareId;

    public String getUserId() {
        return userId;
    }

    public String getWareId() {
        return wareId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setWareId(String wareId) {
        this.wareId = wareId;
    }

    public Wish(String user, String ware)
    {
        userId=user;
        wareId=ware;
    }
}
