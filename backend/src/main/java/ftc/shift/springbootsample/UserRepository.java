package ftc.shift.springbootsample;

import ftc.shift.springbootsample.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Сущность для доступа к данным пользователей (инкапсулирует логику хранения данных и доступа к ним)
 */
@Repository
public class UserRepository {


  private PasswordEncoder encoder = new BCryptPasswordEncoder();

  //потокобезопасная переменная, которая "выдает" идентификаторы для сущностей
  private final AtomicLong sequence = new AtomicLong(1);
  //хранилище пользователей (используем вместо базы данных)
  private Map<String, User> users = new HashMap<>();

  public UserRepository(){
    User user = new User("ololo", encoder.encode("ololo"), String.valueOf(sequence.getAndIncrement()),
            "Dasha");
    users.put(user.getId(), user);

    user = new User("ololosha", encoder.encode("ololosha"), String.valueOf(sequence.getAndIncrement()),
            "Nastya");
    users.put(user.getId(), user);

    user = new User("azaza", encoder.encode("azaza"), String.valueOf(sequence.getAndIncrement()),
            "Rita");
    users.put(user.getId(), user);

    user = new User("keki", encoder.encode("keki"), String.valueOf(sequence.getAndIncrement()),
            "Sasha");
    users.put(user.getId(), user);

    user = new User("Alina", encoder.encode("Alina"), String.valueOf(sequence.getAndIncrement()),
            "Alina");
    users.put(user.getId(), user);

    user = new User("Pasha", encoder.encode("Pasha"), String.valueOf(sequence.getAndIncrement()),
            "Pasha");
    users.put(user.getId(), user);

    user = new User("Nika", encoder.encode("Nika"), String.valueOf(sequence.getAndIncrement()),
            "Nika");
    users.put(user.getId(), user);

    user = new User("Tanya", encoder.encode("Tanya"), String.valueOf(sequence.getAndIncrement()),
            "Tanya");
    users.put(user.getId(), user);

  }

  public User findUser(String email){

    for (Map.Entry<String, User> entry : users.entrySet()) {
      if (entry.getValue().getEmail().equals(email))
        return entry.getValue();
    }
      return null;
  }

  public  User getUserById(String id){
    return users.get(id);
  }

  public User addUser(String email, String hashed_password){
    User new_user = new User(email, hashed_password, String.valueOf(sequence.getAndIncrement()));
    users.put(new_user.getId(), new_user);
    return new_user;
  }

  public Collection<User> getCityUsers(String cityName){
    Vector<User> filtered=new Vector<>();
    for(Map.Entry<String, User> entry :users.entrySet()){
      if(entry.getValue().getCity().equals(cityName))
        filtered.add(entry.getValue());
    }
    return filtered;

  }
}
