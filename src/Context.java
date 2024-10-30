import lombok.Getter;

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


  @Getter
  private final static Scanner scanner = new Scanner(System.in);

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
}
