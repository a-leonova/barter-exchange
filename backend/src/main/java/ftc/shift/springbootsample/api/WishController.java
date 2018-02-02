package ftc.shift.springbootsample.api;

import ftc.shift.springbootsample.WareRepository;
import ftc.shift.springbootsample.WishRepository;
import ftc.shift.springbootsample.models.Ware;
import ftc.shift.springbootsample.models.Wish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

@RestController
@CrossOrigin
public class WishController {
    @Autowired
    private WishRepository wishRepository;
    @Autowired
    private WareRepository wareRepository;

    @PostMapping("/api/new_wish/{id}") public Wish addWish(@RequestBody String wareId,
                                                           @PathVariable String id) {

        return wishRepository.addWish(wareId, id);
    }

    @GetMapping("api/user_wish/{id}") public Collection<Ware> userWishes(@PathVariable String id) {

        ArrayList<Ware> userWares = new ArrayList<>();
        Collection<Wish> wishes = wishRepository.getUserWishes(id);

        for (Wish wish : wishes){
            Ware ware = wareRepository.findWare(wish.getWareId());
            if (ware != null){
                userWares.add(ware);
            }
        }
        return userWares;
    }

    @GetMapping("api/user_wish/find") public Collection<Ware> giveSomeWares() {

        ArrayList<Ware> givingList= new ArrayList<>();
        Collection<Ware> wares = wareRepository.getAll();

        int i = 0;
        for (Iterator<Ware> ware = wares.iterator(); ware.hasNext() && i < 10; ++i){
            Ware w = ware.next();
            givingList.add(w);
        }
        return givingList;
    }


}

