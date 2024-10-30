import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HomeState implements State{
  private List<Board> boards = new ArrayList<>();
  public HomeState (){
    getBoard();
    printBoard();
    printMenu();
  }

  private void getBoard(){
    String selectBoard = "" +
      "SELECT bno, btitle, bwriter, bdate " +
      "FROM boards";
    java.sql.Connection connection = ConnectionManager.getInstance().getConnection();
    try(PreparedStatement preparedStatement = connection.prepareStatement(selectBoard);
        ResultSet resultSet = preparedStatement.executeQuery()){

      while(resultSet.next()){
        Board board = new Board.BoardBuilder()
          .no(resultSet.getInt("bno"))
          .title(resultSet.getString("btitle"))
          .writer(resultSet.getString("bwriter"))
          .date(resultSet.getDate("bdate"))
          .build();
        boards.add(board);
      }
    }catch(SQLException e){
      System.err.println("Error occurred reading board list: " + e.getMessage());
    }
  }

  private void printBoard(){
    System.out.println(Board.listFormat());
    for(Board board : boards){
      System.out.println(board.toListEntity());
    }
  }

  private void printMenu(){
    System.out.printf(
      "Menu: 1.%-6s | 2.%-6s | 3.%-10s | 4.%-10s\n",
      "Write", "Read", "Clear All", "Exit"
    );
    System.out.print("Command: ");
  }


  @Override
  public void execute() {
    String commandLine = Context.getScanner().nextLine();
    String additionalInput;

    try{
      int command = Integer.parseInt(commandLine);
      switch(command){
        case 1:
          Context.setState(new WriteDocumentState());
          break;
        case 2:
          //TODO
          System.out.println("Read a Document(Enter C to cancel)");
          System.out.print("Document id: ");
          additionalInput = Context.getScanner().nextLine();
          if(additionalInput.equalsIgnoreCase("c")){ return;}
          try{
            int id = Integer.parseInt(additionalInput);
            for(Board board : boards){
              if(board.getNo() == id){
                Context.setState(new ReadDocumentState(board));
                return;
              }
            }
            System.out.println("No such board with given document Id.");
          } catch (NumberFormatException e) {
            System.out.println("Operation canceled - Wrong input");
          }
          printBoard();
          printMenu();
          break;
        case 3:
          System.out.println("Warning: Deleting all data, Unrecoverable");
          System.out.println("Enter 1 to Delete all board (Enter C to cancel)");
          additionalInput = Context.getScanner().nextLine();
          if(additionalInput.equalsIgnoreCase("c")){ return;}
          if(additionalInput.equals("1")){
            String query = "delete from boards";
            java.sql.Connection connection = ConnectionManager.getInstance().getConnection();
            try(
                PreparedStatement preparedStatement = connection.prepareStatement(query)){
              preparedStatement.executeUpdate();
            } catch(SQLException ignored){}
            Context.setState(new HomeState());
          }
          break;
        case 4:
          Context.setProgramEnd();
          break;
        default:
          throw new NumberFormatException();
      }
    } catch (NumberFormatException e) {
      System.out.println("Not allowed Command");
    }
  }
}
