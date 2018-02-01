package ftc.shift.springbootsample.api;


import ftc.shift.springbootsample.UserRepository;
import ftc.shift.springbootsample.models.User;
import ftc.shift.springbootsample.models.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin
public class UsersController {

  @Autowired
  private UserRepository userRepository;
  private PasswordEncoder encoder = new BCryptPasswordEncoder();

//запрос в виде "POST/api/register?email=my_email&password=my_password
  @PostMapping("/api/register") public @ResponseBody User register(@RequestParam("email") String email,
                                                              @RequestParam("password") String password,
                                                              HttpServletResponse response){
    if (userRepository.findUser(email) == null){

      User new_user = userRepository.addUser(email, encoder.encode(password));

      String cookie_value = RandomString.randomAlphaNumeric(20);
      response.addCookie(new Cookie("sid", cookie_value));

      userRepository.addCookie(cookie_value, new_user.getId());
      return new_user;
    }
    else
      return null; //there is already user with this email
  }
  //запрос в виде "GET/api/login?email=my_email&password=my_password
  @GetMapping("/api/login") public @ResponseBody User login(@RequestParam("email") String email,
                                                              @RequestParam("password") String password,
                                                              HttpServletResponse response){

    User user = userRepository.findUser(email);
    if (user == null)
      return null; //no user


    if (!encoder.matches(password, user.getPassword()))
        return null; //mismatching password

      String cookie_value = RandomString.randomAlphaNumeric(20);
      response.addCookie(new Cookie("sid", cookie_value));
      return user;
  }

  //Request "GET/api/user" with the aid of cookie
  //backend will know user and will check if he is in the system
  @GetMapping("/api/user") public @ResponseBody User getUserInfo(@CookieValue("sid") String sidCookie){
    return userRepository.getUserByCookie(sidCookie); //if null returned - session was finished, cookie was deleted
  }

}