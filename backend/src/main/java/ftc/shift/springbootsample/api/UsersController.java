package ftc.shift.springbootsample.api;


import ftc.shift.springbootsample.UserRepository;
import ftc.shift.springbootsample.models.User;
import ftc.shift.springbootsample.models.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin
public class UsersController {

  @Autowired
  private UserRepository userRepository;


  @GetMapping("/api/register") public @ResponseBody User register(@RequestParam("email") String email,
                                                              @RequestParam("password") String password,
                                                              HttpServletResponse response){
    if (userRepository.find_user(email) == null){

      User new_user = userRepository.add_user(email, password);

      String cookie_value = RandomString.randomAlphaNumeric(20);
      response.addCookie(new Cookie("sid", cookie_value));

      userRepository.add_cookie(cookie_value, new_user.getId());
      return new_user;
    }
    else
      return null; //there is already user with this email
  }

//  @GetMapping("/api/login") public @ResponseBody String login(@RequestParam("email") String email,
//                                                              @RequestParam("password") String password,
//                                                              HttpServletResponse response){
//    response.addCookie(new Cookie("sid", "ololo"));
//    return "OK";
//  }



}