package ftc.shift.springbootsample;

import ftc.shift.springbootsample.models.Ware;
import org.springframework.beans.factory.annotation.Autowired;
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
        Ware ware = new Ware("Хороший велосипед", String.valueOf(sequence.getAndIncrement()),
                "Спорт", "1", "<1 года");
        wares.put(ware.getId(), ware);

        ware = new Ware("Скоростной велосипед", String.valueOf(sequence.getAndIncrement()),
                "Спорт", "2", "1 - 2 года");
        wares.put(ware.getId(), ware);

        ware = new Ware("Мишка", String.valueOf(sequence.getAndIncrement()),
                    "Игрушки", "2", "<1 года");
        wares.put(ware.getId(), ware);

        ware = new Ware("Игрушка мягкая Лимон", String.valueOf(sequence.getAndIncrement()),
                    "Игрушка", "3", "2 - 4 года");
        wares.put(ware.getId(), ware);

        ware = new Ware("Скейт", String.valueOf(sequence.getAndIncrement()),
                "Спорт", "5", "1 - 2 года");
        wares.put(ware.getId(), ware);

        ware = new Ware("Самокат", String.valueOf(sequence.getAndIncrement()),
                "Спорт", "6", "2 - 4 года");
        wares.put(ware.getId(), ware);

        ware = new Ware("Наушники", String.valueOf(sequence.getAndIncrement()),
                "Аудио и видео", "7", "<1 года");
        wares.put(ware.getId(), ware);

        ware = new Ware("Asus", String.valueOf(sequence.getAndIncrement()),
                "Ноутбуки", "8", "2 - 4 года");
        wares.put(ware.getId(), ware);

        ware = new Ware("Угги", String.valueOf(sequence.getAndIncrement()),
                "Одежда, обувь, аксуссуары", "7", "<1 года");
        wares.put(ware.getId(), ware);
    }

    public Collection<Ware> getAll(){
        return wares.values();
    }

    public Ware addWare(Ware ware, String id){
        ware.setId(String.valueOf(sequence.getAndIncrement()));
        ware.setOwnerId(id);
        ware.setStatus("Свободный");
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
