public class Connector {
  private static final Connector instance = new Connector();
  private Connector(){}

  public Connector getInstance(){
    return instance;
  }
}
