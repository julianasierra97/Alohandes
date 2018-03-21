package vos;
import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;



public class Habitacion
{
	public final static String HABITACION_HOTEL="Habitacion de Hotel";

	public final static String HABITACION_HOSTAL="Habitacion de Hostal";
	
	public final static String HABITACION_VIVIENDA_UNIVERSITARIA="Habitacion de Vivienda Universitaria";
	
	public final static String ESTANDAR="Estandar";
	
	public final static String SEMI_SUITES="Semisuites";
	
	public final static String SUITES="Suites";
	
	
	@JsonProperty(value="capacidad")
	private int capacidad;

	
	@JsonProperty(value="tipo")
	private String tipo;

	@JsonProperty(value="precio")
	private double precio;


	@JsonProperty(value="ubicacion")
	private String ubicacion;


	@JsonProperty(value="compartida")
	private boolean compartida;


	@JsonProperty(value="servicios")
	private ArrayList<Servicio> servicios;


	@JsonProperty(value="id")
	private Integer id ;
	
	
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public int getCapacidad() {
		return capacidad;
	}


	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public double getPrecio() {
		return precio;
	}


	public void setPrecio(double precio) {
		this.precio = precio;
	}


	public String getUbicacion() {
		return ubicacion;
	}


	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}


	public boolean isCompartida() {
		return compartida;
	}


	public void setCompartida(boolean compartida) {
		this.compartida = compartida;
	}


	public ArrayList<Servicio> getServicios() {
		return servicios;
	}


	public void setServicios(ArrayList<Servicio> servicios) {
		this.servicios = servicios;
	}


	public Habitacion(
			@JsonProperty(value="capacidad")int capacidad, 
			@JsonProperty(value="tipo")String tipo, 
			@JsonProperty(value="precio")double precio, 
			@JsonProperty(value="ubicacion")String ubicacion, 
			@JsonProperty(value="compartida")boolean compartida,
			@JsonProperty(value="servicios")ArrayList<Servicio> servicios,
			@JsonProperty(value="id") Integer id)
	{
		super();
		this.capacidad = capacidad;
		this.tipo = tipo;
		this.precio = precio;
		this.ubicacion = ubicacion;
		this.compartida = compartida;
		this.servicios = servicios;
		this.id=id;
	}


	

}

