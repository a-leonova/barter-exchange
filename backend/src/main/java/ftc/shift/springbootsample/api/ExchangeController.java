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

    @GetMapping("/api/exchanges-user/{userId}") public Collection<Collection<Exchange>> getUserExchange(@PathVariable String userId){
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

    @GetMapping("/api/exchanges/find-simple") public Response getSingleExchange(@RequestParam String userId,
                                                                                             @RequestParam String id){

        Response response = new Response();

        Wish wish = new Wish();
        wish.setWareId(id);
        wish.setUserId(userId);
        Collection<StoredExchange> exchange = parseVertex(findExchange(wish,1));
        if(exchange==null) {
            response.setStatus("NO_EXCHANGE");
        }
        else{
            exchangeRepository.addExchange(exchange);
            response.setStatus("OK");
            response.setData(buildExchange(exchange));
        }

        return response;

    }
///api/exchanges/find-chain?userId=uI&id=wI
    @GetMapping("/api/exchanges/find-chain") public Response getComplexExchange(@RequestParam String userId,
                                                                                            @RequestParam String id){
        Response response = new Response();

        Wish wish = new Wish();
        wish.setUserId(userId);
        wish.setWareId(id);

        Collection<StoredExchange> exchange = parseVertex(findExchange(wish,5));
        if(exchange==null) {
            response.setStatus("NO_EXCHANGE");
        }
        else{
            exchangeRepository.addExchange(exchange);
            response.setStatus("OK");
            response.setData(buildExchange(exchange));
        }
        return response;
    }

    private Collection<StoredExchange> parseVertex(Vertex vertex){
        if(vertex.parent!=null) {
            Vector<StoredExchange> exchangeChain = new Vector<>();
            Vertex current = vertex.parent;
            String starterNeed=vertex.exchangeWare;
            do {
                StoredExchange exchange = new StoredExchange();
                exchange.setFirstUserId(current.userId);
                exchange.setFirstWareId(starterNeed);
                exchange.setSecondUserId(current.parent.userId);
                exchange.setSecondWareId(current.exchangeWare);
                exchangeChain.add(exchange);
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
            wareRepository.findWare(s.getFirstWareId()).setStatus("В обработке");
            e.setSecondWare(wareRepository.findWare(s.getSecondWareId()));
            wareRepository.findWare(s.getSecondWareId()).setStatus("В обработке");
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
            if(ware.getStatus().equals("Свободный")) {
                vertex.availableWaresId.add(ware.getId());
            }
        }
        return vertex;
    }
}
