import java.sql.SQLOutput;

public class SignUpState implements State{
  private boolean verifyID(String id){
    return id.length() < 20;
  }
  private boolean verifyPassword(String password) {
    return password.length() > 3 && password.length() < 20;
  }
  @Override
  public void execute() {
    for(int i = 0; i < 50; i++) System.out.println();
    System.out.flush();
    System.out.println("[SIGNUP]");
    System.out.print("ID: ");
    String id;
    do{
      id = Context.getNextLine().strip();
    }while(id.isEmpty());

    if(id.equalsIgnoreCase("signup") ||  ! verifyID(id)){
      System.out.println("ID not verified.");
      return;
    }

    System.out.print("PW: ");
    String password;
    do{
      password = Context.getNextLine().strip();
    }while(password.isEmpty());
    if(password.equalsIgnoreCase("signup") || ! verifyPassword(password)){
      System.out.println("PASSWORD not verified.");
      return;
    }

    System.out.print("name: ");
    String name;
    do{
      name = Context.getNextLine().strip();
    }while(name.isEmpty());

    if(Context.trySignup(id, name, password,
      (int)(Math.random()*71)+15,
      id + "@DEV.TEST")) {
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
      } else {
        System.out.println("Somehow you could not login");
        Context.setState(new LoginState());
      }
    }
  }

}
