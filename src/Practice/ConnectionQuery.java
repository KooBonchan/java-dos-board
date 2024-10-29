package Practice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.sql.*;
import java.util.concurrent.Callable;

public class ConnectionQuery {

  public static void main(String[] args) {
    java.sql.Connection connection = null;
    try{
      Class.forName("oracle.jdbc.OracleDriver");
      connection = DriverManager.getConnection(
        "jdbc:oracle:thin:@localhost:1521/xe",
        "java",
        "oracle"
      );

//      String query =
//        "SELECT userid, username, userpassword, userage, useremail " +
//          "from users " +
//          "where userid=?";
//      try(PreparedStatement preparedStatement = connection.prepareStatement(query)){ //getter
//        preparedStatement.setString(1,"winter");
//        try(ResultSet resultSet = preparedStatement.executeQuery()){
//          if(resultSet.next()){
//            User user = new User.UserBuilder()
//              .id(resultSet.getString("userid"))
//              .name(resultSet.getString("username"))
//              .password(resultSet.getString("userpassword"))
//              .age(resultSet.getInt("userage"))
//              .email(resultSet.getString("useremail"))
//              .build();
//            System.out.println(user);
//          }
//        }
//      }

//      String query = "{call user_create(?, ?, ?, ?, ?, ?)}";
//      try(CallableStatement callableStatement = connection.prepareCall(query)){
//        callableStatement.setString(1, "summer");
//        callableStatement.setString(2, "Summit Erwin");
//        callableStatement.setString(3, "it wins");
//        callableStatement.setInt(4, 31);
//        callableStatement.setString(5, "summer@develop.erwin");
//        callableStatement.registerOutParameter(6,Types.INTEGER);
//        callableStatement.execute();
//        int rows = callableStatement.getInt(6);
//        System.out.println(rows);
//      }

      String query = "{? = call user_login(?, ?)}";
      try(CallableStatement callableStatement = connection.prepareCall(query)){
        // sql injection prevention --> data is separated from query
        // query precompiled -- plan generated once -- multi-execute in a single try block

        callableStatement.registerOutParameter(1, Types.INTEGER);
        callableStatement.setString(2, "summer");
        callableStatement.setString(3, "it wins");

        callableStatement.execute();
        int result = callableStatement.getInt(1);

        System.out.println(
          switch(result){ //extended switch - use only if typed receiver exists
            case 0 -> "Login Success";
            case 1 -> "Wrong Password";
            default -> "No such ID";
          }
        );
      }

    } catch (ClassNotFoundException | SQLException e) {
      throw new RuntimeException(e);
    } finally {
      if(connection != null){
        try{
          connection.close();
        } catch (SQLException e) {}
      }
    }
  }
}
