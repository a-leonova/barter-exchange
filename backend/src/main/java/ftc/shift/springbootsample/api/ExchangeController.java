package ftc.shift.springbootsample.api;

import ftc.shift.springbootsample.*;
import ftc.shift.springbootsample.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/api/exchanges/{userId}") public Collection<Collection<Exchange>> getUserExchange(@PathVariable String userId){
        Collection<Collection<StoredExchange>> usersStoredExchanges = exchangeRepository.getUserExchanges(userId);
        if(usersStoredExchanges==null) {
            return null;
        }
        Vector<Collection<Exchange>> usersExchanges=new Vector<>();
        for(Collection<StoredExchange> storedExchange : usersStoredExchanges){
            usersExchanges.add(buildExchange(storedExchange));
        }
        return usersExchanges;
    }

    @PostMapping("/api/exchanges/find-simple") public Collection<Exchange> getSingleExchange(@RequestBody Wish wish){
        Collection<StoredExchange> exchange = parseVertex(findExchange(wish,1));
        if(exchange==null) {
            return null;
        }
        exchangeRepository.addExchange(exchange);
        return buildExchange(exchange);
    }

    @PostMapping("/api/exchanges/find-complex") public Collection<Exchange> getComplexExchange(@RequestBody Wish wish){
        Collection<StoredExchange> exchange = parseVertex(findExchange(wish,5));
        if(exchange==null) {
            return null;
        }
        exchangeRepository.addExchange(exchange);
        return buildExchange(exchange);
    }

    private Collection<StoredExchange> parseVertex(Vertex vertex){
        if(vertex.parent!=null) {
            Vector<StoredExchange> exchangeChain = new Vector<>();
            Vertex current = vertex.parent;
            Vertex previous = vertex;
            do {
                StoredExchange exchange = new StoredExchange();
                exchange.setFirstUserId(current.userId);
                exchange.setFirstWareId(previous.exchangeWare);
                exchange.setSecondUserId(current.parent.userId);
                exchange.setSecondWareId(current.exchangeWare);
                exchangeChain.add(0, exchange);
                previous = current;
                current = current.parent;
            } while (!current.equals(vertex));
            return exchangeChain;
        }else {
            return null;
        }

    }

    private Vertex findExchange(Wish wish, int exchangeNumber){
        Collection<Ware> wares=wareRepository.getUserWares(wish.getUserId());
        Vector<Wish> wishes=new Vector<>();
        wishes.add(wish);
        Vertex starter=createVertex(wish.getUserId(),wishes, wares);
        Vector<Vertex> partners=new Vector<>();
        for(User user : userRepository.getCityUsers(userRepository.getUserById(wish.getUserId()).getCity())){
            if(!user.getId().equals(wish.getUserId())) {
                String userId=user.getId();
                partners.add(createVertex(userId,wishRepository.getUserWishes(userId),wareRepository.getUserWares(userId)));
            }
        }
        return ExchangeFinder.findExchangeChain(starter,partners,exchangeNumber);
    }

    private Collection<Exchange> buildExchange(Collection<StoredExchange> source){
        Vector<Exchange> exchange=new Vector<>();
        for(StoredExchange s : source){
            Exchange e=new Exchange();
            e.setFirstUser(userRepository.getUserById(s.getFirstUserId()));
            e.setSecondUser(userRepository.getUserById(s.getSecondUserId()));
            e.setFirstWare(wareRepository.findWare(s.getFirstWareId()));
            e.setSecondWare(wareRepository.findWare(s.getSecondWareId()));
            exchange.add(e);
        }
        return exchange;
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
}
