package REPOSITORIES;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.sqlite.JDBC;

import Entidades.Usuario;
import Interfaces.UserDAO;

public class UsuarioCRUD implements UserDAO {
	String cadenaconexion;

	public UsuarioCRUD() {
		try {
			DriverManager.registerDriver(new org.sqlite.JDBC());
			cadenaconexion = "jdbc:sqlite:banco.db";

		} catch (SQLException e) {
			System.out.println("Error de conexion con la base de datos" + e);

		}
	}

	// esta se usa para crear
	@Override
	public Object Guardar(Object objeto) {
		try (Connection conexion = DriverManager.getConnection(cadenaconexion)) {
			Usuario usuario = (Usuario) objeto;

			String sentenciaSql = "INSERT INTO USUARIOS  (ID,NOMBRE,APELLIDO,CEDULA)" + "VALUES('" + usuario.getId()
					+ "'," + usuario.getNombre() + "," + usuario.getApellido() + "," + usuario.getCedula() + "');";

		} catch (SQLException e) {

			System.err.println("Error de conexion:" + e);
		} catch (Exception e) {
			System.err.println("Error " + e.getMessage());
		}
		return "Cuenta exitosa";

	}
	//buscar un usuario con su cedula
	@Override
	public Object Buscar(Object cedula) {
		try (Connection conexion = DriverManager.getConnection(cadenaconexion)) {
			String sentenciaSQL = "SELECT * FROM USUARIOS WHERE CEDULA=?";
			PreparedStatement sentencia = conexion.prepareStatement(sentenciaSQL);
			sentencia.setString(1, sentenciaSQL);
			ResultSet resultadoConsulta = sentencia.executeQuery();
			if (resultadoConsulta != null && resultadoConsulta.next()) {
				Usuario usuario = null;
				int idu = resultadoConsulta.getInt("ID");
				String nombre = resultadoConsulta.getString("NOMBRE");
				String apellido = resultadoConsulta.getNString("APELLIDO");
				String ce = resultadoConsulta.getString("CEDULA");
				return new Usuario(idu, nombre, apellido, ce);
			}
		} catch (SQLException e) {
			System.err.println("Error de conexion " + e);
		}
		return null;

	}

	@Override
	public void actualizar(String cedula, String nombre) {
		try (Connection conexion = DriverManager.getConnection(cadenaconexion)) {
			String sentenciaSQL = "UPDATE USUARIOS SET NOMBRE = '" + nombre + "'  WHERE CEDULA = '" + cedula + "';";
			Statement sentencia = conexion.createStatement();
			sentencia.execute(sentenciaSQL);
		} catch (SQLException e) {
			System.err.println("Error de conexion: " + e);
		}

		catch (Exception e) {
			System.err.println("Error" + e.getMessage());
		}
	}

	@Override
	public List<?> Lista() {
		try (Connection conexion = DriverManager.getConnection(cadenaconexion)) {
			ArrayList<Usuario> usuarios = new ArrayList<>();

			String sentenciaSql = "SELECT * FROM USUARIOS";

			PreparedStatement sentencia = conexion.prepareStatement(sentenciaSql);
			ResultSet resultadoConsulta = sentencia.executeQuery();

			if (resultadoConsulta != null) {
				int id;
				while (resultadoConsulta.next()) {

					Usuario usuario = new Usuario(resultadoConsulta.getInt("ID"), resultadoConsulta.getString("NOMBRE"),
							resultadoConsulta.getString("APELLIDO"), resultadoConsulta.getString("CEDULA"));

					usuarios.add(usuario);
				}

				return usuarios;
			}
		} catch (SQLException e) {
			return null;
		} catch (Exception e) {
			return null;
		}

		return null;
	}

	@Override
	public String eliminar(Object i) {
		try (Connection conexion = DriverManager.getConnection(cadenaconexion)) {
			Usuario eliminado = (Usuario) i;

			String obtenerCuentas = "SELECT * FROM CUENTAS WHERE ID_USUARIO=?";
			PreparedStatement Cuentas = conexion.prepareStatement(obtenerCuentas);
			Cuentas.setInt(1, eliminado.getId());
			ResultSet resultSet = Cuentas.executeQuery();

			// usamos contador para la cantidad de cuentas del user
			int cantCuentas = 0;
			String transaccionEliminar = "DELETE FROM TRANSACCIONES WHERE ID_CUENTA = ?";
			PreparedStatement Transaccion = conexion.prepareStatement(transaccionEliminar);
			int tempc = 0;
			if (resultSet != null) {
				// creamos una variable tipo temp para obtener info del resultset
				while (resultSet.next()) {
					tempc = resultSet.getInt("ID");
					if (tempc != 0) {
						// vamos sumando al contador de cuentas
						cantCuentas++;
						Transaccion.setInt(1, tempc);
						Transaccion.executeUpdate();
					}
				}
			}

			String sentenciaCuentasEliminadas = "DELETE FROM CUENTAS WHERE ID_USUARIO = ?";
			PreparedStatement sentenciaEliminarCuentas = conexion.prepareStatement(sentenciaCuentasEliminadas);
			sentenciaEliminarCuentas.setInt(1, eliminado.getId());
			sentenciaEliminarCuentas.executeUpdate();

			// * Eliminación de usuario
			String sentenciaSql = "DELETE FROM USUARIOS WHERE ID = ?";

			PreparedStatement sentencia = conexion.prepareStatement(sentenciaSql);
			sentencia.setInt(1, eliminado.getId());
			sentencia.executeUpdate();

			return "Usuario Eliminado";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

}
