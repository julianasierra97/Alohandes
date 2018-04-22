package vos;
import java.util.ArrayList;


import org.codehaus.jackson.annotate.JsonProperty;




public class PersonaNatural extends Operador
{
	
	public final static String PROFESOR = "Profesor";

	public final static String ESTUDIANTE = "Estudiante";

	public final static String EMPLEADO = "Empleado";

	public final static String EGRESADO = "Egresado";

	public final static String PADRE = "Padre";

	public static final String VECINO = "Vecino";
	
	@JsonProperty(value="tipo")
	private String tipo;

	@JsonProperty(value="habitaciones")
	private ArrayList<Habitacion> habitaciones;


	@JsonProperty(value="vivienda")
	private ArrayList<Vivienda> vivienda;

	@JsonProperty(value="edad")
	private int edad;

	@JsonProperty(value="genero")
	private boolean genero;
	
	@JsonProperty(value = "apellido")
	private String apellido;

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public ArrayList<Habitacion> getHabitaciones() {
		return habitaciones;
	}

	public void setHabitaciones(ArrayList<Habitacion> habitaciones) {
		this.habitaciones = habitaciones;
	}

	public ArrayList<Vivienda> getVivienda() {
		return vivienda;
	}

	public void setVivienda(ArrayList<Vivienda> vivienda) {
		this.vivienda = vivienda;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public boolean isGenero() {
		return genero;
	}

	public void setGenero(boolean genero) {
		this.genero = genero;
	}


	public PersonaNatural(
			@JsonProperty(value="login")String login, 
			@JsonProperty(value="contrasenha")String contrasenha, 
			@JsonProperty(value="documento")String documento, 
			@JsonProperty(value="tipoDocumento")String tipoDocumento, 
			@JsonProperty(value="nombre")String nombre, 
			//@JsonProperty(value="usuario")Usuario usuario, 
			@JsonProperty(value="tipo")String tipo, 
			@JsonProperty(value="habitaciones")ArrayList<Habitacion> habitaciones, 
			@JsonProperty(value="vivienda")ArrayList<Vivienda> viviendas, 
			@JsonProperty(value="edad")int edad,
			@JsonProperty(value="genero")boolean genero,
			@JsonProperty(value="correo")String correo,
			@JsonProperty(value = "apellido")String apellido){
		super( login, contrasenha, documento, tipoDocumento,  nombre,  correo);
		this.tipo = tipo;
		this.habitaciones = habitaciones;
		this.vivienda = viviendas;
		this.edad = edad;
		this.genero = genero;
	}
	



}

