package SERVICES;

import java.util.List;
import java.util.Map;

import Entidades.Usuario;
import Interfaces.UserDAO;
import REPOSITORIES.UsuarioCRUD;

public class ServiceUser implements Service {
	
	UserDAO repouser;
	
	

	public ServiceUser() {
		
		repouser=new UsuarioCRUD();
	}

		////Crear
	@Override
	public Object Guardar(Map datos) {
		   String nombre = (String) datos.get("nombre");
	        String apellido = (String) datos.get("apellido");
	        String cedula = (String) datos.get("cedula");
	        
	        
	        Usuario newUser = new Usuario(nombre, apellido, cedula);

	        return repouser.Guardar(newUser); 
		
		
		
	}

	@Override
	public Object Buscar(Object id) {
		
		return repouser.Buscar(id);
	}

	

	@Override
	public List<?> Lista() {
		
		return repouser.Lista();
	}

	@Override
	public String eliminar(Object cedula) {
		 Usuario usuarioAEliminar = (Usuario) repouser.Buscar(cedula);

	        if(usuarioAEliminar == null){
	            throw new RuntimeException("No esta registrado en la base de datos el usuario");
	        }

	        return repouser.eliminar(usuarioAEliminar);
	    }

	@Override
	public void actualizar(String nombre, String apellido) {
		// TODO Auto-generated method stub
		
	}
	}

	
	
	

