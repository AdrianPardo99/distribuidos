/*
  Error.java
  Permite regresar al cliente REST un mensaje de error
*/

package negocio;

public class Error{
	String message;

	Error(String message){
		this.message = message;
	}
}
