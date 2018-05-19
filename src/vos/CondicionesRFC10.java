package vos;


import org.codehaus.jackson.annotate.JsonProperty;

public class CondicionesRFC10 {

	@JsonProperty(value = "fechaInicio")
	private String fechaInicio;
	
	@JsonProperty(value = "fechaFin")
	private String fechaFin;
	
	@JsonProperty(value = "condicion")
	private String condicion;
	
	@JsonProperty(value = "idAlojamiento")
	private Integer idAlojamiento;
	
	@JsonProperty(value = "tipo")
	private String tipo;
	
	@JsonProperty(value = "admin")
	private Boolean admin;
	
	public CondicionesRFC10(
			@JsonProperty(value="fechaInicio")String fechaInicio,
			@JsonProperty(value="fechaFin")String fechaFin, 
			@JsonProperty(value="condicion")String condicion,
			@JsonProperty(value="idAlojamiento")Integer idAlojamiento,
			@JsonProperty(value = "tipo")String tipo,
			@JsonProperty(value = "admin")Boolean admin
			) {
		super();
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.condicion = condicion;
		this.idAlojamiento = idAlojamiento;
		this.tipo = tipo;
		this.admin = admin;
	}


	public Boolean getAdmin() {
		return admin;
	}


	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public Integer getIdAlojamiento() {
		return idAlojamiento;
	}


	public void setIdAlojamiento(Integer idAlojamiento) {
		this.idAlojamiento = idAlojamiento;
	}


	public String getFechaInicio() {
		return fechaInicio;
	}


	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}


	public String getFechaFin() {
		return fechaFin;
	}


	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}


	public String getCondicion() {
		return condicion;
	}


	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}
	
	
	
}
