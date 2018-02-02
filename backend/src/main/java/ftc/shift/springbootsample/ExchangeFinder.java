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
                    Vertex nextPossible=users.get(user);
                    String exchangeWare=burning.getPossibleExchange(nextPossible);
                    if(exchangeWare!=null){
                        nextPossible.parent =burning;
                        nextPossible.exchangeWare=exchangeWare;
                        if(nextPossible.getPossibleExchange(starter)!=null){
                            loopFound=true;
                            starter.parent=nextPossible;
                            starter.exchangeWare=nextPossible.getPossibleExchange(starter);
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
