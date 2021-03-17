import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.net.Socket;
import java.net.ServerSocket;
import java.lang.Thread;

class Matriz2{
  static int N=4,
    A[][]=new int[N][N],
    B[][]=new int[N][N],
    C[][]=new int[N][N];

    /*
    *   Graficamente el envio se realiza
    *   ------------
    *   |    A1    |
    *   |----------|
    *   |    A2    |
    *   ------------
    *
    *   Graficamente
    *   -------------
    *   |     |     |
    *   | B1  | B2  |
    *   |     |     |
    *   -------------
    */

    static class Worker extends Thread{
      Socket conexion;
      Worker(Socket conexion){
        this.conexion=conexion;
      }

      public void run(){
        try{
          DataOutputStream salida=new DataOutputStream(conexion.getOutputStream());
          DataInputStream entrada=new DataInputStream(conexion.getInputStream());
          /* Recibe el identifacor del nodo a trabajar (Nodo-1) */
          int id=entrada.readInt(),i,j;
          System.out.println("Nodo "+(id+1)+" comunicando con server");
          switch(id){
            case 0:{
              /* Nodo 1 */
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
              /* Nodo 2 */
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
              /* Nodo 3 */
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
              /* Nodo 4 */
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
          /* Cerramos punto de conexion con un cliente */
          salida.close();
          entrada.close();
          conexion.close();
        }catch (Exception e) {
          System.err.println(e.getMessage());
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
      if(args.length!=1){
        System.out.println("Usage: java Matriz <node>");
        System.exit(1);
      }
      int node=Integer.valueOf(args[0]);
      if(node==0){
        int j;
        /* Asignacion de valores a la matriz A,B,C */
        for(i=0;i<N;i++){
          for(j=0;j<N;j++){
            A[i][j]=2*i+j;
            B[i][j]=2*i-j;
            C[i][j]=0;
          }
        }
        if(N==4){
          System.out.print("Matriz A\n");
          muesta_matriz(A);
          System.out.print("Matriz B\n");
          muesta_matriz(B);
        }
        /* Crea punto de conexion en el puerto 50000 */
        ServerSocket servidor=new ServerSocket(50000);
        Worker w[]=new Worker[4];
        /* Crea hilos en la clase Worker */
        for(i=0;i<w.length;i++){
          Socket conexion=servidor.accept();
          w[i]=new Worker(conexion);
          w[i].start();
        }
        /* Espera al termino de cada hilo */
        for(i=0;i<w.length;i++){
          w[i].join();
        }
        /* Calcula el checksum por filas */
        int checksumA[]=new int[N],cT;
        for(i=0;i<N;i++){
          checksumA[i]=checksum(C[i]);
        }
        /* Calcula el checksum del arreglo de filas */
        cT=checksum(checksumA);
        /* Muestra el checksum en Hexadecimal */
        System.out.print("Checksum de la matriz C: 0x"+Integer.toHexString(cT)+"\n");
        if(N==4){
          System.out.print("Matriz C\n");
          muesta_matriz(C);
        }

      }else if(node>0&&node<5){
        Socket conexion=null;
        while(true){
          try{
        	  conexion=new Socket("localhost",50000);
            break;
          }catch (Exception e){
            Thread.sleep(100);
          }
        }
        DataOutputStream salida=new DataOutputStream(conexion.getOutputStream());
        DataInputStream entrada=new DataInputStream(conexion.getInputStream());
        /* Envia el nodo al que se trabaja */
        salida.writeInt(node-1);
        /* Crea secciones de memoria */
        /*
        *   Graficamente la matriz que recibe
        *   ------------
        *   |    Ax    |
        *   ------------
        *
        *   ------------
        *   |    Bx    |
        *   ------------
        *
        *   ------
        *   | Cx |
        *   ------
        */
        int a[][]=new int[N/2][N],
          b[][]=new int[N/2][N],
          c[][]=new int[N/2][N/2],j;
        for(i=0;i<N/2;i++){
          for(j=0;j<N;j++){
            a[i][j]=entrada.readInt();
          }
        }
        /* Recibe la matriz de forma transpuesta */
        for(i=0;i<N;i++){
          for(j=0;j<N/2;j++){
            b[j][i]=entrada.readInt();
          }
        }
        /* Multiplicacion de la matriz */
        for(i=0;i<N/2;i++){
          for(j=0;j<N;j++){
            for(int k=0;k<N/2;k++){
              c[i][k]+=a[i][j]*b[k][j];
            }
          }
        }
        /* Enviar matriz Cx */
        for(i=0;i<N/2;i++){
          for(j=0;j<N/2;j++){
            salida.writeInt(c[i][j]);
          }
        }
        salida.close();
        entrada.close();
        conexion.close();
      }else{
        System.exit(1);
      }
    }
}
