import java.util.ArrayList;
import java.util.List;

public class HomeState implements State{
  private List<Board> board = new ArrayList<>();
  public HomeState (){
    getBoard();
  }

  private void getBoard(){

  }


  @Override
  public void execute(String commandLine) {
    try{
      int command = Integer.parseInt(commandLine);
      switch(command){
        case 1:
          break;
        case 2:
          break;
        case 3:
          break;
        case 4:
          Context.setState(null);
          break;
        default:
          throw new NumberFormatException();
      }


    } catch (NumberFormatException e) {
      System.out.println("Not allowed Command");
    }
  }
}
