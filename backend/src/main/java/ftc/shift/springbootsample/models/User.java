package ftc.shift.springbootsample.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import  java.util.Vector;

/**
 * Модель пользователя
 */

@JsonIgnoreProperties(value = {"password"})
public class User {

  private String id;
  private String email;
  private String password;
  private String name;
  private String city;
  private Integer rate;
  private String number;
  private String page_in_social_network;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public Integer getRate() {
    return rate;
  }

  public void setRate(Integer rate) {
    this.rate = rate;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getPage_in_social_network() {
    return page_in_social_network;
  }

  public void setPage_in_social_network(String page_in_social_network) {
    this.page_in_social_network = page_in_social_network;
  }

  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  private String comments;

  public User(){

  }


  public User(String email, String hashed_password, String id, String name){

    this.email = email;
    this.password = hashed_password;
    this.name = name;
    this.city = "Новосибирск";
    this.comments = "";
    this.rate = 0;
    this.id = id;
    this.number = "88005553535";
    this.page_in_social_network = "vk.com";


  }

  public User(String email, String hashed_password, String id) {
    this.email = email;
    this.password = hashed_password;
    this.name = "Розовый гусь";
    this.city = "Новосибирск";
    this.comments = "";
    this.rate = 0;
    this.id = id;
    this.number = "88005553535";
    this.page_in_social_network = "vk.com";
  }

  public User change(User changed_user){

    this.name = changed_user.name;
    this.city = changed_user.city;
    this.comments = changed_user.comments;
    this.email = changed_user.email;
    this.number = changed_user.number;
    this.page_in_social_network = changed_user.page_in_social_network;


    if (!changed_user.password.equals(""))
      this.password = changed_user.password;

    if (!changed_user.rate.equals(""))
      this.rate = changed_user.rate;

    if (!changed_user.id.equals(""))
      this.id = changed_user.id;

    return this;

  }

}
