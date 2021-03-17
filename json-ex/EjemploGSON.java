import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.Timestamp;

class EjemploGSON{
  static class Empleado{
    String nombre;
    int edad;
    float sueldo;
    Timestamp fecha_ingreso;
    Empleado jefe;
    Empleado(String nombre,int edad,float sueldo,Timestamp fecha_ingreso,Empleado jefe){
      this.nombre=nombre;
      this.edad=edad;
      this.sueldo=sueldo;
      this.fecha_ingreso=fecha_ingreso;
      this.jefe=jefe!=null?jefe:this;
    }
  }

  public static void main(String[] args){
    Empleado[] e=new Empleado[3];
    e[0]=new Empleado("Hugo",20,1000,Timestamp.valueOf("2020-01-01 20:10:00"),null);
    e[1]=new Empleado("Paco",21,2000,Timestamp.valueOf("2019-10-01 10:15:00"),e[0]);
    e[2]=new Empleado("Luis",22,3000,Timestamp.valueOf("2018-11-01 00:00:00"),e[1]);

    Gson j=new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").create();

    String s=j.toJson(e);
    System.out.println(s);

    Empleado[] v=(Empleado[])j.fromJson(s,Empleado[].class);

    for (int i=0;i<v.length;i++)
      System.out.println(v[i].nombre+" "+v[i].edad+" "+v[i].sueldo+" "+
        v[i].fecha_ingreso+" jefe: "+(v[i].jefe!=null?v[i].jefe.nombre:"ninguno"));
  }
}
