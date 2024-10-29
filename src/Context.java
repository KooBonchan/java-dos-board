public class Context {
  private static State currentState;

  public static boolean programEndFlag(){
    return currentState == null;
  }
  public static void setState(State state){
    currentState = state;
  }

  public static void executeState(String commandLine){
    if(currentState != null){
      currentState.execute(commandLine);
    }
  }
}
