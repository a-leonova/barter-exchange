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

  //запрос в виде "GET/api/login?email=my_email&password=my_password
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
  //запрос в виде "POST/api/register?email=my_email&password=my_password
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

  //Request "GET/api/user/{id}" - when you need information about another user
  @GetMapping("/api/user/{id}") public @ResponseBody User getAnotherUserInfo(@PathVariable String id){
    return userRepository.getUserById(id); //if null returned - there is no user with this ID
  }

  //Request "PUT/api/user/change?name=newName&email=newEmail....
  //some fields can be unchanged, so they won't be in request
  //but backend will check it
  @PutMapping("/api/user/change") public @ResponseBody User changeUser(@CookieValue(value = "sid") String sidCookie,
                                                                       @RequestParam(value = "email", defaultValue = "null") String email,
                                                                       @RequestParam(value = "password", defaultValue = "null") String password,
                                                                       @RequestParam(value = "name", defaultValue = "null") String name,
                                                                       @RequestParam(value = "city", defaultValue = "null") String city,
                                                                       @RequestParam(value = "number", defaultValue = "null") String number,
                                                                       @RequestParam(value = "pageInSocialNetwork", defaultValue = "null") String pageInSocialNetwork) {

    User user = userRepository.getUserByCookie(sidCookie);
    if (user == null)
      return null; //no user with this cookie. session was finished

    if (!email.equals("null"))
        user.setEmail(email);

    if (!password.equals("null"))
      user.setPassword(encoder.encode(password));
    if (!name.equals("null"))
      user.setName(name);
    if (!city.equals("null"))
      user.setCity(city);
    if (!number.equals("null"))
      user.setNumber(number);
    if (!pageInSocialNetwork.equals("null"))
      user.setPage_in_social_network(pageInSocialNetwork);
    return user;
  }

//  public boolean checkCookieId (String cookie, String id){
//    if (!userRepository.getUserByCookie(cookie).getId().equals(id))
//      return false;
//    else
//      return true;
//  }


}