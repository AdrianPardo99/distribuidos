import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/*
 * @author Adrian Gonzalez Pardo
 **/

public class Teclado{
    public static String readString(){
      try{
        BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
        return b.readLine();
      }catch(IOException e){
        return "";
      }
    }
}
