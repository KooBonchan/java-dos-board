public class LoginState implements State{
  private boolean verifyID(String id){
    return id.length() < 20;
  }

  @Override
  public void execute() {
    /*
    get input
    verify input
    try login
    if fail: login | signup menu
     */
    for(int i = 0; i < 50; i++) System.out.println();
    System.out.flush();
    System.out.println("(Type 'SIGNUP' to signup (case ignored)");
    System.out.print("ID: ");
    String id;
    do{
      id = Context.getNextLine().strip();
    }while(id.isEmpty());
    if(id.equalsIgnoreCase("signup")){
      Context.setState(new SignUpState());
      return;
    }
    if( ! verifyID(id)){
      System.out.println("ID not verified.");
      return;
    }

    System.out.print("PW: ");
    String password;
    do{
      password = Context.getNextLine().strip();
    }while(password.isEmpty());
    if(password.equalsIgnoreCase("signup")){
      Context.setState(new SignUpState());
      return;
    }

    if(Context.tryLogin(id, password)){
      // ANSI escape codes:
      // \033[H - Move the cursor to the top-left corner of the console
      // \033[2J - Clear the screen
      // Intellij RUN does not support such ANSI escape codes
//      System.out.print("\033[H\033[2J");
      for(int i = 0; i < 50; i++) System.out.println();
      System.out.flush();
      System.out.println("Welcome, " + id);
      Context.setState(new HomeState());
    }


  }
}
