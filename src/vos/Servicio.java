package vos;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

public class Servicio
{
	public final static String  BANERA="Bañera";
	public final static String  YACUZZI="Yacuzzi";
	public final static String  SAlA="Bañera";
	public final static String  COCINETA="Cosineta";
	public final static String  RESTAURANTE="Restaurante";
	public final static String  PISCINA="Piscina";
	public final static String  PARQUEADERO="Bañera";
	public final static String  WIFI="Wifi";
	public final static String  TV="TV";
	public final static String  HABITACION_COMPARTIDA="Habitacion Compartida";
	public final static String  BANO_PRIVADO="Baño privado";
	public final static String  BANO_COMPARITDO="Baño compartido";
	public final static String  HABITACION_INDIVIDUAL="Habitacion individual";
	public final static String  LUZ="Luz";
	public final static String  TELEFONO="Telefono";
	public final static String  AGUA="Agua";
	public final static String  MENAJE="Menaje";
	public final static String  AMOBLADO="Esta amoblado";
	public final static String  PORTERIA_24H="Porteria 24 horas";
	public final static String  GIMANSIO="Gimnasio";
	public final static String  SALAS_ESTUDIO="Salas de estudio";
	public final static String  SALAS_ESPARCIMIENTO="Salas de esparcimiento";
	public final static String  APOYO_SOCIAL="Apoyo social";
	public final static String  APOYO_ACADEMIDO="Apoyo academico";
	public static final String SERVICIOS_ASEO = "Servicios aseo";
	
	@JsonProperty(value="costo")
	private double costo;

	@JsonProperty(value="nombre")
	private String nombre;

	@JsonProperty(value="id")
	private long id;

//	@JsonProperty(value="habitaciones")
//	public ArrayList<Habitacion> habitaciones;

//	@JsonProperty(value="viviendas")
//	public ArrayList<Vivienda> viviendas;

//	@JsonProperty(value="empresa")
//	public ArrayList<Empresa> empresa;


	public double getCosto() {
		return costo;
	}


	public void setCosto(double costo) {
		this.costo = costo;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


//	public ArrayList<Habitacion> getHabitaciones() {
//		return habitaciones;
//	}
//
//
//	public void setHabitaciones(ArrayList<Habitacion> habitaciones) {
//		this.habitaciones = habitaciones;
//	}
//
//
//	public ArrayList<Vivienda> getViviendas() {
//		return viviendas;
//	}
//
//
//	public void setViviendas(ArrayList<Vivienda> viviendas) {
//		this.viviendas = viviendas;
//	}
//
//
//	public ArrayList<Empresa> getEmpresa() {
//		return empresa;
//	}
//
//
//	public void setEmpresa(ArrayList<Empresa> empresa) {
//		this.empresa = empresa;
//	}


	public Servicio(
			@JsonProperty(value="costo")double costo, 
			@JsonProperty(value="nombre")String nombre, 
			@JsonProperty(value="id")long id 
//			@JsonProperty(value="habitaciones")ArrayList<Habitacion> habitaciones,
//			@JsonProperty(value="viviendas")ArrayList<Vivienda> viviendas,
//			@JsonProperty(value="empresa")ArrayList<Empresa> empresa) 
			)
			{
		super();
		this.costo = costo;
		this.nombre = nombre;
		this.id = id;
//		this.habitaciones = habitaciones;
//		this.viviendas = viviendas;
//		this.empresa = empresa;
	}


	

}

