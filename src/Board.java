import lombok.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Board {
  private int no;
  private String title;
  private String content;
  private String writerID;
  private Date date;

  @Builder
  public Board(int no, String title, String content, String writerID, Date date) {
    this.no = no;
    this.title = title;
    this.content = content;
    this.writerID = writerID;
    this.date = date;
  }

  /*
   *
   * no filename / filedata
   * no image on the dos board
   */



  private static String abbreviation(String string, int length){
    if(length < 3) return "...";
      // length should be longer than 3:
    if(string.length() > length){
      return string.substring(0,string.length()-4).concat("...");
    }
    return string;
  }

  public static String listFormat(){
    return
      new StringBuilder()
        .append("[Posts]\n")
        .append("-".repeat(75))
        .append(String.format("\n%-4s | %-12s | %-10s | %-40s\n",
          "No.", "Writer", "Date", "Title"))
        .append("-".repeat(75))
        .toString();
  }

  public String toListEntity(){
    return String.format(
      "%04d | %-12s | %-10s | %-40s\n",
      //total length = 75
      no%10000,
      abbreviation(writerID,12),
      new SimpleDateFormat("yyyy-MM-dd").format(date),
      abbreviation(title,40)
    );
  }
}
