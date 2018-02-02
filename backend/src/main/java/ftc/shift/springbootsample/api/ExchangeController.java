package ftc.shift.springbootsample.api;

import ftc.shift.springbootsample.*;
import ftc.shift.springbootsample.models.Exchange;
import ftc.shift.springbootsample.models.User;
import ftc.shift.springbootsample.models.Ware;
import ftc.shift.springbootsample.models.Wish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;


@RestController
@CrossOrigin
public class ExchangeController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WareRepository wareRepository;
    @Autowired
    private ExchangeRepository exchangeRepository;
    @Autowired
    private WishRepository wishRepository;

    @GetMapping("/api/exchanges/{id}") public Collection<Collection<Exchange>> getUserExchange(@PathVariable String userId){
        return exchangeRepository.getUserExchanges(userId);
    }

    @GetMapping("/api/exchanges/find-simple") public Collection<Exchange> getSingleExchange(@RequestBody Wish wish){
        return parseVertex(findExchange(wish,1));
    }

    @GetMapping("/api/exchanges/find-complex") public Collection<Exchange> getComplexExchange(@RequestBody Wish wish){
        return parseVertex(findExchange(wish,5));
    }

    private Collection<Exchange> parseVertex(Vertex vertex){
        Vector<Exchange> exchangeChain=new Vector<>();
        Vertex current=vertex.parent;
        Vertex previous = vertex;
        do{
            Exchange exchange=new Exchange();
            exchange.setFirstUserId(current.userId);
            exchange.setFirstWareId(previous.exchangeWare);
            exchange.setSecondUserId(current.parent.userId);
            exchange.setSecondWareId(current.exchangeWare);
            exchangeChain.add(0,exchange);
            previous=current;
            current=current.parent;
        }while(!current.equals(vertex));
        return exchangeChain;
    }

    private Vertex findExchange(Wish wish, int exchangeNumber){
        Collection<Ware> wares=wareRepository.getUserWares(wish.getUserId());
        Vector<Wish> wishes=new Vector<>();
        wishes.add(wish);
        Vertex starter=createVertex(wish.getUserId(),wishes, wares);
        Vector<Vertex> partners=new Vector<>();
        for(User user : userRepository.getCityUsers(userRepository.findUser(wish.getUserId()).getCity())){
            if(!user.getId().equals(wish.getUserId())) {
                String userId=user.getId();
                partners.add(createVertex(userId,wishRepository.getUserWishes(userId),wareRepository.getUserWares(userId)));
            }
        }
        return ExchangeFinder.findExchangeChain(starter,partners,exchangeNumber);
    }

    private Vertex createVertex(String userId, Collection<Wish> wishes, Collection<Ware> wares){
        Vertex vertex=new Vertex(userId);
        for(Wish wish : wishes){
            vertex.neededWaresId.add(wish.getWareId());
        }
        for(Ware ware : wares){
            vertex.availableWaresId.add(ware.getId());
        }
        return vertex;
    }
    //@GetMapping("/api/")


}
