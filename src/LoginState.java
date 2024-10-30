public class LoginState implements State{
  private String id = null;

  LoginState(){
    getIDInput();
  }

  void getIDInput(){
    System.out.print("ID: ");
  }

  @Override
  public void execute() {

  }
}
