package logica;

public class InscripcionDto {
	
	private String dni_a;
	private String id_c;
	private String categoria;
	private String email;
	private String fecha;
	private String metodo_pago;
	private float cantidad_pagada;
	private int horas;
	public String getDni_a() {
		return dni_a;
	}

	public void setDni_a(String dni_a) {
		this.dni_a = dni_a;
	}

	public String getId_c() {
		return id_c;
	}

	public void setId_c(String id_c) {
		this.id_c = id_c;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getMetodo_pago() {
		return metodo_pago;
	}

	public void setMetodo_pago(String metodo_pago) {
		this.metodo_pago = metodo_pago;
	}

	public float getCantidad_pagada() {
		return cantidad_pagada;
	}

	public void setCantidad_pagada(float cantidad_pagada) {
		this.cantidad_pagada = cantidad_pagada;
	}

	public int getHoras() {
		return horas;
	}

	public void setHoras(int horas) {
		this.horas = horas;
	}

	public int getMinutos() {
		return minutos;
	}

	public void setMinutos(int minutos) {
		this.minutos = minutos;
	}

	private int minutos;
	
	public InscripcionDto()
	{
	}
	
	
	

}
