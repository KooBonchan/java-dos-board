import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    /*
     *
     * LOGIC
     *
     * CONNECT DB - ConnectionManager
     * LOGIN          |
     * Signup         |
     * HOME           | - state management by Context
     * WRITE DOCUMENT |
     * DISCONNECT DB - ConnectionManager
     */

    Context.setState(new LoginState());
    Scanner scanner = new Scanner(System.in);
    String line = null;
    while(true){
      Context.executeState(line);
      if(Context.programEndFlag()){
        break;
      }
      scanner.nextLine();
    }
    ConnectionManager.getInstance().closeConnection();
  }
}
