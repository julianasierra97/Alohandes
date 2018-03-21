package vos;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;



public class Usuario
{
	

	public final static String ESTUDIANTE= "Estudiante";
	public final static String PROFESOR= "Estudiante";
	public final static String PERSONA_EVENTO= "Persona registrada en evento";
	public final static String EGRESADO= "Estudiante";
	public final static String PADRE= "Padre";
	public final static String PROFESOR_INVITADO= "Profesor Invitado";
	public final static String EMPLEADO= "Empleado";
	@JsonProperty(value="login")
	private String login;

	@JsonProperty(value="correo")
	private String correo;
	
	@JsonProperty(value="apellido")
	private String apellido;
	
	@JsonProperty(value="tipo")
	private String tipo;

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

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

	@JsonProperty(value="contrasenha")
	private String contrasenha;


	@JsonProperty(value="documento")
	private String documento;


	@JsonProperty(value="tipoDocumento")
	private String tipoDocumento;

	@JsonProperty(value="edad")
	private int edad;


	@JsonProperty(value="genero")
	private boolean genero;


	@JsonProperty(value="nombre")
	private String nombre;

	@JsonProperty(value="contratos")
	private ArrayList<Contrato> contratos;

//	@JsonProperty(value="operador")
//	private Operador operador;
	
	@JsonProperty(value="padres")
	private ArrayList<Usuario> padres;
	
	@JsonProperty(value="hijos")
	private ArrayList<Usuario> hijos;
	
	
	
	

	public ArrayList<Usuario> getHijos() {
		return hijos;
	}

	public void setHijos(ArrayList<Usuario> hijos) {
		this.hijos = hijos;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getContrasenha() {
		return contrasenha;
	}

	public void setContrasenha(String contrasenha) {
		this.contrasenha = contrasenha;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
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



	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ArrayList<Contrato> getContratos() {
		return contratos;
	}

	public void setContratos(ArrayList<Contrato> contratos) {
		this.contratos = contratos;
	}

//	public Operador getOperador() {
//		return operador;
//	}
//
//	public void setOperador(Operador operador) {
//		this.operador = operador;
//	}

	public Usuario(
			@JsonProperty(value="login")String login, 
			@JsonProperty(value="contrasenha")String contrasenha, 
			@JsonProperty(value="documento")String documento, 
			@JsonProperty(value="tipoDocumento")String tipoDocumento, 
			@JsonProperty(value="edad")int edad, 
			@JsonProperty(value="genero")boolean genero,
			@JsonProperty(value="nombre")String nombre,
			@JsonProperty(value="correo")String correo,
			@JsonProperty(value="tipo")String tipo,
			@JsonProperty(value="apellido")String apellido,
			@JsonProperty(value="contratos")ArrayList<Contrato> contratos
//			@JsonProperty(value="operador")Operador operador,
//			@JsonProperty(value="hijos")ArrayList<Usuario> hijos,
//			@JsonProperty(value="padres")ArrayList<Usuario> padres
			) {
		super();
		this.login = login;
		this.contrasenha = contrasenha;
		this.documento = documento;
		this.tipoDocumento = tipoDocumento;
		this.edad = edad;
		this.genero = genero;
		this.nombre = nombre;
		this.correo = correo;
		this.tipo = tipo;
		this.apellido = apellido;
		this.contratos = contratos;
		//this.operador = operador;
		//this.hijos= hijos;
		//this.padres = padres;
	}

	public ArrayList<Usuario> getPadres() {
		return padres;
	}

	public void setPadres(ArrayList<Usuario> padres) {
		this.padres = padres;
	}


	

}

