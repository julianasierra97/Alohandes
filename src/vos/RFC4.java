package vos;

import org.codehaus.jackson.annotate.JsonProperty;



public class RFC4
{
	@JsonProperty(value="fechaInicio")
	private String fechaInicio;

	@JsonProperty(value="fechaFin")
	private String fechaFin;

	@JsonProperty(value="servicios")
	private String servicios;


	@JsonProperty(value="id")
	private Integer id;

	
	


	public RFC4(
			@JsonProperty(value="fechaInicio")String fechaInicio, 
			@JsonProperty(value="fechaFin")String fechaFin, 
			@JsonProperty(value="servicios")String servicios)
	{
		super();
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.servicios = servicios;
		
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





	public String getServicios() {
		return servicios;
	}





	public void setServicios(String servicios) {
		this.servicios = servicios;
	}
	
	



}

