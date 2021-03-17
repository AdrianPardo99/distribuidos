import java.net.DatagramPacket;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.net.InetAddress;
import java.lang.Thread;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MulticastSocket;
import java.net.InetAddress;

/*
 * @author Adrian Gonzalez Pardo
**/

class Chat{


  static void envia_mensaje(byte[] buffer,String ip,int puerto) throws IOException{
    DatagramSocket socket = new DatagramSocket();
    InetAddress grupo = InetAddress.getByName(ip);
    DatagramPacket paquete = new DatagramPacket(buffer,buffer.length,grupo,puerto);
    socket.send(paquete);
    socket.close();
  }

  static byte[] recibe_mensaje(MulticastSocket socket,int longitud) throws IOException{
    byte[] buffer = new byte[longitud];
    DatagramPacket paquete = new DatagramPacket(buffer,buffer.length);
    socket.receive(paquete);
    return buffer;
  }

  static class Worker extends Thread{
    public void run(){
      /* En un ciclo infinito se recibiran los mensajes enviados al grupo
          230.0.0.0 a traves del puerto 50000 y se desplegaran en la pantalla.
      */
      try{
        InetAddress grupo = InetAddress.getByName("230.0.0.0");
        MulticastSocket socket = new MulticastSocket(50000);
        socket.joinGroup(grupo);
        /* Maximo tamanio de datagrama por la interfaz de red */
        int max_size=1500;
        String mensaje="";
        while(true){
          byte[] a=recibe_mensaje(socket,max_size);
          mensaje+=new String(a,"UTF-8").replace("*","")+"\n";
          System.out.print("\033[H\033[2J");
          System.out.flush();
          System.out.println(mensaje);
          System.out.print("Escribe un mensaje: ");
        }
      }catch(Exception e){
        System.err.println("Exception: "+e.getClass()+" with "+e);
      }
    }
  }

  public static void main(String[] args) throws Exception{
    if(args.length<1){
      System.err.println("Error usage\njava Chat <username>");
      System.exit(1);
    }
    Worker w = new Worker();
    w.start();
    String nombre = args[0];
    BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
    /* En un ciclo infinito se leera los mensajes del teclado y se enviaran
        al grupo 230.0.0.0 a traves del puerto 50000.
    */
    System.out.println("Bienvenido: "+nombre);

    String msg="";
    int i;
    System.out.print("Escribe el mensaje: ");
    while(true){
      msg=b.readLine();
      msg=nombre+" escribe: "+msg;
      /* Usa el tamanio maximo de MTU de la interfaz de red de linux */
      if(msg.length()%1500!=0){
        int resto=1500-msg.length()%1500;
        for(i=0;i<resto;i++){
          msg+="*";
        }
      }
      /* Envia los datos de forma segmentada, por lo tanto todo
          lo que escriba es todo lo que envia
      */
      for(i=0;i<msg.length();i+=1500){
        envia_mensaje(msg.substring(i,i+1500).getBytes(),"230.0.0.0",50000);
      }
    }
  }
}
