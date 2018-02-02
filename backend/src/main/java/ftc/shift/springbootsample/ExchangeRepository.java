package ftc.shift.springbootsample;

import ftc.shift.springbootsample.models.StoredExchange;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ExchangeRepository {
    private final AtomicLong sequence = new AtomicLong(1);
    private Map<String, Collection<StoredExchange>> exchanges=new HashMap<>();

    public ExchangeRepository() {
    }

    public Collection<StoredExchange> findExchange(String exchangeId){
        for (Map.Entry<String, Collection<StoredExchange>> entry : exchanges.entrySet()) {
            if (entry.getKey().equals(exchangeId))
                return entry.getValue();
        }
        return null;
    }

    public void addExchange (Collection<StoredExchange> exchange){
        exchanges.put(String.valueOf(sequence.getAndIncrement()),exchange);
    }

    public Collection<Collection<StoredExchange>> getUserExchanges(String userId){
        Vector<Collection<StoredExchange>> userExchanges=new Vector<>();
        for(Map.Entry<String, Collection<StoredExchange>> entry : exchanges.entrySet()) {
            boolean participate=false;
            for(StoredExchange exchange : entry.getValue()){
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




