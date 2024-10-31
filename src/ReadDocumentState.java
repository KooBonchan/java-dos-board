import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReadDocumentState implements State{
  private Board board;
  public ReadDocumentState(Board board) {
    System.out.flush();
    this.board = board;
  }
  private void printMenu(){
    System.out.printf(
      "Menu: 3.%-6s | 4.%-6s\n",
      "Delete", "Exit"
    );
    System.out.print("Command: ");
  }
  private void writerFallback(){
    printMenu();
    String commandLine = Context.getNextLine();
    try{
      int command = Integer.parseInt(commandLine);
      switch(command) {
        case 3:
          System.out.println("(Enter C to cancel)");
          System.out.println("Enter anything to proceed.");
          String additionalInput = Context.getNextLine();
          if(additionalInput.equalsIgnoreCase("c")){ return;}
          if(additionalInput.equals("1")){
            String query = "delete from boards where bno=?";
            java.sql.Connection connection = ConnectionManager.getInstance().getConnection();
            try(
                PreparedStatement preparedStatement = connection.prepareStatement(query)){
              preparedStatement.setInt(1, board.getNo());
              preparedStatement.executeUpdate();
              System.out.flush();
              System.out.println("Succeeded deletion");
              Context.setState(new HomeState());
            } catch(SQLException ignored){
              System.out.println("Failed to delete your document. Retry.");
            }
          }
          break;
        case 4:
          Context.setState(new HomeState());
          break;
      }
    } catch (NumberFormatException e) {
      System.out.println("Not allowed Command");
      printMenu();
    }
  }

  @Override
  public void execute() {
    String query = "" +
      "SELECT bcontent from boards WHERE bno=?";
    java.sql.Connection connection = ConnectionManager.getInstance().getConnection();
    try(
      PreparedStatement preparedStatement = connection.prepareStatement(query)) {
      preparedStatement.setInt(1,board.getNo());
      try(ResultSet resultSet = preparedStatement.executeQuery()){
        board.setContent("");
        if(resultSet.next()){

          Clob clob = resultSet.getClob("bcontent");
          if(clob != null){
            board.setContent(clob.getSubString(1, (int)clob.length()));
          }
        }
        System.out.println("title: " + board.getTitle());
        System.out.println("writer: " + board.getWriter());
        System.out.println("date: " + board.getDate());
        System.out.println("-".repeat(40) + "content" + "-".repeat(40));
        System.out.println(board.getContent());
        System.out.println("-".repeat(87));
        if(board.getWriter().equals(Context.getUserId())){
          writerFallback();
        } else{
          System.out.println("Enter anything to exit...");
          Context.getNextLine();
          Context.setState(new HomeState());
        }
      }
    } catch (SQLException e){
      System.out.println("Article is somehow deleted or moved.");
      Context.setState(new HomeState());
    }
  }
}
