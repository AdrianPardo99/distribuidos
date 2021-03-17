import java.net.Socket;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.nio.ByteBuffer;

class Clientemod{
  static void read(DataInputStream f,byte[] b,int posicion,int longitud)
  throws Exception{
    while (longitud > 0){
      int n = f.read(b,posicion,longitud);
      posicion += n;
      longitud -= n;
    }
  }
  public static void main(String[] args) throws Exception{
    int t=10000;
    long timeI=0,timeO=0;
    Socket conexion = new Socket("localhost",50000);

    DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
    DataInputStream entrada = new DataInputStream(conexion.getInputStream());

    timeI=System.currentTimeMillis();
    ByteBuffer b = ByteBuffer.allocate(t*8);
    for(int i=0;i<t;i++)
      b.putDouble(Double.parseDouble(i+1+".0"));
    byte[] a = b.array();
    salida.write(a);
    timeO=System.currentTimeMillis();
    System.out.println("El tiempo que tomo en enviar los primeros 10000 " +
      "doubles con ByteBuffer fue: "+(timeO-timeI)+" ms");

    timeI=System.currentTimeMillis();
    for(int i=0;i<t;i++){
      salida.writeDouble(Double.parseDouble(i+1+".0"));
    }
    timeO=System.currentTimeMillis();
    System.out.println("El tiempo que tomo en enviar los primeros 10000 " +
      "doubles con writeDouble fue: "+(timeO-timeI)+" ms");

    salida.close();
    entrada.close();
    conexion.close();
  }
}
