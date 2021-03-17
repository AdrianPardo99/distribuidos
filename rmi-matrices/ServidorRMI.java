import java.rmi.Naming;

/*
 * @author Adrian Gonzalez Pardo
 */

public class ServidorRMI{
  public static void main(String[] args) throws Exception{
    String url="rmi://0.0.0.0/matrices";
    /* 0.0.0.0 es un comodin para la interfaz de red en el que permita
     * escuchar en cualquier interfaz lo cual permite que no importa si
     * el server esta en intranet o extranet
     **/
    ClaseRMI obj=new ClaseRMI();
    // registra la instancia en el rmiregistry
    Naming.rebind(url,obj);
  }
}
