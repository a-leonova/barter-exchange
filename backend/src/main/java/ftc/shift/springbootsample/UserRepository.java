package ftc.shift.springbootsample;

import ftc.shift.springbootsample.models.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Сущность для доступа к данным пользователей (инкапсулирует логику хранения данных и доступа к ним)
 */
@Repository
public class UserRepository {

  //потокобезопасная переменная, которая "выдает" идентификаторы для сущностей
  private final AtomicLong sequence = new AtomicLong(1);
  //хранилище пользователей (используем вместо базы данных)
  private Map<String, User> users = new HashMap<>();
  private Map<String, String> cookies = new HashMap<>();

  public UserRepository(){

  }

  public User find_user(String email){

    for (Map.Entry<String, User> entry : users.entrySet()) {
      if (entry.getValue().getEmail().equals(email))
        return entry.getValue();
    }
      return null;
  }

  public User add_user(String email, String hashed_password){
    User new_user = new User(email, hashed_password, String.valueOf(sequence.getAndIncrement()));
    users.put(new_user.getId(), new_user);
    return new_user;
  }

  public void add_cookie(String cookie_value, String id){
    cookies.put(cookie_value, id);
  }

}
