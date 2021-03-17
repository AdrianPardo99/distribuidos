import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.Timestamp;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.io.OutputStream;
import java.io.IOException;

/*
 * @author Adrian Gonzalez Pardo
 **/

 public class Cliente{
   static class Usuario{
     String email,nombre,apellido_paterno,
      apellido_materno,fecha_nacimiento,telefono,genero,foto;
     Usuario(String email,String nombre,String apellido_paterno,
      String apellido_materno,String fecha_nacimiento,String telefono,
      String genero, String foto){
       this.email=email;
       this.nombre=nombre;
       this.apellido_paterno=apellido_paterno;
       this.apellido_materno=apellido_materno;
       this.fecha_nacimiento=fecha_nacimiento;
       this.telefono=telefono;
       this.genero=genero;
       this.foto=foto;
     }
     @Override
     public String toString(){
       return "\tEmail: "+this.email+"\n\tNombre: "+this.nombre+
         "\n\tApellidos: "+this.apellido_paterno+" "+this.apellido_materno+
         "\n\tFecha de nacimiento: "+this.fecha_nacimiento+"\n\tTelefono: "+
         this.telefono+"\n\tGenero: "+this.genero+"\n\tFoto: "+this.foto;
     }
   }
   public static String readString(){
     try{
       BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
       return b.readLine();
     }catch(IOException e){
       return "";
     }
   }

   public static void clear_print(){
     System.out.print("\033[H\033[2J");
     System.out.flush();
   }

   public static void baja_usuarios(String ip){
     try{
       /* Mover aqui el metodo de borrar todos los usuarios en la url */
       URL url = new URL("http://"+ip+":8080/Servicio/rest/ws/");
       HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
       conexion.setDoOutput(true);
       conexion.setRequestMethod("POST");
       conexion.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
       String parametros = "";
       OutputStream os = conexion.getOutputStream();
       os.write(parametros.getBytes());
       os.flush();
       if(conexion.getResponseCode()!=HttpURLConnection.HTTP_OK)
        throw new RuntimeException("Codigo de error HTTP: " + conexion.getResponseCode());
       System.out.println("Baja de usuarios correcta");
     }catch(Exception e){

     }
   }

   public static void baja(String ip){
     try{
       URL url = new URL("http://"+ip+":8080/Servicio/rest/ws/borra");
       HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
       conexion.setDoOutput(true);
       conexion.setRequestMethod("POST");
       conexion.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
       System.out.print("Escribe el email: ");
       String parametros = "email=" + URLEncoder.encode(readString(),"UTF-8");
       OutputStream os = conexion.getOutputStream();
       os.write(parametros.getBytes());
       os.flush();
       if(conexion.getResponseCode()!=HttpURLConnection.HTTP_OK)
        throw new RuntimeException("Codigo de error HTTP: " + conexion.getResponseCode());
       System.out.println("Baja de usuario correcta");
     }catch(Exception e){

     }
   }

   public static void alta(String ip){
     try{
       String email=null,nombre,apellido_paterno,
         apellido_materno,fecha_nacimiento,telefono,genero,parametros;
       URL url = new URL("http://"+ip+":8080/Servicio/rest/ws/alta");
       HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
       GsonBuilder builder;
       Gson j=null;
       conexion.setDoOutput(true);
       conexion.setRequestMethod("POST");
       conexion.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
       System.out.print("Escribe el email: ");
       email=URLEncoder.encode(readString(),"UTF-8");
       System.out.print("Ingresa tu nombre: ");
       nombre=URLEncoder.encode(readString(),"UTF-8");
       System.out.print("Ingresa tu apellido paterno: ");
       apellido_paterno=URLEncoder.encode(readString(),"UTF-8");
       System.out.print("Ingresa tu apellido materno: ");
       apellido_materno=URLEncoder.encode(readString(),"UTF-8");
       System.out.print("Ingresa tu fecha de nacimiento (aaaa/mm/dd): ");
       fecha_nacimiento=URLEncoder.encode(readString(),"UTF-8");
       System.out.print("Ingresa tu telefono: ");
       telefono=URLEncoder.encode(readString(),"UTF-8");
       System.out.print("Ingresa tu genero: ");
       genero=readString();
       Usuario u=new Usuario(email,nombre,apellido_paterno,
         apellido_materno,fecha_nacimiento,telefono,genero,"null");
       builder=new GsonBuilder();
       j=builder.create();
       parametros="usuario="+j.toJson(u).replace("{",
         URLEncoder.encode("{","UTF-8")).replace("}",
         URLEncoder.encode("}","UTF-8")).replace("\"",
         URLEncoder.encode("\"","UTF-8"));
       OutputStream os = conexion.getOutputStream();
       os.write(parametros.getBytes());
       os.flush();
       if(conexion.getResponseCode()!=HttpURLConnection.HTTP_OK)
       throw new RuntimeException("Codigo de error HTTP: " + conexion.getResponseCode());
       BufferedReader br = new BufferedReader(new InputStreamReader((conexion.getInputStream())));
       String respuesta;
       while ((respuesta = br.readLine()) != null) System.out.println(respuesta);
       System.out.println("Usuario ok");
       conexion.disconnect();
     }catch(Exception e){

     }
   }
   public static void consulta(String ip){
     try{
       URL url = new URL("http://"+ip+":8080/Servicio/rest/ws/consulta");
       HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
       conexion.setDoOutput(true);
       conexion.setRequestMethod("POST");
       conexion.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
       System.out.print("Escribe el email: ");
       String parametros = "email=" + URLEncoder.encode(readString(),"UTF-8");
       OutputStream os = conexion.getOutputStream();
       os.write(parametros.getBytes());
       os.flush();
       if(conexion.getResponseCode()!=HttpURLConnection.HTTP_OK)
        throw new RuntimeException("Codigo de error HTTP: " + conexion.getResponseCode());
       BufferedReader br = new BufferedReader(new InputStreamReader((conexion.getInputStream())));
       String respuesta;
       while ((respuesta = br.readLine()) != null){
         System.out.println("JSON:\n"+respuesta);
         Gson g=new Gson();
         Usuario u=g.fromJson(respuesta, Usuario.class);
         System.out.println("Datos del JSON a objeto:\n"+u.toString());
       }
       conexion.disconnect();
     }catch(Exception e){

     }
   }

   public static void main(String args[]){
     if(args.length<1){
       System.err.println("Error\n\tUsage: java -cp gsonfile.jar:. Cliente <IP>");
       System.exit(1);
     }
     boolean consumiendo=true;
     while(consumiendo){
       System.out.print("Bienvenido al Cliente REST, Escribe la letra que"+
         " deseas consumir del server REST?\n\ta. Alta usuario\n\tb. "+
         "Consulta usuario\n\tc. Borra usuario\n\td. Borra todos los"+
         " usuarios\n\te. Salir\nOpcion: ");
       String request=readString();
       if(!request.isEmpty()){
         switch(request.charAt(0)){
           case 'a':case 'A':alta(args[0]);continue;
           case 'b':case 'B':consulta(args[0]);continue;
           case 'c':case 'C':baja(args[0]);continue;
           case 'd':case 'D':;continue;
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
