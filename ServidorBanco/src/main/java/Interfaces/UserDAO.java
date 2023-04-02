package Interfaces;

import java.util.List;

public interface UserDAO {
	public Object Guardar(Object objeto);
	public  Object  Buscar (Object id );
	  public  void  actualizar (String nombre,String apellido);
	  public List<?> Lista();
	  public String eliminar(Object i);
	  
}
