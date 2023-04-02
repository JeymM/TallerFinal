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
import Interfaces.CuentaDAO;

public class CuentaCRUD implements CuentaDAO {
	String cadenaconexion;

	public CuentaCRUD() {
		try {
			DriverManager.registerDriver(new org.sqlite.JDBC());
			cadenaconexion = "jdbc:sqlite:banco.db";

		} catch (SQLException e) {
			System.out.println("Error de conexion con la base de datos " + e);

		}
	}

	@Override
	public Object  Guardar(Object objeto) {
		try (Connection conexion = DriverManager.getConnection(cadenaconexion)) {
			Cuenta cuenta = (Cuenta) objeto;
			String sentenciaSql = "INSERT INTO CUENTAS  (NUMERO_CUENTA, SALDO, TIPO_CUENTA,ID_USUARIO) " + " VALUES('"
					+ cuenta.getNumCuenta() + "', " + cuenta.getSaldo() + "', '" + cuenta.getTipoCuenta() + "',"
					+ cuenta.getId() + "');";
			Statement sentencia = conexion.createStatement();
			sentencia.execute(sentenciaSql);
		} catch (SQLException e) {
			System.err.println("Error de conexión: " + e);
		} catch (Exception e) {
			System.err.println("Error " + e.getMessage());
		}
		return "Cuenta creada exitosamente";

	}

//
//	Este se usara para buscar una cuenta en especifico
	@Override
	public Object Buscar(Object numCuenta) {
		try (Connection conexion = DriverManager.getConnection(cadenaconexion)) {
			String sentenciaSQL = "SELECT * FROM CUENTAS WHERE NUMERO_CUENTA= ?";
			PreparedStatement sentencia = conexion.prepareStatement(sentenciaSQL);
			sentencia.setString(1, (String) numCuenta);
			ResultSet resultadoConsulta = sentencia.executeQuery();
			if (resultadoConsulta != null && resultadoConsulta.next()) {
				Cuenta cuenta = null;
				int idC=resultadoConsulta.getInt("ID");
				String numResultado = resultadoConsulta.getString("numCuenta");
				float saldo = resultadoConsulta.getFloat("saldo");
				String tipoCuenta = resultadoConsulta.getString("tipoCuenta");
				int iduser = resultadoConsulta.getInt("iduser");

				return new Cuenta(idC,numResultado, saldo, tipoCuenta, iduser);

			}

		} catch (SQLException e) {
			System.err.println("Error de conexión: " + e);
		}
		return null;
	}

	
	 @Override 
	 public List<?> Listar() {
	List<Cuenta>cuentas=new ArrayList<Cuenta>();
	
	 try (Connection conexion = DriverManager.getConnection(cadenaconexion)) { String
	sentenciaSql = "SELECT * FROM CUENTAS";
	 PreparedStatement sentencia =
	  conexion.prepareStatement(sentenciaSql); ResultSet resultadoConsulta =
	  sentencia.executeQuery();
	  
	  if (resultadoConsulta != null) {
	  while (resultadoConsulta.next()) { Cuenta
	  cuenta = null;
	  int idC=resultadoConsulta.getInt("ID");
	  String numCuenta = resultadoConsulta.getString("NUMERO_CUENTA");
	  float saldo = resultadoConsulta.getFloat("SALDO"); 
	  String tipoCuenta=resultadoConsulta.getString("TIPO_CUENTA");
	  int idUser=resultadoConsulta.getInt("ID_USUARIO");
	  
	  cuenta = new Cuenta(idC,numCuenta,saldo,tipoCuenta,idUser);
	  cuentas.add(cuenta); } return cuentas; } } catch (SQLException e) {
	  System.err.println("Error de conexión: " + e); } return null;
	  
	  }
	 
	public boolean actualizar(Object id, Object saldo) {
		try (Connection conexion = DriverManager.getConnection(cadenaconexion)) {
			String sentenciaSql = "UPDATE CUENTAS SET SALDO = '" + saldo + "'  WHERE NUMERO_CUENTA = '" + id
					+ "';";
			Statement sentencia = conexion.createStatement();
			sentencia.execute(sentenciaSql);
		} catch (SQLException e) {
			System.err.println("Error de conexión: " + e);
		} catch (Exception e) {
			System.err.println("Error " + e.getMessage());
		}
		return false;
	}

	@Override
	public String eliminarCuenta(Object cuenta) {
		 try (Connection conexion = DriverManager.getConnection(cadenaconexion)) {
	            Cuenta cuentaEliminar = (Cuenta) cuenta;

	            
	            String  EliminarTransacciones =
	                    "DELETE FROM TRANSACCIONES WHERE ID_CUENTA = ?;";
	            PreparedStatement sentenciaEliminarTransacciones = conexion.prepareStatement(EliminarTransacciones);

	            sentenciaEliminarTransacciones.setInt(1, cuentaEliminar.getId());
	            sentenciaEliminarTransacciones.executeUpdate();

	            
	            String EliminarCuentas =
	                    "DELETE FROM CUENTAS WHERE ID = ?;";
	            PreparedStatement sentenciaEliminarCuentas = conexion.prepareStatement(EliminarCuentas);
	            sentenciaEliminarCuentas.setInt(1, cuentaEliminar.getId());
	            sentenciaEliminarCuentas.executeUpdate();
	            
	         
	            return "Cuenta Eliminada com exito";

	        } catch (SQLException e) {
	            return "Error de conexión: " + e;
	        } catch (Exception e) {
	            return "Error " + e.getMessage();
	        }
	    }

	

}
