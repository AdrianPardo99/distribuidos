import java.net.Socket;
import java.net.ServerSocket;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.nio.ByteBuffer;

class Servidor{
  // lee del DataInputStream todos los bytes requeridos
  static void read(DataInputStream f,byte[] b,int posicion,int longitud) throws Exception{
    while (longitud > 0){
      int n = f.read(b,posicion,longitud);
      posicion += n;
      longitud -= n;
    }
  }

  public static void main(String[] args) throws Exception{
    int t=10000;
    long timeI=0,timeO=0;
    ServerSocket servidor = new ServerSocket(50000);

    Socket conexion = servidor.accept();

    DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
    DataInputStream entrada = new DataInputStream(conexion.getInputStream());

    // recibe un entero de 32 bits
    int n = entrada.readInt();
    System.out.println(n);

    // recibe un numero punto flotante
    double x = entrada.readDouble();
    System.out.println(x);

    // recibe una cadena
    byte[] buffer = new byte[4];
    read(entrada,buffer,0,4);
    System.out.println(new String(buffer,"UTF-8"));

    // envia una cadena
    salida.write("HOLA".getBytes());

    // recibe 5 numeros punto flotante
    byte[] a = new byte[t*8];
    timeI=System.currentTimeMillis();
    read(entrada,a,0,t*8);
    ByteBuffer b = ByteBuffer.wrap(a);
    for (int i = 0; i < t; i++)
      System.out.print("");
    timeO=System.currentTimeMillis();
    System.out.println("El tiempo que tomo en recibir los primeros 10000 doubles con ByteBuffer fue: "+(timeO-timeI)+" ms");
    timeI=System.currentTimeMillis();
    for(int i=0;i<t;i++){
      x=entrada.readDouble();
    }
    timeO=System.currentTimeMillis();
    System.out.println("El tiempo que tomo en recibir los primeros 10000 doubles fue: "+(timeO-timeI)+" ms");
    salida.close();
    entrada.close();
    conexion.close();
    System.out.println(System.currentTimeMillis());
  }
}
