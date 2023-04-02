package SERVICES;

import java.util.List;
import java.util.Map;

public interface Service {
	public Object Guardar(Map datos);
	public  Object  Buscar (Object id );
	  public  void  actualizar (String nombre,String apellido);
	  public List<?> Lista();
	  public String eliminar(Object i);

}
