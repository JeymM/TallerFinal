package SERVICES;

import java.util.List;
import java.util.Map;

import Entidades.Cuenta;
import Entidades.CuentaAhorro;
import Entidades.CuentaCorriente;
import Entidades.Usuario;
import Interfaces.CuentaDAO;
import Interfaces.UserDAO;
import REPOSITORIES.CuentaCRUD;
import REPOSITORIES.UsuarioCRUD;

public class ServicesAccount implements Service {

	private CuentaDAO cuenta;
	private UserDAO usuario;
	
	
	
	public ServicesAccount() {
		cuenta=new CuentaCRUD();
		usuario= new UsuarioCRUD();
	}

	
	////Crear
	
	@Override
	public Object Guardar(Map datos) {
		 String numeroCuenta = (String) datos.get("numeroCuenta");
		   String cedula = (String) datos.get("cedula");
	        String TipoCuenta = (String) datos.get("TipoCuenta");
	    
     
	        if(!TipoCuenta.equals("CuentaAhorro") && !TipoCuenta.equals("CuentaCorriente")){
	            throw new RuntimeException("El tipo de cuenta indicado no es valido, deber ser\n" +
	                    "CuentaAhorro o CuentaCorriente");
	        }

	        Usuario usuari = (Usuario)usuario.Buscar(cedula);

	        Cuenta cuent;
	        if(TipoCuenta.equals("CuentaAhorro")){
	           cuent= new CuentaAhorro(numeroCuenta,TipoCuenta,usuari.getId());
	        } else {
	            cuent = new CuentaCorriente(numeroCuenta, TipoCuenta, usuari.getId());
	        }

	        return cuenta.Guardar(cuent);
	
	}
		
	

	

	@Override
	public Object Buscar(Object id) {
		
		return cuenta.Buscar(id);
	}

	@Override
	public void actualizar(String nombre, String apellido) {
		
		
	}

	@Override
	public List<?> Lista() {
		return cuenta.Listar();
	}

	@Override
	public String eliminar(Object numcuenta) {
		Cuenta cuentaAEliminar = (Cuenta) cuenta.Buscar(numcuenta);

        if(cuentaAEliminar == null){
            throw new RuntimeException("La cuenta indicada no existe");
        }

        return cuenta.eliminarCuenta(cuentaAEliminar);
    }
	}

	

	
	


