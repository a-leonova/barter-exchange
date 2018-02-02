package ftc.shift.springbootsample.api;


import ftc.shift.springbootsample.UserRepository;
import ftc.shift.springbootsample.WareRepository;
import ftc.shift.springbootsample.models.User;
import ftc.shift.springbootsample.models.RandomString;
import ftc.shift.springbootsample.models.Ware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import javax.jws.soap.SOAPBinding;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;

@RestController
@CrossOrigin
public class UsersController {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private WareRepository wareRepository;

  private PasswordEncoder encoder = new BCryptPasswordEncoder();

  //запрос в виде "POST/api/register
  @PostMapping("/api/register") public @ResponseBody Response register(@RequestBody User newer){

    Response response = new Response();

    if (userRepository.findUser(newer.getEmail()) == null){

      User new_user = userRepository.addUser(newer.getEmail(), encoder.encode(newer.getPassword()));

      response.setData(new_user);
      response.setStatus("OK");
    }

    else{
      response.setStatus("SAME_USER");
    }

      return  response;
  }


  @PostMapping("/api/login") public @ResponseBody Response login(@RequestBody User guest){
    Response response = new Response();
    User user = userRepository.findUser(guest.getEmail());
    if (user == null){
      response.setStatus("USER_NOT_FOUND");

    }

    else if (!encoder.matches(guest.getPassword(), user.getPassword())){
      response.setStatus("BAD_PASSWORD");
    }

    else{
      response.setStatus("OK");
      response.setData(user);
    }

    return  response;

  }

//  //Request "GET/api/user" with the aid of cookie
//  //backend will know user and will check if he is in the system
//  @GetMapping("/api/user/id") public @ResponseBody User getUserInfo(@CookieValue("sid") String sidCookie){
//    return userRepository.getUserByCookie(sidCookie); //if null returned - session was finished, cookie was deleted
//  }

  //Request "GET/api/user/{id}" - when you need information about another user
  @GetMapping("/api/user/{id}") public @ResponseBody User getUserInfo(@PathVariable String id){
    return userRepository.getUserById(id); //if null returned - there is no user with this ID
  }

  //Request "PUT/api/user/{id} into body give me a user
  @PutMapping("/api/user/{id}") public @ResponseBody User changeUser(@PathVariable String id, @RequestBody User changed_user) {

    if (!changed_user.getPassword().equals(""))
      changed_user.setPassword(encoder.encode(changed_user.getPassword()));

    return userRepository.getUserById(id).change(changed_user);

  }

  @GetMapping("/api/user/ware/{id}") public @ResponseBody Collection<Ware> getUserWare(@PathVariable String id){

    Collection<Ware> allWare = wareRepository.getAll();
    ArrayList<Ware> userWare = new ArrayList<>();

    for (Ware it : allWare){
      if (it.getOwnerId().equals(id))
        userWare.add(it);
    }

    return  userWare;
  }
}