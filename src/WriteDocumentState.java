import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WriteDocumentState implements State{
  public WriteDocumentState () {
    System.out.flush();
    System.out.println("Write Document");
    System.out.println("title: ");
  }
  private String title;
  private StringBuilder contentBuilder;

  @Override
  public void execute() {
    String commandLine = Context.getScanner().nextLine();
    commandLine = commandLine.strip();
    if(title == null){
      title = commandLine;
      System.out.println("Type empty line to end typing contents");
      System.out.print("content: ");
      return;
    }
    if(contentBuilder == null) contentBuilder = new StringBuilder();
    contentBuilder.append(commandLine);
    if(commandLine.isEmpty() && !contentBuilder.isEmpty()){
      System.out.println("Uploading content...");
      String query =
        "INSERT INTO boards (bno, btitle, bcontent, bwriter, bdate) " +
          "VALUES (SEQ_BNO.NEXTVAL, ?, ?, ?, SYSDATE)";
      java.sql.Connection connection = ConnectionManager.getInstance().getConnection();
      try(
        PreparedStatement preparedStatement = connection.prepareStatement(query)
        ){
        preparedStatement.setString(1, title);
        preparedStatement.setString(2, contentBuilder.toString());
        preparedStatement.setString(3, Context.getUserId());
        preparedStatement.executeUpdate();

      }catch (SQLException e){
        System.out.println("Could not upload your document. Sorry");
      }finally{
        Context.setState(new HomeState());
      }
    }
  }
}
