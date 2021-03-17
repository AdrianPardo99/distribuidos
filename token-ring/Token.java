import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.net.Socket;
import java.net.ServerSocket;
import java.lang.Thread;

/*
 * @author Adrian Gonzalez Pardo
 */


class Token{
  static DataInputStream entrada;
  static DataOutputStream salida;
  static boolean primera_vez = true;
  static String ip;
  static long token = 0;
  static int nodo;

  static class Worker extends Thread{
    public void run(){
       //Algoritmo 1
       try{
         ServerSocket servidor=new ServerSocket(50000);
         Socket conexion=servidor.accept();
         entrada=new DataInputStream(conexion.getInputStream());
       }catch(Exception e){
         System.err.println(e.getMessage());
       }
    }
  }

  public static void main(String[] args) throws Exception{
    if (args.length != 2){
      System.err.println("Se debe pasar como parametros el numero de nodo y la IP del siguiente nodo");
      System.exit(1);
    }

    nodo = Integer.valueOf(args[0]);  // el primer parametro es el numero de nodo
    ip = args[1];  // el segundo parametro es la IP del siguiente nodo en el anillo
    //Algoritmo 2
    Worker w=new Worker();
    w.start();
    Socket conexion=null;
    while(true){
      try{
        conexion=new Socket(ip,50000);
        break;
      }catch (Exception e){
        Thread.sleep(500);
      }
    }
    salida=new DataOutputStream(conexion.getOutputStream());
    w.join();
    for(;;){
      if(nodo==0){
        if(primera_vez){ /*True*/
          primera_vez=!primera_vez;
        }else{
          token=entrada.readLong();
        }
      }else{
        token=entrada.readLong();
      }
      token++;
      System.out.println("Nodo:"+nodo+" Token: "+token);
      salida.writeLong(token);
      break;
    }
  }
}
