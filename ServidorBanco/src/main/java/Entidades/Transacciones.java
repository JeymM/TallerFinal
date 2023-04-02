package Entidades;

public class Transacciones {
	private int id;
	String fecha;
	String hora;
	String tipoTransaccion;
	double monto;
	String idCuenta;
	String tipoCuentaDestino;



	public Transacciones(String tipoTransaccion, double monto, String idCuenta, String tipoCuentaDestino) {
		super();
		this.tipoTransaccion = tipoTransaccion;
		this.monto = monto;
		this.idCuenta = idCuenta;
		this.tipoCuentaDestino = tipoCuentaDestino;
	}

	public Transacciones() {
		// TODO Auto-generated constructor stub
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getTipoTransaccion() {
		return tipoTransaccion;
	}

	public void setTipoTransaccion(String tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public String getIdCuenta() {
		return idCuenta;
	}

	public void setIdCuenta(String idCuenta) {
		this.idCuenta = idCuenta;
	}

	public String getTipoCuentaDestino() {
		return tipoCuentaDestino;
	}

	public void setTipoCuentaDestino(String tipoCuentaDestino) {
		this.tipoCuentaDestino = tipoCuentaDestino;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
