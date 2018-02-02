package ftc.shift.springbootsample.api;

import ftc.shift.springbootsample.WareRepository;
import ftc.shift.springbootsample.WishRepository;
import ftc.shift.springbootsample.models.Ware;
import ftc.shift.springbootsample.models.Wish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;


@RestController
@CrossOrigin
public class WishController {
    @Autowired
    private WishRepository wishRepository;
    @Autowired
    private WareRepository wareRepository;

    @PostMapping("/api/new_wish/{id}") public Wish addWish(@RequestBody Wish wish,
                                                           @PathVariable String id) {

        return wishRepository.addWish(wish, id);
    }

    @GetMapping("api/user_wish/{id}") public Collection<Ware> userWishes(@PathVariable String id) {

        ArrayList<Ware> userWares = new ArrayList<>();
        Collection<Wish> wishes = wishRepository.getUserWishes(id);

        for (Wish wish : wishes){
            userWares.add(wareRepository.findWare(wish.getWareId()));
        }

        return userWares;



    }


}

