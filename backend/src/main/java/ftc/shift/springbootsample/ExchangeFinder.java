package ftc.shift.springbootsample;

import java.util.LinkedList;
import java.util.Vector;

public class ExchangeFinder {
    public static Vertex findExchangeChain(Vertex starter, Vector<Vertex> users, int exchangesNumber){
        LinkedList<Vertex> possibleParents=new LinkedList<>();
        possibleParents.push(starter);
        boolean loopFound=false;
        for(int exchange=0; exchange<exchangesNumber && !loopFound; exchange++){
            int parentsNumber=possibleParents.size();
            for(int parent=0; parent<parentsNumber && !loopFound; parent++){
                Vertex burning=possibleParents.remove();
                for(int user=0; user<users.size(); user++){
                    String exchangeWare=burning.getPossibleExchange(users.get(user));
                    if(!exchangeWare.isEmpty()){
                        Vertex nextPossible=users.get(user);
                        nextPossible.parent =burning;
                        nextPossible.exchangeWare=exchangeWare;

                        if(!nextPossible.getPossibleExchange(starter).isEmpty()){
                            loopFound=true;
                            starter.parent=nextPossible;
                            break;
                        }
                        possibleParents.push(nextPossible);
                        users.remove(user);
                        user--;
                    }
                }
            }
        }
        return starter;
    }

}
