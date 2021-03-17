import java.net.Socket;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.net.Socket;
import java.net.ServerSocket;
import java.lang.Thread;
import java.nio.ByteBuffer;

/*
 * Archivo: Matriz.java
 * @author Adrian Gonzalez Pardo
**/

public class Matriz{

  static int N=4,
    A[][]=new int[N][N],
    B[][]=new int[N][N],
    C[][]=new int[N][N];
  static long timeT=0;

  static Object lock=new Object();

  static void read(DataInputStream f,byte[] b,int posicion,int longitud) throws Exception{
    while (longitud>0){
      int n=f.read(b,posicion,longitud);
      posicion+=n;
      longitud-=n;
    }
  }

  static class Worker extends Thread{
    Socket conexion;
    Worker(Socket conexion){
      this.conexion=conexion;
    }

    public void run(){
      try{
        long timeO=0,timeI=0;
        timeI=System.currentTimeMillis();
        DataOutputStream salida=new DataOutputStream(conexion.getOutputStream());
        DataInputStream entrada=new DataInputStream(conexion.getInputStream());
        int id=entrada.readInt(),i,j;
        System.out.println("Nodo "+(id+1)+" comunicando con server");
        switch(id){
          case 0:{
            for(i=0;i<N/2;i++){
              for(j=0;j<N;j++){
                salida.writeInt(A[i][j]);
              }
            }
            System.out.print("Se envio A1 al nodo "+(id+1)+"\n");
            for(i=0;i<N;i++){
              for(j=0;j<N/2;j++){
                salida.writeInt(B[i][j]);
              }
            }
            System.out.print("Se envio B1 al nodo "+(id+1)+"\n");
            for(i=0;i<N/2;i++){
              for(j=0;j<N/2;j++){
                C[i][j]=entrada.readInt();
              }
            }
            System.out.print("Se recibio C1\n");
          }
          break;
          case 1:{
            for(i=0;i<N/2;i++){
              for(j=0;j<N;j++){
                salida.writeInt(A[i][j]);
              }
            }
            System.out.print("Se envio A1 al nodo "+(id+1)+"\n");
            for(i=0;i<N;i++){
              for(j=N/2;j<N;j++){
                salida.writeInt(B[i][j]);
              }
            }
            System.out.print("Se envio B2 al nodo "+(id+1)+"\n");
            for(i=0;i<N/2;i++){
              for(j=N/2;j<N;j++){
                C[i][j]=entrada.readInt();
              }
            }
            System.out.print("Se recibio C2\n");
          }
          break;
          case 2:{
            for(i=N/2;i<N;i++){
              for(j=0;j<N;j++){
                salida.writeInt(A[i][j]);
              }
            }
            System.out.print("Se envio A2 al nodo "+(id+1)+"\n");
            for(i=0;i<N;i++){
              for(j=0;j<N/2;j++){
                salida.writeInt(B[i][j]);
              }
            }
            System.out.print("Se envio B1 al nodo "+(id+1)+"\n");
            for(i=N/2;i<N;i++){
              for(j=0;j<N/2;j++){
                C[i][j]=entrada.readInt();
              }
            }
            System.out.print("Se recibio C3\n");
          }
          break;
          case 3:{
            for(i=N/2;i<N;i++){
              for(j=0;j<N;j++){
                salida.writeInt(A[i][j]);
              }
            }
            System.out.print("Se envio A2 al nodo "+(id+1)+"\n");
            for(i=0;i<N;i++){
              for(j=N/2;j<N;j++){
                salida.writeInt(B[i][j]);
              }
            }
            System.out.print("Se envio B2 al nodo "+(id+1)+"\n");
            for(i=N/2;i<N;i++){
              for(j=N/2;j<N;j++){
                C[i][j]=entrada.readInt();
              }
            }
            System.out.print("Se recibio C4\n");
          }
          break;
        }
        salida.close();
        entrada.close();
        conexion.close();
        timeO=System.currentTimeMillis();
        long time=timeO-timeI;
        System.out.print("Tiempo de procesamiento en el hilo del node "+
          (id+1)+": "+time+" ms\n");
        synchronized(lock){
          timeT+=time;
        }
      }catch (Exception e) {
        System.err.println(e.getMessage());
      }
    }
  }

