import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReadDocumentState implements State{
  private Board board;
  public ReadDocumentState(Board board) {
    System.out.flush();
    this.board = board;
  }

  private void writerFallback(){
    System.out.printf(
      "Menu: 1.%-6s | 2.%-6s\n",
      "Delete", "Exit"
    );
    System.out.print("Command: ");
    String commandLine = Context.getScanner().nextLine();
    try{
      int command = Integer.parseInt(commandLine);
      switch(command) {
        case 1:
          System.out.println("Warning: Deleting all data, Unrecoverable");
          System.out.println("Enter 1 to Delete all board (Enter C to cancel)");
          String additionalInput = Context.getScanner().nextLine();
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
        case 2:
          Context.setState(new HomeState());
          break;
      }
    } catch (NumberFormatException e) {
      System.out.println("Not allowed Command");
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
        if(resultSet.next()){
          board.setContent(resultSet.getClob("bcontent").toString());
          System.out.println("title: " + board.getTitle());
          System.out.println("writer: " + board.getWriter());
          System.out.println("date: " + board.getDate());
          System.out.println("content" + "-".repeat(75));
          System.out.println(board.getContent());
        }
        if(board.getWriter().equals(Context.getUserId())){
          writerFallback();
        } else{
          System.out.println("Enter anything to exit...");
          Context.getScanner().nextLine();
          Context.setState(new HomeState());
        }
      }
    } catch (SQLException e){
      System.out.println("Article is somehow deleted or moved.");
      Context.setState(new HomeState());
    }
  }
}
