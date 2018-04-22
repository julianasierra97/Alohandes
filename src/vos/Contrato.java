package vos;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;




public class Contrato
{
	public final static String RESERVA="Reserva";
	public final static String CONTRATO="Contrato";
	
	@JsonProperty(value="fechaInicio")
	private Date fechaInicio;
	
	@JsonProperty(value="fechaFin")
	private Date fechaFin;
	
	@JsonProperty(value="tipo")
	private String tipo;
	
	@JsonProperty(value="costo")
	private double costo;

	@JsonProperty(value="id")
	private long id;

	@JsonProperty(value="idVivienda")
	private Integer idVivienda;

	@JsonProperty(value="id_habitacion")
	private Integer idHabitacion;
	
	@JsonProperty(value="numeroDePersonas")
	private int numeroDePersonas;
	
	@JsonProperty(value="estado")
	private String estado;
	
	@JsonProperty(value="idCliente")
	private String idCliente;

	@JsonProperty(value="fechaCreacion")
	private Date fechaCreacion;
	
	
	
	public Contrato(
			@JsonProperty(value="fechaInicio")Date fechaInicio,
			@JsonProperty(value="fechaFin")Date fechaFin, 
			@JsonProperty(value="tipo")String tipo, 
			@JsonProperty(value="costo")double costo,
			@JsonProperty(value="id")long id, 
			@JsonProperty(value="idVivienda")Integer vivienda,
			@JsonProperty(value="idHabitacion")Integer habitacion, 
			@JsonProperty(value="numeroDePersonas") int numeroDePersonas,
			@JsonProperty(value="idCliente") String idCliente,
			@JsonProperty(value="fechaCreacion") Date fechaCreacion,
			@JsonProperty(value="estado") String estado) 
	{
		super();
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.tipo = tipo;
		this.costo = costo;
		this.id = id;
		this.idVivienda = vivienda;
		this.idHabitacion = habitacion;
		this.numeroDePersonas = numeroDePersonas;
		this.idCliente=idCliente;
		this.fechaCreacion=fechaCreacion;
		this.estado=estado;
	}

	
	
	public Date getFechaCreacion() {
		return fechaCreacion;
	}



	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}



	public Integer getIdHabitacion() {
		return idHabitacion;
	}


	public void setIdHabitacion(Integer idHabitacion) {
		this.idHabitacion = idHabitacion;
	}


	public int getNumeroDePersonas() {
		return numeroDePersonas;
	}


	public void setNumeroDePersonas(int numeroDePersonas) {
		this.numeroDePersonas = numeroDePersonas;
	}


	public void setIdVivienda(Integer idVivienda) {
		this.idVivienda = idVivienda;
	}


	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getIdVivienda() {
		return idVivienda;
	}

	public void setVivienda(Integer vivienda) {
		this.idVivienda = vivienda;
	}

	public Integer getHabitacion() {
		return idHabitacion;
	}

	public void setHabitacion(Integer habitacion) {
		this.idHabitacion = habitacion;
	}


	public String getIdCliente() {
		return idCliente;
	}


	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	

	

}

