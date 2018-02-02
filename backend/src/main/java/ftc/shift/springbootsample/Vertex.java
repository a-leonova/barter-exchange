package ftc.shift.springbootsample;

import java.util.Vector;

public class Vertex {
    public String userId;
    public Vector<String> availableWaresId;
    public Vector<String> neededWaresId;
    public String exchangeWare;
    public Vertex parent;

    public Vertex(String userId){
        this.userId=userId;
        availableWaresId=new Vector<>();
        neededWaresId=new Vector<>();
        parent=null;
    }

    public String getPossibleExchange(Vertex partner){
        for(String wareId : availableWaresId){
            if(partner.neededWaresId.contains(wareId)){
                return wareId;
            }
        }
        return null;
    }
}
