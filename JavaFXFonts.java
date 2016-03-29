import java.util.List;
import javafx.scene.text.Font;

public class JavaFXFonts {

  public static void main(String[] args) {
    List<String> familiesList = Font.getFamilies();

    for ( String s : familiesList )
      System.out.println( s );
  }

}

