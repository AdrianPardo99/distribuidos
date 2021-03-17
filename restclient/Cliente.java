import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.Timestamp;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.io.OutputStream;

/*
 * @author Adrian Gonzalez Pardo
 **/

public class Cliente{
  public static void clear_print(){
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }
  public static void consumo_rest_server(int peticion,String dir_url){
    URL url;
    HttpURLConnection conexion;
    OutputStream os;
    String email=null,nombre,apellido_paterno,
      apellido_materno,fecha_nacimiento,telefono,genero,parametros;
    try{
      url=new URL(dir_url+((peticion==0)?("alta"):((peticion==1)?("consulta"):
        ((peticion==2)?("borra"):("borrar_usuarios")))));
      conexion=(HttpURLConnection) url.openConnection();
      conexion.setDoOutput(true);
      conexion.setRequestMethod("POST");
      conexion.setRequestProperty("Content-Type",
        "application/x-www-form-urlencoded");
      Usuario u=null;
      GsonBuilder builder;
      Gson j=null;
      switch(peticion){
        case 0:case 1:case 2:
          System.out.print("Ingresa el email: ");
          email=URLEncoder.encode(Teclado.readString(),"UTF-8");
          if(peticion==0){
            System.out.print("Ingresa tu nombre: ");
            nombre=URLEncoder.encode(Teclado.readString(),"UTF-8");
            System.out.print("Ingresa tu apellido paterno: ");
            apellido_paterno=URLEncoder.encode(Teclado.readString(),"UTF-8");
            System.out.print("Ingresa tu apellido materno: ");
            apellido_materno=URLEncoder.encode(Teclado.readString(),"UTF-8");
            System.out.print("Ingresa tu fecha de nacimiento (aaaa/mm/dd): ");
            fecha_nacimiento=URLEncoder.encode(Teclado.readString(),"UTF-8");
            System.out.print("Ingresa tu telefono: ");
            telefono=URLEncoder.encode(Teclado.readString(),"UTF-8");
            System.out.print("Ingresa tu genero: ");
            genero=Teclado.readString();
            u=new Usuario(email,nombre,apellido_paterno,
              apellido_materno,fecha_nacimiento,telefono,genero,"null");
            builder=new GsonBuilder();
            j=builder.create();
          }
        break;
      }
      parametros=peticion==0?("usuario="+j.toJson(u).replace("{",
        URLEncoder.encode("{","UTF-8")).replace("}",
        URLEncoder.encode("}","UTF-8")).replace("\"",
        URLEncoder.encode("\"","UTF-8"))):(peticion==1||peticion==2)?
        "email="+email:
        "borrar_usuarios=borrar_usuarios";

      os=conexion.getOutputStream();
      os.write(parametros.getBytes());
      os.flush();
      clear_print();
      String respuesta;
      if (conexion.getResponseCode()!=HttpURLConnection.HTTP_OK){
        if(peticion==1||peticion==2){
          System.out.println("Codigo de error HTTP: "+conexion.getResponseCode()+
            " es decir no se encontro email.");
        }
        throw new RuntimeException("Codigo de error HTTP: "+conexion.getResponseCode());
      }
      if(peticion==1){
        BufferedReader br=new BufferedReader(new InputStreamReader((conexion.getInputStream())));
        while ((respuesta=br.readLine())!=null){
          System.out.println("JSON:\n"+respuesta);
          Gson g=new Gson();
          u=g.fromJson(respuesta, Usuario.class);
          System.out.println("Datos del JSON a objeto:\n"+u.toString());
        }
      }else{
        System.out.println((peticion==0?"Ok alta correcta":
          peticion==2?"Ok eliminacion de usuario correcta":
          "Ok eliminacion de todos lo usuarios correcta"));
      }
    }catch(Exception e){
      System.err.println("Exception: "+e.getClass()+" with "+e);
    }
  }

  public static void main(String[] args){
    if(args.length<1){
      System.out.println("Usage:\n\tjava Cliente <IP>");
      System.exit(1);
    }
    String url_str="http://"+args[0]+":8080/Servicio/rest/ws/";
    boolean consumiendo=true;
    while(consumiendo){
      System.out.print("Bienvenido al Cliente REST, Escribe la letra que"+
        " deseas consumir del server REST?\n\ta. Alta usuario\n\tb. "+
        "Consulta usuario\n\tc. Borra usuario\n\td. Borra todos los"+
        " usuarios\n\te. Salir\nOpcion: ");
      String request=Teclado.readString();
      if(!request.isEmpty()){
        switch(request.charAt(0)){
          case 'a':case 'A':consumo_rest_server(0,url_str);continue;
          case 'b':case 'B':consumo_rest_server(1,url_str);continue;
          case 'c':case 'C':consumo_rest_server(2,url_str);continue;
          case 'd':case 'D':consumo_rest_server(3,url_str);continue;
          case 'e':case 'E':consumiendo=!consumiendo;continue;
          default:clear_print();
            System.out.println("Por favor ingresa una opcion valida");
        }
      }else{
        clear_print();
        System.out.println("Por favor no dejes vacio los campos");
      }
    }
  }
}
