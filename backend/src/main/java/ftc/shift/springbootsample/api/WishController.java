package ftc.shift.springbootsample.api;

import ftc.shift.springbootsample.WishRepository;
import ftc.shift.springbootsample.models.Wish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@RestController
@CrossOrigin
public class WishController {
    @Autowired
    private WishRepository wishRepository;

    @PostMapping("/api/new_wish/{id}") public Wish addWish(@RequestBody Wish wish,
                                                           @PathVariable String id) {

        return wishRepository.addWish(wish, id);
    }

    @GetMapping("api/user_wish/{id}") public Collection<Wish> userWishes(@PathVariable String id) {
        return wishRepository.getUserWishes(id);
    }

}

