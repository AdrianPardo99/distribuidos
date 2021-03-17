import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.Timestamp;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class EjemploGSON{
  static class Coordenada{
    int x,y,z;
    Coordenada(int x,int y,int z){
      this.x=x;
      this.y=y;
      this.z=z;
    }
  }

  public static void main(String[] args)throws Exception{
    String json=new String(Files.readAllBytes(Paths.get("coordenadas_ehad.txt")));
    Gson j=new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").create();
    Coordenada[] v=(Coordenada[])j.fromJson(json,Coordenada[].class);
    int x=0,y=0,z=0;
    for (int i=0;i<v.length;i++){
      x+=v[i].x;
      y+=v[i].y;
      z+=v[i].z;
    }
    System.out.println("x="+(x)+"\ty="+(y)+"\tz="+(z));
  }
}
