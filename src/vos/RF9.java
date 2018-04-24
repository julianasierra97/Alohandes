package vos;

import org.codehaus.jackson.annotate.JsonProperty;



public class RF9
{
	@JsonProperty(value="id")
	private String id;

	@JsonProperty(value="tipo")
	private String tipo;


	
	


	public RF9(
			@JsonProperty(value="id")String id, 
			@JsonProperty(value="tipo")String tipo)
	{
		super();
		this.id = id;
		this.tipo = tipo;
		
	}






	public String getId() {
		return id;
	}






	public void setId(String id) {
		this.id = id;
	}






	public String getTipo() {
		return tipo;
	}






	public void setTipo(String tipo) {
		this.tipo = tipo;
	}





	



}

