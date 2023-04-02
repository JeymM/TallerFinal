package Entidades;

import Interfaces.Movimientos;

public class CuentaCorriente extends Cuenta implements Movimientos {
	int numDeposito, numRetiros, transferenciaA, transferenciaC;

	public CuentaCorriente(String numCuenta, String tipoCuenta, int id_user) {
		super(numCuenta, tipoCuenta, id_user);

	}

	@Override
	public double depositar(double depositoDinero) {
		this.saldo += depositoDinero;
		numDeposito++;
		return this.saldo;
	}

	@Override
	public double retirar(double retiroDinero) {
		if (retiroDinero <= this.saldo && this.numRetiros <= 5) {
			this.saldo -= retiroDinero;
			numRetiros++;
		} else {
			throw new IllegalArgumentException("No puede realizar esta transaccion");
		}
		return this.saldo;
	}

	@Override
	public void transferir(String cuentaDestino, double cantidadATransferir) {
		double interes = 0, valormasinteres = cantidadATransferir;
		interes += (cantidadATransferir * 2) / 100;
		valormasinteres += interes;

		if (!this.getTipoCuenta().equals(cuentaDestino)) {
			if (this.transferenciaA < 2) {
				if (valormasinteres > this.saldo) {
					throw new IllegalArgumentException("No tiene saldo");
				} else {
					this.saldo -= valormasinteres;
					this.transferenciaA++;
				}
			} else {
				throw new IllegalArgumentException("Ha excedido el limite de transferencia ");
			}
		} else {
			if (valormasinteres > this.saldo) {
				throw new IllegalArgumentException("Saldo insuficiente");
			} else {
				this.saldo -= valormasinteres;
				this.transferenciaC++;
			}
		}

	}

	@Override
	public void imprimir() {
		int totalTransaccion = numDeposito + numRetiros;
		System.out.println("Ha realizado:" + totalTransaccion);

	}

}
