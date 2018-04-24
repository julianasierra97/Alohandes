package vos;
import java.sql.Time;
import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;




public class Empresa extends Operador
{
	
	public final static String VIVIENDA_UNIVERSITARIA="Vivienda Universitaria";
	
	public final static String  HOTEL="Hotel";
	
	public final static String HOSTAL="Hostal";
	
	public final static int NUM_SERVICIO_SOBLIGATORIOS_VIVIENDA_UNIVERSITARIA=7;
	
	@JsonProperty(value="ubicacion")
	private String ubicacion;

	@JsonProperty(value="tipo")
	private String tipo;

	@JsonProperty(value="horarioApertura")
	private String horarioApertura;

	@JsonProperty(value="horarioCierra")
	private String horarioCierre;

	@JsonProperty(value="numRegistroSuperintendencia")
	private long numRegistroSuperintendencia;


	@JsonProperty(value="numRegistroCamaraComercio")
	private long numRegistroCamaraComercio;

	@JsonProperty(value="servicios")
	private ArrayList<Servicio> servicios;

	@JsonProperty(value="habitaciones")
	private ArrayList<Habitacion> habitaciones;



	public String getUbicacion() {
		return ubicacion;
	}


	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}


	public String getNombre() {
		return nombre;
	}




	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public String getHorarioApertura() {
		return horarioApertura;
	}


	public void setHorarioApertura(String horarioApertura) {
		this.horarioApertura = horarioApertura;
	}


	public String getHorarioCierre() {
		return horarioCierre;
	}


	public void setHorarioCierre(String horarioCierre) {
		this.horarioCierre = horarioCierre;
	}


	public long getNumRegistroSuperintendencia() {
		return numRegistroSuperintendencia;
	}


	public void setNumRegistroSuperintendencia(long numRegistroSuperintendencia) {
		this.numRegistroSuperintendencia = numRegistroSuperintendencia;
	}


	public long getNumRegistroCamaraComercio() {
		return numRegistroCamaraComercio;
	}


	public void setNumRegistroCamaraComercio(long numRegistroCamaraComercio) {
		this.numRegistroCamaraComercio = numRegistroCamaraComercio;
	}


	public ArrayList<Servicio> getServicios() {
		return servicios;
	}


	public void setServicios(ArrayList<Servicio> servicios) {
		this.servicios = servicios;
	}


	public ArrayList<Habitacion> getHabitaciones() {
		return habitaciones;
	}


	public void setHabitaciones(ArrayList<Habitacion> habitaciones) {
		this.habitaciones = habitaciones;
	}


	public Empresa(
			@JsonProperty(value="ubicacion")String ubicacion, 
			@JsonProperty(value="tipo")String tipo, 
			@JsonProperty(value="horarioApertura")String horarioApertura, 
			@JsonProperty(value="horarioCierre")String horarioCierre,
			@JsonProperty(value="numRegistroSuperintendencia")long numRegistroSuperintendencia, 
			@JsonProperty(value="numRegistroCamaraComercio")long numRegistroCamaraComercio, 
			@JsonProperty(value="login")String login, 
			@JsonProperty(value="contrasenha")String contrasenha, 
			@JsonProperty(value="documento")String documento, 
			@JsonProperty(value="tipoDocumento")String tipoDocumento, 
			@JsonProperty(value="nombre")String nombre, 
			@JsonProperty(value="correo")String correo
//			,
//			@JsonProperty(value="servicios")ArrayList<Servicio> servicios,
//			@JsonProperty(value="habitaciones")ArrayList<Habitacion> habitaciones

			)
			
	{
		super( login, contrasenha, documento, tipoDocumento,  nombre,  correo);
		this.ubicacion = ubicacion;
	
		this.tipo = tipo;
		this.horarioApertura = horarioApertura;
		this.horarioCierre = horarioCierre;
		this.numRegistroSuperintendencia = numRegistroSuperintendencia;
		this.numRegistroCamaraComercio = numRegistroCamaraComercio;
		this.servicios = new ArrayList<>();
		this.habitaciones = new ArrayList<>();
	}


	

}

