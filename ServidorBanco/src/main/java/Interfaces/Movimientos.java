package Interfaces;

public interface Movimientos {
	
	  public abstract double depositar(double depositoDinero);

	    public abstract double retirar(double retiroDinero);

	    public abstract void transferir(String cuentaDestino, double cantidadATransferir);

	    public abstract void imprimir();
}
