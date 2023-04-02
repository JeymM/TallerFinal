package Interfaces;

import java.util.List;

public interface CuentaDAO {

	public Object Guardar(Object objeto);

	public Object Buscar(Object id);

	public boolean actualizar(Object numeroDeCuenta,Object saldo);
	
	public List<?> Listar();
	public String eliminarCuenta(Object nCuenta);

}