  public static void llena_matriz(int[][] a,int[][] b,int[][] c){
    int i,j;
    for(i=0;i<N;i++){
      for(j=0;j<N;j++){
        a[i][j]=2*i+j;
        b[i][j]=2*i-j;
        c[i][j]=0;
      }
    }
  }

  public static int checksum(int[] a){
    int c=0;
    for(int i=0;i<a.length;i++){
      c+=a[i];
    }
    return c;
  }

  public static void muesta_matriz(int[][] y){
    for(int[] i:y){
      for(int j:i){
        System.out.print("\t"+j+"\t");
      }
      System.out.print("\n");
    }
  }
  public static void main(String[] args) throws Exception{
    int i;
    if(args.length<2){
      System.out.println("Usage: java Matriz <node> <ip>");
      System.exit(1);
    }
    int node=Integer.valueOf(args[0]);
    switch(node){
      case 0:
      /*Iniciar matriz A,B

      */
      llena_matriz(A,B,C);
      if(N==4){
        System.out.print("Matriz A\n");
        muesta_matriz(A);
        System.out.print("Matriz B\n");
        muesta_matriz(B);
      }
      ServerSocket servidor=new ServerSocket(50000);
      Worker w[]=new Worker[4];
      for(i=0;i<w.length;i++){
        Socket conexion=servidor.accept();
        w[i]=new Worker(conexion);
        w[i].start();
      }
      for(i=0;i<w.length;i++){
        w[i].join();
      }
      int checksumA[]=new int[N],cT;
      for(i=0;i<N;i++){
        checksumA[i]=checksum(C[i]);
      }
      cT=checksum(checksumA);
      System.out.print("Checksum de la matriz C: 0x"+Integer.toHexString(cT)+"\n");
      if(N==4){
        System.out.print("Matriz C\n");
        muesta_matriz(C);
      }
      System.out.println("Tiempo total de procesamiento en los hilos: "+
        timeT+" ms\nPromedio de tiempo de procesamiento en hilos: "+(timeT/4)+" ms");
      break;
      case 1:case 2:case 3:case 4:
        Socket conexion=null;
        while(true){
          try{
        	  conexion=new Socket(args[1],50000);
            break;
          }catch (Exception e){
            Thread.sleep(100);
          }
        }
        long timeI=0,timeO=0;
        timeI=System.currentTimeMillis();
        DataOutputStream salida=new DataOutputStream(conexion.getOutputStream());
        DataInputStream entrada=new DataInputStream(conexion.getInputStream());
        salida.writeInt(node-1);
        int a[][]=new int[N/2][N],
          b[][]=new int[N/2][N],
          c[][]=new int[N/2][N/2],j;
        for(i=0;i<N/2;i++){
          for(j=0;j<N;j++){
            a[i][j]=entrada.readInt();
          }
        }
        for(i=0;i<N;i++){
          for(j=0;j<N/2;j++){
            b[j][i]=entrada.readInt();
          }
        }
        for(i=0;i<N/2;i++){
          for(j=0;j<N;j++){
            for(int k=0;k<N/2;k++){
              c[i][k]+=a[i][j]*b[k][j];
            }
          }
        }
        for(i=0;i<N/2;i++){
          for(j=0;j<N/2;j++){
            salida.writeInt(c[i][j]);
          }
        }
        salida.close();
        entrada.close();
        conexion.close();
        timeO=System.currentTimeMillis();
        timeT=timeO-timeI;
        System.out.println("Tiempo de ejecucion del nodo "+node+": "+
          timeT+" ms");
      break;
      default:
        System.exit(1);
    }
  }
}
