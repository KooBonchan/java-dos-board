public class LoginState implements State{
  private String id = null;

  LoginState(){
    showDefault();
  }

  void showDefault(){
    System.out.print("ID: ");
  }

  @Override
  public void execute(String commandLine) {

  }
}
