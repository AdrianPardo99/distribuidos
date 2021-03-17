/*
 * @author Adrian Gonzalez Pardo
 **/

public class Usuario{
  String email,nombre,apellido_paterno,
    apellido_materno,fecha_nacimiento,telefono,genero,foto;

  Usuario(String email,String nombre,String apellido_paterno,
    String apellido_materno,String fecha_nacimiento,String telefono,
    String genero, String foto){
    this.email=email;
    this.nombre=nombre;
    this.apellido_paterno=apellido_paterno;
    this.apellido_materno=apellido_materno;
    this.fecha_nacimiento=fecha_nacimiento;
    this.telefono=telefono;
    this.genero=genero;
    this.foto=foto;
  }

  @Override
  public String toString(){
    return "\tEmail: "+this.email+"\n\tNombre: "+this.nombre+
      "\n\tApellidos: "+this.apellido_paterno+" "+this.apellido_materno+
      "\n\tFecha de nacimiento: "+this.fecha_nacimiento+"\n\tTelefono: "+
      this.telefono+"\n\tGenero: "+this.genero+"\n\tFoto: "+this.foto;
  }
}
