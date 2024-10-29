package Practice;

import java.sql.*;

public class Connection {
  public static void main(String[] args) {
    java.sql.Connection conn = null;
    try{
      Class.forName("oracle.jdbc.OracleDriver");
      conn = DriverManager.getConnection(
        "jdbc:oracle:thin:@localhost:1521/xe",
        "java",
        "oracle"
      );


      /* Insert */
      /*
      * TODO: refactor connection to diff static library
      * TODO: verify field name at first connection?
      * */
//      String sql =
//        "INSERT INTO users (userid, username, userpassword, userage, useremail)" +
//        "VALUES (?, ?, ?, ?, ?)";
//
//      int rows = -1;
//      try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
//        preparedStatement.setString(1, "winter");
//        preparedStatement.setString(2, "Winston Terrain");
//        preparedStatement.setString(3, "12345");
//        preparedStatement.setInt(4, 25);
//        preparedStatement.setString(5, "winter@company.com");
//
//        // execute: ReadOnly
//        // executeUpdate: ManTable
//        rows = preparedStatement.executeUpdate();
//      };
//      System.out.println(rows);

//      String sql =
//        "INSERT INTO boards (bno, btitle, bcontent, bwriter, bdate, bfilename, bfiledata)" +
//                    "VALUES (SEQ_BNO.NEXTVAL, ?, ?, ?, SYSDATE, ?, ?)";
//      try(PreparedStatement preparedStatement = conn.prepareStatement(sql, new String[] {"bno"})){
//        preparedStatement.setString(1, "Snowflake");
//        preparedStatement.setString(2, "a single snowflake");
//        preparedStatement.setString(3, "winter");
//        preparedStatement.setString(4, "Snowflake.jpg");
//        preparedStatement.setBlob(5, Connection.class.getResourceAsStream("Snowflake.jpg"));
//          // In general, not save file directly, but only path to.
//          // use AWS S3?
//        // execute: ReadOnly
//        // executeUpdate: ManTable
//        int rows = preparedStatement.executeUpdate();
//        System.out.println(rows);
//        if(rows == 1){
//          try(ResultSet resultSet = preparedStatement.getGeneratedKeys()){
//            if(resultSet.next()){
//              int bno = resultSet.getInt(1);
//              System.out.println(bno);
//            }
//          }
//        }
//      }


      // update: follow conventions of sql
//      String sql = new StringBuilder()
//        .append("Update boards SET ")
//        .append("btitle=?,")
//        .append("bcontent=?,")
//        .append("bfilename=?,")
//        .append("bfiledata=? ")
//        .append("where bno=?")
//        .toString();
//      try(PreparedStatement preparedStatement = conn.prepareStatement(sql, new String[] {"bno"})){
//        preparedStatement.setString(1, "Snowflake");
//        preparedStatement.setString(2, "Image deleted due to copyright");
//        preparedStatement.setNull(3, Types.VARCHAR);
//        preparedStatement.setNull(4, Types.VARCHAR);
//        preparedStatement.setInt(5,1);
//        int rows = preparedStatement.executeUpdate();
//        System.out.println(rows);
//      }

      String sql = "delete from boards";
      try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
        preparedStatement.executeUpdate();
      }
    } catch (ClassNotFoundException | SQLException e) {
      System.out.println(e.getMessage());
    } finally {
      if(conn != null){
        try{
          conn.close();
          System.out.println("Disconnect");
        } catch(SQLException e){}
      }
    }
  }
}
