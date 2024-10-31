import lombok.Builder;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Context {
  private static State currentState;
  // TODO
  // currently only one state available
  // might be better to use nav stack -- to keep home page article caching


  @Getter
  private static String userId = "DEVTEST";
  // TODO
  // remove default user id after login / signup.

  private final static Scanner scanner = new Scanner(System.in);
  public static String getNextLine(){
    return scanner.nextLine();
  }

  public static boolean programEndFlag(){
    return currentState == null;
  }
  public static void setProgramEnd(){
    currentState = null;
  }

  public static void setState(State state){
    if(state == null){
      throw new RuntimeException("State is null");
    }
    currentState = state;
  }

  public static void executeState(){
    if(currentState != null){
      currentState.execute();
    }
  }

  public static boolean tryLogin(String id, String password){
    Connection connection = ConnectionManager.getInstance().getConnection();
    String query = "" +
      "SELECT userid, username, userpassword, userage, useremail " +
      "FROM users " +
      "WHERE userid=? and userpassword=?";
    try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
      preparedStatement.setString(1,id);
      preparedStatement.setString(2,uglifyPassword(password));
      try(ResultSet resultSet = preparedStatement.executeQuery()){
        if(resultSet.next()){
          userId = id;
          return true;
        } else{
          return false;
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static boolean trySignup(String id, String name, String password, int age, String email){
    Connection connection = ConnectionManager.getInstance().getConnection();
    String query = "" +
      "INSERT INTO users " +
      "(userid, username, userpassword, userage, useremail) " +
      "VALUES (?, ?, ?, ?, ?)";
    try(
      PreparedStatement preparedStatement = connection.prepareStatement(query)
      ){
      preparedStatement.setString(1, id);
      preparedStatement.setString(2, name);
      preparedStatement.setString(3, password);
      preparedStatement.setInt(4, age);
      preparedStatement.setString(5, email);
      preparedStatement.executeUpdate();
      return true;
    }catch (SQLException e){
      System.out.println("ID Already exists");
      return false;
    }
  }

  private static String uglifyPassword(String password){
    return password;
  }
}
