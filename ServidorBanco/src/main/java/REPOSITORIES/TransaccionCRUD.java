package REPOSITORIES;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Entidades.Cuenta;
import Entidades.Transacciones;
import Interfaces.TransaccionDAO;

public class TransaccionCRUD implements TransaccionDAO {

	String cadenaconexion;

	public TransaccionCRUD() {
		try {
			DriverManager.registerDriver(new org.sqlite.JDBC());
			cadenaconexion = "jdbc:sqlite:banco.db";

		} catch (SQLException e) {
			System.out.println("Error de conexion con la base de datos" + e);

		}
	}

	@Override
	public Object Guardar(Object objeto) {
		try (Connection conexion = DriverManager.getConnection(cadenaconexion)) {
			// se realiza un casteo al objeto
			Transacciones transaccion = (Transacciones) objeto;
			String sentenciaSql = "INSERT INTO TRANSACCIONES (FECHA, HORA,TIPO_TRANSACCION,MONTO,ID_CUENTA,TIPO_CUENTA_DESTINO) "
					+ " VALUES('" + transaccion.getFecha() + "', " + transaccion.getHora() + "', "
					+ transaccion.getTipoTransaccion() + "'," + transaccion.getMonto() + "',"
					+ transaccion.getIdCuenta() + "', '" + transaccion.getTipoCuentaDestino() + "');";
			Statement sentencia = conexion.createStatement();
			sentencia.execute(sentenciaSql);
		} catch (SQLException e) {
			System.err.println("Error de conexión: " + e);
		} catch (Exception e) {
			System.err.println("Error " + e.getMessage());
		}
		return "Transaccion realizada  con exito";
	}

	@Override
	public Object BuscarporId(Object idCuenta) {
		try (Connection conexion = DriverManager.getConnection(cadenaconexion)) {
			String sentenciaSQL = "SELECT * FROM TRANSACCIONES WHERE ID_CUENTA= ?";
			PreparedStatement sentencia = conexion.prepareStatement(sentenciaSQL);
			sentencia.setString(1, (String) idCuenta);
			ResultSet resultadoConsulta = sentencia.executeQuery();
			if (resultadoConsulta != null && resultadoConsulta.next()) {
				Transacciones transacciones = null;
				int id = resultadoConsulta.getInt("ID");

				String tipo_transaccion = resultadoConsulta.getString("TIPO_TRANSACCION");
				double monto = resultadoConsulta.getDouble("MONTO");
				String tipoCuenta = resultadoConsulta.getString("ID_CUENTA");
				String id_cuenta = resultadoConsulta.getString("TIPO_CUENTA_DESTINO");

				return new Transacciones(tipo_transaccion, monto, tipoCuenta, id_cuenta);

			}

		} catch (SQLException e) {
			System.err.println("Error de conexión: " + e);
		}
		return null;

	}

	@Override
	public Object BuscarTransaccion(Object id) {

		try (Connection conexion = DriverManager.getConnection(cadenaconexion)) {
			int idTransaccionAEncontrar = (int) id;

			String sentenciaSql = "SELECT * FROM TRANSACCIONES " + "WHERE ID = ?";

			PreparedStatement sentencia = conexion.prepareStatement(sentenciaSql);
			sentencia.setInt(1, idTransaccionAEncontrar);
			ResultSet resultadoConsulta = sentencia.executeQuery();

			if (resultadoConsulta != null && resultadoConsulta.next()) {
				int idt = resultadoConsulta.getInt("ID");

				Transacciones transaccion;

				transaccion = new Transacciones();
				resultadoConsulta.getString("FECHA");
				resultadoConsulta.getString("HORA");
				resultadoConsulta.getString("TIPO_TRANSACCION");
				resultadoConsulta.getDouble("MONTO");
				resultadoConsulta.getString("ID_CUENTA");
				resultadoConsulta.getString("TIPO_CUENTA_DESTINO");

				transaccion.setId(idt);
				;

				return transaccion;
			}
		} catch (SQLException e) {
			return "Error de conexión: " + e;
		} catch (Exception e) {
			return "Error " + e.getMessage();
		}

		return null;
	}

	@Override
	public void eliminarTransaccionCuenta(String id_cuenta) {
		try (Connection conexion = DriverManager.getConnection(cadenaconexion)) {
			String sql = "DELETE FROM TRANSACCIONES WHERE ID_CUENTA = " + id_cuenta + ";";
			Statement sentencia = conexion.createStatement();
			sentencia.execute(sql);
		} catch (SQLException e) {
			System.out.println("Error de conexion: " + e.getMessage());
		}

	}

	@Override
	public List<?> ListarTransaccionCuenta(String id) {
		List<Transacciones> transacciones = new ArrayList<Transacciones>();
		try (Connection conexion = DriverManager.getConnection(cadenaconexion)) {
			String sql = "SELECT * FROM TRANSACCIONES WHERE ID_CUENTA = " + id + ";";
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			ResultSet resultadoConsulta = sentencia.executeQuery();

			if (resultadoConsulta != null) {
				while (resultadoConsulta.next()) {

					String tipoTransaccion = resultadoConsulta.getString("TIPO_TRANSACCION");
					double monto = resultadoConsulta.getDouble("MONTO");
					String idCuenta = resultadoConsulta.getString("ID_CUENTA");
					String tipoCuentaDestino = resultadoConsulta.getString("TIPO_CUENTA_DESTINO");

					Transacciones transaccion = new Transacciones(tipoTransaccion, monto, idCuenta, tipoCuentaDestino);
					transacciones.add(transaccion);
				}
				return transacciones;
			}
		} catch (SQLException e) {
			System.out.println("Error de conexion: " + e.getMessage());
		}
		return null;

	}

	public List<?> ListaTransacciones() {
		List<Transacciones> transacciones = new ArrayList<Transacciones>();
		try (Connection conexion = DriverManager.getConnection(cadenaconexion)) {
			String sql = "SELECT * FROM TRANSACCIONES";
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			ResultSet resultadoConsulta = sentencia.executeQuery();

			if (resultadoConsulta != null) {
				while (resultadoConsulta.next()) {

					String tipoTransaccion = resultadoConsulta.getString("TIPO_TRANSACCION");
					double monto = resultadoConsulta.getDouble("MONTO");
					String idCuenta = resultadoConsulta.getString("ID_CUENTA");
					String tipoCuentaDestino = resultadoConsulta.getString("TIPO_CUENTA_DESTINO");

					Transacciones transaccion = new Transacciones(tipoTransaccion, monto, idCuenta, tipoCuentaDestino);
					transacciones.add(transaccion);
				}
				return transacciones;
			}
		} catch (SQLException e) {
			System.out.println("Error de conexion: " + e.getMessage());
		}
		return null;
	}

	@Override
	public boolean Actualizar(Object id, Object data) {

		return false;
	}

	@Override
	public void eliminarTransaccion(String id) {

		try (Connection conexion = DriverManager.getConnection(cadenaconexion)) {
			String sql = "DELETE FROM TRANSACCIONES WHERE ID = " + id + ";";
			Statement sentencia = conexion.createStatement();
			sentencia.execute(sql);
		} catch (SQLException e) {
			System.out.println("Error de conexion: " + e.getMessage());
		}
	}

}
