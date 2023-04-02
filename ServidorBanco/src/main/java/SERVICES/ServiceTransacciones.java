package SERVICES;

import java.util.List;
import java.util.Map;

import Entidades.Cuenta;
import Entidades.CuentaAhorro;
import Entidades.CuentaCorriente;
import Entidades.Transacciones;
import Interfaces.CuentaDAO;
import Interfaces.TransaccionDAO;
import REPOSITORIES.CuentaCRUD;
import REPOSITORIES.TransaccionCRUD;

public class ServiceTransacciones implements Service {

	TransaccionDAO transaccionn;
	CuentaDAO cuent;

	public ServiceTransacciones() {
		transaccionn = new TransaccionCRUD();
		cuent = new CuentaCRUD();
	}

	@Override
	public Object Guardar(Map datos) {
		String tipoTransaccion = (String) datos.get("tipoTransaccion");
		double monto = (double) datos.get("monto");
		String numeroCuenta = (String) datos.get("numeroCuenta");
		String tipoCuentaDestino = (String) datos.get("tipoCuentaDestino");

		if (tipoTransaccion == null || tipoTransaccion == "" || monto <= 0 || numeroCuenta == null
				|| numeroCuenta == "") {
			throw new RuntimeException("No se enviaron todos los campos");
		}

		if (tipoTransaccion.equals("transferencia") && tipoCuentaDestino == null || tipoCuentaDestino == "") {
			throw new RuntimeException("No puede realizar la transaccion, se necesita la cuenta de destino(tipo)");
		}

		Cuenta cuenta = (Cuenta) cuent.Buscar(numeroCuenta);

		if (cuenta == null) {
			throw new RuntimeException("No esta en bd la cuenta ingresada");
		}

		Transacciones transaccion = new Transacciones(tipoTransaccion, monto, numeroCuenta, tipoCuentaDestino);

		try {
			if (cuenta.equals("CuentaAhorro")) {
				CuentaAhorro cuentaAhorro = (CuentaAhorro) cuenta;
				cuenta = dotransacciondeAhorro(cuentaAhorro, transaccion);
			} else {
				CuentaCorriente cuentacorriente = (CuentaCorriente) cuenta;
				cuenta = dotransacciondeCorriente(cuentacorriente, transaccion);
			}
		} catch (Exception e) {
			throw e;
		}

		boolean saldoActualizado = cuent.actualizar(cuenta.getId(), cuenta.getSaldo());

		return transaccionn.Guardar(transaccion);

	}

	// transacciones por id de cuenta
	@Override
	public Object Buscar(Object id) {
		return transaccionn.BuscarporId(id);
	}

	// no lo uso

	@Override
	public void actualizar(String nombre, String apellido) {

	}

	@Override
	public List<?> Lista() {

		return transaccionn.ListaTransacciones();
	}
//no se usa
	@Override
	public String eliminar(Object id) {
		  Transacciones tEliminar = (Transacciones) transaccionn.BuscarporId(id);

	        if(tEliminar == null){
	            throw new RuntimeException("La transaccion indicada no existe");
	        }

	        return null;
	}

	public Cuenta dotransacciondeAhorro(CuentaAhorro cuenta, Transacciones transaccion) {
		switch (transaccion.getTipoCuentaDestino()) {
		case "depositar":
			cuenta.depositar(transaccion.getMonto());
			break;
		case "retirar":
			cuenta.retirar(transaccion.getMonto());
			break;
		case "transferir":
			cuenta.transferir(transaccion.getTipoCuentaDestino(), transaccion.getMonto());
			break;
		}
		return cuenta;
	}

	public Cuenta dotransacciondeCorriente(CuentaCorriente cuenta, Transacciones transaccion) {
		switch (transaccion.getTipoCuentaDestino()) {
		case "depositar":
			cuenta.depositar(transaccion.getMonto());
			break;
		case "retirar":
			cuenta.retirar(transaccion.getMonto());
			break;
		case "transferir":
			cuenta.transferir(transaccion.getTipoCuentaDestino(), transaccion.getMonto());
			break;
		}
		return cuenta;
	}

}
