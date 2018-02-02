package ftc.shift.springbootsample;

import ftc.shift.springbootsample.models.Exchange;
import ftc.shift.springbootsample.models.Ware;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ExchangeRepository {
    private final AtomicLong sequence = new AtomicLong(1);
    private Map<String, Collection<Exchange>> exchanges=new HashMap<>();

    public ExchangeRepository() {
    }

    public Collection<Exchange> findExchange(String exchangeId){
        for (Map.Entry<String, Collection<Exchange>> entry : exchanges.entrySet()) {
            if (entry.getKey().equals(exchangeId))
                return entry.getValue();
        }
        return null;
    }

    public void addExchange (Collection<Exchange> exchange){
        exchanges.put(String.valueOf(sequence.getAndIncrement()),exchange);
    }

    public Collection<Collection<Exchange>> getUserExchanges(String userId){
        Vector<Collection<Exchange>> userExchanges=new Vector<>();
        for(Map.Entry<String, Collection<Exchange>> entry : exchanges.entrySet()) {
            boolean participate=false;
            for(Exchange exchange : entry.getValue()){
                if((exchange.getFirstUserId().equals(userId)) || (exchange.getSecondUserId().equals(userId))){
                    participate=true;
                    break;
                }
            }
            if(participate){
                userExchanges.add(entry.getValue());
            }
        }
        if(userExchanges.isEmpty())
            return null;
        else return userExchanges;
    }
}




