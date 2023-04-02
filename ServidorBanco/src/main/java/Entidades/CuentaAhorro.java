package Entidades;

import Interfaces.Movimientos;

public class CuentaAhorro extends Cuenta implements Movimientos{
	int numDeposito,numRetiros,transferenciaA,transferenciaC;
	
	public CuentaAhorro( String numCuenta, String tipoCuenta, int id_user) {
		super( numCuenta,  tipoCuenta, id_user);
		
	}

	@Override
	public double depositar(double depositoDinero) {
		if (numDeposito <= 2) {
            this.saldo += depositoDinero + (depositoDinero*0.5)/100;
        }else {
            this.saldo += depositoDinero;
        }
        numDeposito++;
        return this.saldo;
	}

	@Override
	public double retirar(double retiroDinero) {
		 if (retiroDinero < this.saldo) {
	            if (this.numRetiros > 3) {
	                double interes= (retiroDinero + (retiroDinero*1)/100);
	                if(interes < this.saldo){
	                    this.saldo -= interes;
	                } else {
	                    throw new IllegalArgumentException("No tiene saldo disponible para la cantidad solicitada en el retiro");
	                }
	            }else {
	                this.saldo -= retiroDinero;
	            }
	            this.numRetiros++;
	        }else {
	            throw new IllegalArgumentException("Saldo insuficiente ");
	        }
	        return this.saldo;
	}

	@Override
	public void transferir(String cuentaDestino, double cantidadATransferir) {
		 double interes= 0, valormasinteres = cantidadATransferir;

	        if(!this.getTipoCuenta().equals(cuentaDestino)){
	            interes += (cantidadATransferir*1.5)/100;
	            cantidadATransferir += interes;

	            transferenciaC++;
	        } else {
	            transferenciaA++;
	        }
	        if(valormasinteres > this.getSaldo()){
	            throw new IllegalArgumentException("No se puede realziar la operación, porque la cantida a transferir es mayor al saldo de la cuenta");
	        } else {
	            this.saldo -= cantidadATransferir;
	        }
		
	}

	@Override
	public void imprimir() {
		int totalTransaccion=numDeposito+numRetiros;
		System.out.println("Ha realizado:" +totalTransaccion );
		
	}

	

}
