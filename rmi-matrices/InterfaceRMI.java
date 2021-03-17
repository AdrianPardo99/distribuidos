import java.rmi.RemoteException;
import java.rmi.Remote;

/*
 * @author Adrian Gonzalez Pardo
 */
 
public interface InterfaceRMI extends Remote{
  public void setN(int N) throws RemoteException;
  public int[][] multiplica_matrices(int[][] A,int[][] B) throws RemoteException;
}
