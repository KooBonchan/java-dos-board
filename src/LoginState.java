public class LoginState implements State{
  private String id = null;



  @Override
  public void execute() {
    /*
    get input
    verify input
    try login
    if fail: login | signup menu
     */
    System.out.print("ID: ");
    String line;
    do{
      line = Context.getNextLine();
    }while(line.isEmpty());

    System.out.print("PW: ");
    do{
      line = Context.getNextLine();
    }while(line.isEmpty());



  }
}
