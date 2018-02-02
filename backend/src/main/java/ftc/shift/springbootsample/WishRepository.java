package ftc.shift.springbootsample;

import ftc.shift.springbootsample.models.Ware;
import ftc.shift.springbootsample.models.Wish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Vector;

@Repository
public class WishRepository {
    private Vector<Wish> wishRepository=new Vector<Wish>();

    public Wish addWish(Wish wish, String userId)
    {
        wish.setUserId(userId);
        wishRepository.add(wish);
        return wish;
    }

    public Collection<Wish> getUserWishes(String userId){
        ArrayList<Wish> userWishes=new ArrayList<>();

        for(Wish wish : wishRepository){
            if(wish.getUserId().equals(userId)){
                userWishes.add(wish);
            }
        }
        return userWishes;
    }

}
