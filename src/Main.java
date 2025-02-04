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

    while(true){
      Context.executeState();
      // if it needs to exit program, each state may set program end state.
      if(Context.programEndFlag()){
        break;
      }
    }
    ConnectionManager.getInstance().closeConnection();
  }
}
