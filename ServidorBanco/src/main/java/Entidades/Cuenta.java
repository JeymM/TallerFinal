package Entidades;

public class Cuenta {
	int id;
	String numCuenta;
	float saldo;
	String tipoCuenta;
	int id_user;
	
	
	

	public Cuenta(String numCuenta, String tipoCuenta, int id_user) {
		super();
		this.numCuenta = numCuenta;
		this.tipoCuenta = tipoCuenta;
		this.id_user = id_user;
	}


	public Cuenta(int id,String numCuenta, float saldo, String tipoCuenta, int id_User) {
		super();
		this.id=id;
		this.numCuenta = numCuenta;
		this.saldo = saldo;
		this.tipoCuenta = tipoCuenta;
		this.id_user = id_User;

	}
	

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNumCuenta() {
		return numCuenta;
	}

	public void setNumCuenta(String numCuenta) {
		this.numCuenta = numCuenta;
	}

	public float getSaldo() {
		return saldo;
	}

	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}

	public String getTipoCuenta() {
		return tipoCuenta;
	}

	public void setTipoCuenta(String tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	

}
