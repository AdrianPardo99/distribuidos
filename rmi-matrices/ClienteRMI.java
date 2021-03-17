import java.rmi.Naming;

/*
 * @author Adrian Gonzalez Pardo
 */

public class ClienteRMI{

  static int N=4,
    A[][]=new int[N][N],
    B[][]=new int[N][N],
    C[][]=new int[N][N];
  static void acomoda_matriz(int[][] C,int[][] A,int renglon,int columna){
  for (int i = 0; i < N/2; i++)
    for (int j = 0; j < N/2; j++)
      C[i + renglon][j + columna] = A[i][j];
  }

  public static void muesta_matriz(int[][] y){
    for(int[] i:y){
      for(int j:i){
        System.out.print("\t"+j+"\t");
      }
      System.out.print("\n");
    }
  }

  static int[][] parte_matriz(int[][] A,int inicio){
  int[][] M = new int[N/2][N];
  for (int i = 0; i < N/2; i++)
    for (int j = 0; j < N; j++)
      M[i][j] = A[i + inicio][j];
  return M;
  }

  static int[][] transponer(int[][] A){
    int [][] Bt=new int[N][N];
    for(int i=0;i<N;i++){
      for(int j=0;j<N;j++){
        Bt[j][i]=A[i][j];
      }
    }
    return Bt;
  }

  public static void llena_matriz(int[][] a,int[][] b,int[][] c){
    int i,j;
    for(i=0;i<N;i++){
      for(j=0;j<N;j++){
        a[i][j]=2*i-j;
        b[i][j]=2*i+j;
        c[i][j]=0;
      }
    }
  }

  public static long checksum(int[] a){
    long c=0;
    for(int i=0;i<a.length;i++){
      c+=a[i];
    }
    return c;
  }

  public static long checksum(long[] a){
    long c=0;
    for(int i=0;i<a.length;i++){
      c+=a[i];
    }
    return c;
  }

  public static void main(String args[]) throws Exception{
    if(args.length<1){

    }
    String url_local="rmi://localhost/matrices", /* Nodo 0 */
    url_nube1="rmi://52.185.206.51/matrices",    /* Nodo 1 */
    url_nube2="rmi://52.171.214.166/matrices",   /* Nodo 2 */
    url_nube3="rmi://13.84.203.145/matrices";    /* Nodo 3 */

    InterfaceRMI r=(InterfaceRMI)Naming.lookup(url_local),
    r1=(InterfaceRMI)Naming.lookup(url_nube1),
    r2=(InterfaceRMI)Naming.lookup(url_nube2),
    r3=(InterfaceRMI)Naming.lookup(url_nube3);
    r.setN(N);
    r1.setN(N);
    r2.setN(N);
    r3.setN(N);
    llena_matriz(A,B,C);
    int [][] Bt=transponer(B);
    int[][] A1=parte_matriz(A,0);
    int[][] A2=parte_matriz(A,N/2);
    int[][] B1=parte_matriz(Bt,0);
    int[][] B2=parte_matriz(Bt,N/2);
    int[][] C1=r.multiplica_matrices(A1,B1);
    int[][] C2=r1.multiplica_matrices(A1,B2);
    int[][] C3=r2.multiplica_matrices(A2,B1);
    int[][] C4=r3.multiplica_matrices(A2,B2);
    acomoda_matriz(C,C1,0,0);
    acomoda_matriz(C,C2,0,N/2);
    acomoda_matriz(C,C3,N/2,0);
    acomoda_matriz(C,C4,N/2,N/2);
    if(N==4){
      System.out.println("Matriz A");
      muesta_matriz(A);
      System.out.println("Matriz B");
      muesta_matriz(B);
      System.out.println("Matriz C");
      muesta_matriz(C);
    }
    long checksum_t[]=new long[N],checksum_f=0;
    for(int i=0;i<N;i++){
      checksum_t[i]=checksum(C[i]);
    }
    checksum_f=checksum(checksum_t);
    System.out.println("El checksum de la matriz C es: 0x"+
      Long.toHexString(checksum_f));
  }
}
