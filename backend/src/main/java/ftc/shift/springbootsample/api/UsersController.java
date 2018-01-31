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

//запрос в виде "GET/api/register?email=my_email&password=my_password
  @GetMapping("/api/register") public @ResponseBody User register(@RequestParam("email") String email,
                                                              @RequestParam("password") String password,
                                                              HttpServletResponse response){
    if (userRepository.find_user(email) == null){

      User new_user = userRepository.add_user(email, encoder.encode(password));

      String cookie_value = RandomString.randomAlphaNumeric(20);
      response.addCookie(new Cookie("sid", cookie_value));

      userRepository.add_cookie(cookie_value, new_user.getId());
      return new_user;
    }
    else
      return null; //there is already user with this email
  }
  //запрос в виде "GET/api/login?email=my_email&password=my_password
  @GetMapping("/api/login") public @ResponseBody User login(@RequestParam("email") String email,
                                                              @RequestParam("password") String password,
                                                              HttpServletResponse response){

    User user = userRepository.find_user(email);
    if (user == null)
      return null; //no user


    if (!encoder.matches(password, user.getPassword()))
        return null; //mismatching password

      String cookie_value = RandomString.randomAlphaNumeric(20);
      response.addCookie(new Cookie("sid", cookie_value));
      return user;
  }



}