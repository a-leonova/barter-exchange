package ftc.shift.springbootsample.api;


import ftc.shift.springbootsample.UserRepository;
import ftc.shift.springbootsample.WareRepository;
import ftc.shift.springbootsample.models.User;
import ftc.shift.springbootsample.models.Ware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

@RestController
@CrossOrigin
public class WareController {

  @Autowired
  private WareRepository wareRepository;
  @Autowired
  private UserRepository userRepository;

  @PostMapping("/api/new_ware/{id}") public Ware addWare(@RequestBody Ware ware,
                                                        @PathVariable String id) {

      return wareRepository.addWare(ware, id);
  }

  @PutMapping("/api/change_ware/{id}") public Ware changeWare(@PathVariable String id, @RequestBody Ware changeWare) {
    Ware ware = wareRepository.findWare(id);
    if (ware == null)
        return null; //no ware with this ID


      return ware.change(changeWare);
 }

  //Request GET/api/filter?city=some_city&... (параметры запроса еще или отсутствуют)
  @GetMapping("api/ware/filter") public Collection<Ware> listWare(@RequestParam(value = "category", defaultValue = "null") String category,
                                                                  @RequestParam(value = "city", defaultValue = "null") String city,
                                                                  @RequestParam(value = "exploitation", defaultValue = "null") String exploitation) {
    Collection<Ware> filteredList = wareRepository.getAll();
    if (!category.equals("null")){
        filteredList = categoryFilter(category, filteredList);
    }
    if (!exploitation.equals("null")){
        filteredList = exploitationFilter(exploitation, filteredList);
    }
    if (!city.equals("null")){
        filteredList = cityFilter(city, filteredList);
    }
    return filteredList;

  }
//GET/api/ware/{id}
    @GetMapping("api/ware/{id}") public Ware getWare(@PathVariable String id){

      return wareRepository.findWare(id);

    }


    private Collection<Ware> categoryFilter(String category, Collection<Ware> unfilteredList){

        ArrayList<Ware> categoryWare = new ArrayList<>();

        for (Ware it : unfilteredList) {
           if (it.getCategory().equals(category))
               categoryWare.add(it);
        }
        return categoryWare;
    }

    private Collection<Ware> exploitationFilter(String exploitation, Collection<Ware> unfilteredList){

      ArrayList<Ware> exploitationWare = new ArrayList<>();

        for (Ware it : unfilteredList) {
            if (it.getExploitation().equals(exploitation))
                exploitationWare.add(it);
        }
        return exploitationWare;
    }

    private  Collection<Ware> cityFilter(String city, Collection<Ware> unfilteredList){

        ArrayList<Ware> cityWare = new ArrayList<>();

        for (Ware it : unfilteredList) {
            User user = userRepository.getUserById(it.getOwnerId());
            if (user.getCity().equals(city))
                cityWare.add(it);
        }
        return  cityWare;

    }


}

