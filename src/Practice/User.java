package Practice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
class User{
  private String id;
  private String name;
  private String password;
  private int age;
  private String email;
}