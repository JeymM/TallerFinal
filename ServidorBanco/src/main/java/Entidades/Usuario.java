package Entidades;

public class Usuario {
	private int  id;
	String nombre;
	String apellido;
	String cedula;

	public Usuario(int  id, String nombre, String apellido, String cedula) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.cedula = cedula;
	}

	public Usuario(String nombre2, String apellido2, String cedula2) {
		
	}

	public int  getId() {
		return id;
	}

	public void setId(int  id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

}
