import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClaseRMI extends UnicastRemoteObject implements InterfaceRMI{
  // es necesario que el contructor ClaseRMI() invoque el constructor de la superclase
  public ClaseRMI() throws RemoteException{
    super( );
  }

  public String mayusculas(String s) throws RemoteException{
    return s.toUpperCase();
  }

  public int suma(int a,int b){
    return a + b;
  }

  public long checksum(int[][] m) throws RemoteException{
    long s = 0;
    for (int i = 0; i < m.length; i++)
      for (int j = 0; j < m[0].length; j++)
        s += m[i][j];
    return s;
  }
}
