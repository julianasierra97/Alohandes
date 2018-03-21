package vos;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

public class Vivienda
{
	@JsonProperty(value="capacidad")
	private int capacidad;

	@JsonProperty(value="tipo")
	private String tipo;

	@JsonProperty(value="numeroDeHabitaciones")
	private int numeroDeHabitaciones;

	@JsonProperty(value="costo")
	private double costo;

	@JsonProperty(value="id")
	private int id;

	@JsonProperty(value="direccion")
	private String direccion;

	@JsonProperty(value="servicios")
	public ArrayList<Servicio> servicios;

	@JsonProperty(value="seguro")
	public Seguro seguro;


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


	public int getNumeroDeHabitaciones() {
		return numeroDeHabitaciones;
	}


	public void setNumeroDeHabitaciones(int numeroDeHabitaciones) {
		this.numeroDeHabitaciones = numeroDeHabitaciones;
	}


	public double getCosto() {
		return costo;
	}


	public void setCosto(double costo) {
		this.costo = costo;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getDireccion() {
		return direccion;
	}


	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}


	public ArrayList<Servicio> getServicios() {
		return servicios;
	}


	public void setServicios(ArrayList<Servicio> servicios) {
		this.servicios = servicios;
	}


	public Seguro getSeguro() {
		return seguro;
	}


	public void setSeguro(Seguro seguro) {
		this.seguro = seguro;
	}


	public Vivienda(
			@JsonProperty(value="capacidad")int capacidad, 
			@JsonProperty(value="tipo")String tipo, 
			@JsonProperty(value="numeroDeHabitaciones")int numeroDeHabitaciones, 
			@JsonProperty(value="costo")double costo, 
			@JsonProperty(value="id")int id, 
			@JsonProperty(value="direccion")String direccion,
			@JsonProperty(value="servicios")ArrayList<Servicio> servicios, 
			@JsonProperty(value="seguro")Seguro seguro) {
		super();
		this.capacidad = capacidad;
		this.tipo = tipo;
		this.numeroDeHabitaciones = numeroDeHabitaciones;
		this.costo = costo;
		this.id = id;
		this.direccion = direccion;
		this.servicios = servicios;
	}




}

