package ftc.shift.springbootsample.api;


import ftc.shift.springbootsample.UserRepository;
import ftc.shift.springbootsample.WareRepository;
import static ftc.shift.springbootsample.UserRepository.*;
import ftc.shift.springbootsample.models.User;
import ftc.shift.springbootsample.models.Ware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin

public class WareController {
  @Autowired
  private WareRepository wareRepository;

  @PostMapping("/api/new_ware/{id}") public Ware addWare(@RequestBody Ware ware,
                                                        @PathVariable String id) {

      return wareRepository.addWare(ware, id);
  }

  @PutMapping("/api/change_ware/{id}") public Ware changeWare(@PathVariable String id,
                                                              @RequestParam(value = "category", defaultValue = "null") String category,
                                                              @RequestParam(value = "name", defaultValue = "null") String name,
                                                              @RequestParam(value = "description", defaultValue = "null") String description,
                                                              @RequestParam(value = "exploitation", defaultValue = "null") String exploitation) {
    Ware ware = wareRepository.findWare(id);
    if (ware == null)
        return null; //no ware with this ID

      if (!category.equals("null"))
          ware.setCategory(category);

      if (!name.equals("null"))
          ware.setName(name);

      if (!description.equals("null"))
          ware.setDescription(description);

      if (!exploitation.equals("null"))
          ware.setExploitation(exploitation);
      
      return ware;
  }

}
