package logica;

public class CompeticionDto {
	
	private int id;
	private String nombre;
	private String f_comp;
	private String tipo;
	private String distancia;
	private float cuota;
	private String f_fin;
	private int num_plazas;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getF_comp() {
		return f_comp;
	}
	public void setF_comp(String f_comp) {
		this.f_comp = f_comp;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getDistancia() {
		return distancia;
	}
	public void setDistancia(String distancia) {
		this.distancia = distancia;
	}
	public float getCuota() {
		return cuota;
	}
	public void setCuota(float cuota) {
		this.cuota = cuota;
	}
	public String getF_fin() {
		return f_fin;
	}
	public void setF_fin(String f_fin) {
		this.f_fin = f_fin;
	}
	public int getNum_plazas() {
		return num_plazas;
	}
	public void setNum_plazas(int num_plazas) {
		this.num_plazas = num_plazas;
	}
	public String getF_inicio() {
		return f_inicio;
	}
	public void setF_inicio(String f_inicio) {
		this.f_inicio = f_inicio;
	}
	private String f_inicio;
	public CompeticionDto()
	{
	}
	
//	public String toString()
//	{
//		return this.nombre + " - " + this.f_comp + " - " + this.distancia + "km.";
//	}
	
	public String toString()
	{
		return this.nombre + " - " + this.f_comp + " - " +this.tipo+" - "+ this.distancia +" - "+ this.cuota+ "e - "+ this.f_fin+ " - " +
					this.num_plazas + " plazas disponibles ";
	}
	
	

}
