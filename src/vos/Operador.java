package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Operador
{
	
	@JsonProperty(value="login")
	protected  String login;

	@JsonProperty(value="contrasenha")
	protected String contrasenha;


	@JsonProperty(value="documento")
	protected String documento;

	@JsonProperty(value="tipoDocumento")
	protected String tipoDocumento;



	@JsonProperty(value="nombre")
	protected String nombre;





	@JsonProperty(value="correo")
	public String correo;

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





	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}





	public String getCorreo() {
		return correo;
	}



	public void setCorreo(String correo) {
		this.correo = correo;
	}



	public Operador(
			@JsonProperty(value="login")String login, 
			@JsonProperty(value="contrasenha")String contrasenha, 
			@JsonProperty(value="documento")String documento, 
			@JsonProperty(value="tipoDocumento")String tipoDocumento, 
			@JsonProperty(value="nombre")String nombre, 
			@JsonProperty(value="correo" )String correo) {
		
		this.login = login;
		this.contrasenha = contrasenha;
		this.documento = documento;
		this.tipoDocumento = tipoDocumento;
		this.nombre = nombre;
		
		this.correo=correo;
	}



}

