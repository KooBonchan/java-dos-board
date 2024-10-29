import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
  @Getter
  private static ConnectionManager instance = new ConnectionManager();
  @Getter
  private Connection connection;

  private static final String URL = "jdbc:oracle:thin:@localhost:1521/xe";
  private static final String USER = "java";
  private static final String PW = "oracle";

  static{
    try{
      instance.connection = DriverManager.getConnection(URL, USER, PW);
    } catch (SQLException e) {
      throw new RuntimeException("Failed to create a database connection",e);
    }

    Runtime.getRuntime().addShutdownHook(
      new Thread(()->{instance.closeConnection();})
    );
  }

  private ConnectionManager(){}

  public void closeConnection(){
    try{
      if(connection != null && !connection.isClosed()){
        connection.close();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
