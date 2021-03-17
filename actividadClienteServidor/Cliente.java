import java.net.Socket;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.nio.ByteBuffer;

class Cliente{
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
    Socket conexion = new Socket("0.0.0.0",50000);

    DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
    DataInputStream entrada = new DataInputStream(conexion.getInputStream());

    // enva un entero de 32 bits
    salida.writeInt(123);

    // envia un numero punto flotante
    salida.writeDouble(1234567890.1234567890);

    // envia una cadena
    salida.write("hola".getBytes());

    // recibe una cadena
    byte[] buffer = new byte[4];
    read(entrada,buffer,0,4);
    System.out.println(new String(buffer,"UTF-8"));

    // envia 5 numeros punto flotante
    timeI=System.currentTimeMillis();
    ByteBuffer b = ByteBuffer.allocate(t*8);
    for(int i=0;i<t;i++)
      b.putDouble(Double.parseDouble(i+1+".0"));
    byte[] a = b.array();
    salida.write(a);
    timeO=System.currentTimeMillis();
    System.out.println("El tiempo que tomo en enviar los primeros 10000 doubles con ByteBuffer fue: "+(timeO-timeI)+" ms");

    timeI=System.currentTimeMillis();
    for(int i=0;i<t;i++){
      salida.writeDouble(Double.parseDouble(i+1+".0"));
    }
    timeO=System.currentTimeMillis();
    System.out.println("El tiempo que tomo en enviar los primeros 10000 doubles fue: "+(timeO-timeI)+" ms");

    salida.close();
    entrada.close();
    conexion.close();
  }
}
