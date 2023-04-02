package Interfaces;

import java.util.List;

import Entidades.Transacciones;

public interface TransaccionDAO {
	public Object Guardar(Object objeto);

	public Object BuscarporId(Object id);

	public Object BuscarTransaccion(Object id);

	public void eliminarTransaccionCuenta(String id_cuenta);

	public List<?> ListarTransaccionCuenta(String id);

	public List<?> ListaTransacciones();

	public boolean Actualizar(Object id, Object data);

	public void eliminarTransaccion(String id);

}
