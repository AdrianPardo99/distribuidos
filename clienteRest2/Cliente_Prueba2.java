//aqui va lo del clienterest que dhay que ver que onda
//aqui va el menu:

import java.util.Scanner;
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
import java.lang.*;

public class Cliente_Prueba2 {
	//aqui debe ir lo que lee lo de la url
	//Haremos una clase De usuario aqui por que el servicio requiere que nosotrso mandemos los datos como JSON
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
    		return "\tEmail: "+this.email+"\n\tNombre: "+this.nombre+"\n\tApellidos: "+this.apellido_paterno+" "+this.apellido_materno+
         	"\n\tFecha de nacimiento: "+this.fecha_nacimiento+"\n\tTelefono: "+
         	this.telefono+"\n\tGenero: "+this.genero+"\n\tFoto: "+this.foto;
     	}
   }
	public static void Alta(){
		try{
			URL url = new URL("http://52.168.140.90:8080/Servicio/rest/ws/alta");
			HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
			GsonBuilder builder;
			Gson j=null;
			conexion.setDoOutput(true);
			conexion.setRequestMethod("POST");
			conexion.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			System.out.println("Ingresa tu nemail:");
			BufferedReader b7= new BufferedReader(new InputStreamReader(System.in));
			String email=URLEncoder.encode(b7.readLine(),"UTF-8");
			System.out.println("Ingresa tu nombre:");
			String nombre=URLEncoder.encode(b7.readLine(),"UTF-8");
			System.out.println("Ingresa tu Apellido Paterno:");
			String apellido_paterno=URLEncoder.encode(b7.readLine(),"UTF-8");
			System.out.println("Ingresa tu Apellido Materno:");
			String apellido_materno=URLEncoder.encode(b7.readLine(),"UTF-8");
			System.out.println("Ingresa tu fecha de nacimiento:");
			String fecha_nacimiento=URLEncoder.encode(b7.readLine(),"UTF-8");
			System.out.println("Ingresa tu telefono:");
			String telefono=URLEncoder.encode(b7.readLine(),"UTF-8");
			System.out.println("Ingresa tu Genero (F o M):");
			String genero=URLEncoder.encode(b7.readLine(),"UTF-8");
			Usuario u=new Usuario(email,nombre,apellido_paterno,
         	apellido_materno,fecha_nacimiento,telefono,genero,"null");
       		builder=new GsonBuilder();
       		j=builder.create();
      String parametros="usuario="+j.toJson(u).replace("{",URLEncoder.encode("{","UTF-8")).replace("}",URLEncoder.encode("}","UTF-8")).replace("\"",URLEncoder.encode("\"","UTF-8"));
			OutputStream os = conexion.getOutputStream();
			os.write(parametros.getBytes());
			os.flush();
			if (conexion.getResponseCode() != HttpURLConnection.HTTP_OK)
				throw new RuntimeException("Codigo de error HTTP: " + conexion.getResponseCode());
			System.out.println("Usuario ok");
			conexion.disconnect();
		}catch(Exception E){}
	}
	//funcion para borrar un solo usuario
	public static void BorrarUsuario(){
		try{
			URL url = new URL("http://52.168.140.90:8080/Servicio/rest/ws/borra");
			HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
			conexion.setDoOutput(true);
			// se utiliza el método HTTP POST (ver el método en la clase Servicio.java)
			conexion.setRequestMethod("POST");
			// indica que la petición estará codificada como URL
			conexion.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			// el método web "consulta" recibe como parámetro el email de un usuario, en este caso el email es "c@com"
			System.out.println("Ingrese el correo del usuario que quiera borrar:");
			BufferedReader a= new BufferedReader(new InputStreamReader(System.in));
			String email=a.readLine();
			String parametrosBU = "email=" + URLEncoder.encode(email,"UTF-8");
			OutputStream os = conexion.getOutputStream();
			os.write(parametrosBU.getBytes());
			os.flush();
			// se debe verificar si hubo error
			if (conexion.getResponseCode() != HttpURLConnection.HTTP_OK)
				throw new RuntimeException("Codigo de error HTTP: " + conexion.getResponseCode());
			BufferedReader br = new BufferedReader(new InputStreamReader((conexion.getInputStream())));
			String respuesta;
			// el método web regresa una string en formato JSON
			while ((respuesta = br.readLine()) != null) System.out.println(respuesta);
			conexion.disconnect();
		}catch(Exception E){}
	}
	//funcion para borrar a todos los usuarios de la base
	public static void BorrarUsuarios(){
		try{
			URL url = new URL("http://52.168.140.90:8080/Servicio/rest/ws/borrar_todo");
			HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
			conexion.setDoOutput(true);
			// se utiliza el método HTTP POST (ver el método en la clase Servicio.java)
			conexion.setRequestMethod("POST");
			// indica que la petición estará codificada como URL
			conexion.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			// el método web "consulta" recibe como parámetro el email de un usuario, en este caso el email es "c@com"
			BufferedReader a= new BufferedReader(new InputStreamReader(System.in));
			String email=a.readLine();
			String parametrosBUS = "email=" + URLEncoder.encode(email,"UTF-8");
			OutputStream os = conexion.getOutputStream();
			os.write(parametrosBUS.getBytes());
			os.flush();
			// se debe verificar si hubo error
			if (conexion.getResponseCode() != HttpURLConnection.HTTP_OK)
				throw new RuntimeException("Codigo de error HTTP: " + conexion.getResponseCode());
			BufferedReader br = new BufferedReader(new InputStreamReader((conexion.getInputStream())));
			String respuesta;
			// el método web regresa una string en formato JSON
			while ((respuesta = br.readLine()) != null) System.out.println(respuesta);
			conexion.disconnect();
		}catch(Exception E){}
	}
	public static void Consulta(){
		try{
			URL url = new URL("http://52.168.140.90:8080/Servicio/rest/ws/consulta");
			HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
			conexion.setDoOutput(true);
			// se utiliza el método HTTP POST (ver el método en la clase Servicio.java)
			conexion.setRequestMethod("POST");
			// indica que la petición estará codificada como URL
			conexion.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			// el método web "consulta" recibe como parámetro el email de un usuario, en este caso el email es "c@com
			System.out.println("Ingresa el correo para consultar");
			BufferedReader k= new BufferedReader(new InputStreamReader(System.in));
			String email=k.readLine();
			String parametrosC = "email=" + URLEncoder.encode(email,"UTF-8");
			OutputStream os = conexion.getOutputStream();
			os.write(parametrosC.getBytes());
			os.flush();
			// se debe verificar si hubo error
			if (conexion.getResponseCode() != HttpURLConnection.HTTP_OK)
				throw new RuntimeException("Codigo de error HTTP: " + conexion.getResponseCode());
			BufferedReader br = new BufferedReader(new InputStreamReader((conexion.getInputStream())));
			String respuesta;
			// el método web regresa una string en formato JSON
			while ((respuesta = br.readLine()) != null) System.out.println(respuesta);
			conexion.disconnect();
		}catch(Exception E){}
	}

	public static void main (String [ ] args){
		String opcionesConsole	 = "";
		do{
			System.out.println("Hola bienvenido que opcion deseas usar para comprobar el funcionamiento?");
			System.out.println("a.Atlta de usuario");
			System.out.println("b.Consulta de Usuario");
			System.out.println("c.Borrar Usuario");
			System.out.println("d.Borrar todos los Usuarios");
			System.out.println("e.Salir");
			System.out.println("Ingresa la Opcion:");
			Scanner opcionesConsole1 = new Scanner (System.in);
			opcionesConsole = opcionesConsole1.nextLine();
			switch(opcionesConsole.charAt(0)){
				case 'a':
				//aqui va el Alta de usuario pedir todos los datos
				Alta();
				break;
				case 'b':
				//aqui va el Consultar Usuario solo el email
				Consulta();
				break;
				case 'c':
				//Aqui va borrar un solo usuario (pedir el email)
				BorrarUsuario();
				break;
				case 'd':
				//Aqui va a ir borrar todos los usuarios
				BorrarUsuarios();
				break;
				case 'e':
				//aqui es salir del servicio
				break;
	 		}
		}while(opcionesConsole.charAt(0)!='e');
	}
}
