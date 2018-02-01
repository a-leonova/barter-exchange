package ftc.shift.springbootsample;

import ftc.shift.springbootsample.models.Ware;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class WareRepository {
    private final AtomicLong sequence = new AtomicLong(1);
    private Map<String, Ware> wares = new HashMap<>();

    public WareRepository(){

    }

    public Ware addWare(Ware ware, String id){
        ware.setId(String.valueOf(sequence.getAndIncrement()));
        ware.setOwnerId(id);
        ware.setStatus("FREE");
        wares.put(ware.getId(), ware);
        return ware;
    }

    public Ware findWare(String id){
        for (Map.Entry<String, Ware> entry : wares.entrySet()) {
            if (entry.getValue().getId().equals(id))
                return entry.getValue();
        }
        return null;
    }

}
