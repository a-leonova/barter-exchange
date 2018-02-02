package ftc.shift.springbootsample;

import ftc.shift.springbootsample.models.Ware;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository

public class WareRepository {
    private final AtomicLong sequence = new AtomicLong(1);
    private Map<String, Ware> wares = new HashMap<>();

    public WareRepository(){
    Ware ware = new Ware("velik", String.valueOf(sequence.getAndIncrement()),
            "velosiped", "1", "1 year");
    wares.put(ware.getId(), ware);

    ware = new Ware("My bike", String.valueOf(sequence.getAndIncrement()),
            "velosiped", "2", "2 year");
    wares.put(ware.getId(), ware);

    ware = new Ware("Mishka", String.valueOf(sequence.getAndIncrement()),
                "toy", "2", "1 year");
    wares.put(ware.getId(), ware);

    ware = new Ware("Limon", String.valueOf(sequence.getAndIncrement()),
                "toy", "3", "3 year");
    wares.put(ware.getId(), ware);

    }

    public Collection<Ware> getAll(){
        return wares.values();
    }

    public Ware addWare(Ware ware, String id){
        ware.setId(String.valueOf(sequence.getAndIncrement()));
        ware.setOwnerId(id);
        ware.setStatus("Свободен");
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

    public Collection<Ware> getUserWares(String id){

        Collection<Ware> allWare = getAll();
        ArrayList<Ware> userWare = new ArrayList<>();

        for (Ware it : allWare){
            if (it.getOwnerId().equals(id))
                userWare.add(it);
        }
        return  userWare;
    }


}
