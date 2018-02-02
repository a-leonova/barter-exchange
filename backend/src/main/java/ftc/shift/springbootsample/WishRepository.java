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

    public WishRepository(){
        Wish wish = new Wish("3", "1");
        wishRepository.add(wish);

        wish = new Wish("3", "4");
        wishRepository.add(wish);

        wish = new Wish("1", "2");
        wishRepository.add(wish);

        wish = new Wish("2", "1");
        wishRepository.add(wish);

        wish = new Wish("4", "2");
        wishRepository.add(wish);

        wish = new Wish("1", "3");
        wishRepository.add(wish);

        wish = new Wish("4", "1");
        wishRepository.add(wish);
    }

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
