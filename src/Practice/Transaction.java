package Practice;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Transaction {
  public static void main(String[] args) {
    try(java.sql.Connection connection = DriverManager.getConnection(
        "jdbc:oracle:thin:@localhost:1521/xe",
        "java",
        "oracle")) {

      /**********/
      connection.setAutoCommit(false);
      String withdraw = "" +
        "UPDATE accounts SET " +
        "balance=balance-? " +
        "WHERE ano=?";
      String deposit = "" +
        "UPDAte accounts SET " +
        "balance=balance+? " +
        "WHERE ano=?";

      try(PreparedStatement withdrawStatement = connection.prepareStatement(withdraw);
          PreparedStatement depositStatement = connection.prepareStatement(deposit)){
        withdrawStatement.setInt(1,1000);
        withdrawStatement.setString(2,"333-333-3333");

        depositStatement.setInt(1,1001);
        depositStatement.setString(2, "111-111-1111");

        withdrawStatement.executeUpdate();
        depositStatement.executeUpdate();
        connection.commit();
      }catch (SQLException e){
        connection.rollback();
        System.out.println("Rollback: Somewhat error occurred: " + e.getMessage());
      }finally{
        connection.setAutoCommit(true);
      }

      String query = "Select * from accounts";
      try(PreparedStatement read = connection.prepareStatement(query);
          ResultSet resultSet = read.executeQuery();){
        while (resultSet.next()) {
          System.out.println(resultSet.getString(1));
          System.out.println(resultSet.getString(2));
          System.out.println(resultSet.getLong(3));
        }

      }


      /**********/


    } catch (SQLException e) {

      throw new RuntimeException(e);
    }
  }
}
