package vos;

import org.codehaus.jackson.annotate.JsonProperty;



public class Seguro
{
	@JsonProperty(value="costo")
	private double costo;

	@JsonProperty(value="incendio")
	private boolean incendio;

	@JsonProperty(value="robo")
	private boolean robo;

	@JsonProperty(value="inundaciones")
	private boolean inundaciones;

//	@JsonProperty(value="vivienda")
//	public Vivienda vivienda;

	@JsonProperty(value="id")
	private Integer id;

	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

//	public Vivienda getVivienda() {
//		return vivienda;
//	}
//
//	public void setVivienda(Vivienda vivienda) {
//		this.vivienda = vivienda;
//	}

	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}

	public boolean isIncendio() {
		return incendio;
	}

	public void setIncendio(boolean incendio) {
		this.incendio = incendio;
	}

	public boolean isRobo() {
		return robo;
	}

	public void setRobo(boolean robo) {
		this.robo = robo;
	}

	public boolean isInundaciones() {
		return inundaciones;
	}

	public void setInundaciones(boolean inundaciones) {
		this.inundaciones = inundaciones;
	}

	public Seguro(
			@JsonProperty(value="costo")double costo, 
			@JsonProperty(value="incendio")boolean incendio, 
			@JsonProperty(value="robo")boolean robo, 
			@JsonProperty(value="inundaciones")boolean inundaciones, 
			//@JsonProperty(value="vivienda")Vivienda vivienda,
			@JsonProperty(value="id")Integer id) 
	{
		super();
		this.costo = costo;
		this.incendio = incendio;
		this.robo = robo;
		this.inundaciones = inundaciones;
		//this.vivienda = vivienda;
		this.id=id;
	}



}

